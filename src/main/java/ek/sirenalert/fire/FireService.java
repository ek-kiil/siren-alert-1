package ek.sirenalert.fire;

import ek.sirenalert.siren.Siren;
import ek.sirenalert.siren.SirenRepository;
import ek.sirenalert.siren.Status;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FireService {

    private final FireRepository fireRepository;
    private final SirenRepository sirenRepository;

    public FireService(FireRepository fireRepository, SirenRepository sirenRepository) {
        this.fireRepository = fireRepository;
        this.sirenRepository = sirenRepository;
    }

    public List<Fire> getFires() {
        return fireRepository.findAll();
    }

    public List<Fire> getActiveFires() {
        return fireRepository.findByClosedFalse();
    }

    // Saves the fire and sets off the sirens within 10 km that are in service
    public Fire reportFire(Fire fire) {
        fire.setTime(LocalDateTime.now());

        for (Siren siren : sirenRepository.findAll()) {
            double km = Distance.km(fire.getLatitude(), fire.getLongitude(),
                    siren.getLatitude(), siren.getLongitude());

            if (km <= 10 && !siren.isDisabled()) {
                siren.setStatus(Status.FARE);
                sirenRepository.save(siren);
                fire.getSirens().add(siren);
            }
        }

        return fireRepository.save(fire);
    }

    // Closes the fire and sets its sirens back to FRED
    public Fire closeFire(Long id) {
        Fire fire = fireRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No fire with id " + id));

        fire.setClosed(true);

        for (Siren siren : fire.getSirens()) {
            siren.setStatus(Status.FRED);
            sirenRepository.save(siren);
        }

        return fireRepository.save(fire);
    }
}
