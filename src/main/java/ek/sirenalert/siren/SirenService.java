package ek.sirenalert.siren;

import ek.sirenalert.fire.Fire;
import ek.sirenalert.fire.FireRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SirenService {

    private final SirenRepository sirenRepository;
    private final FireRepository fireRepository;

    public SirenService(SirenRepository sirenRepository, FireRepository fireRepository) {
        this.sirenRepository = sirenRepository;
        this.fireRepository = fireRepository;
    }

    public List<Siren> getSirens() {
        return sirenRepository.findAll();
    }

    public Siren createSiren(Siren siren) {
        return sirenRepository.save(siren);
    }

    public Siren updateSiren(Long id, Siren changes) {
        Siren siren = getSiren(id);
        siren.setName(changes.getName());
        siren.setLatitude(changes.getLatitude());
        siren.setLongitude(changes.getLongitude());
        siren.setDisabled(changes.isDisabled());
        return sirenRepository.save(siren);
    }

    public void deleteSiren(Long id) {
        Siren siren = getSiren(id);

        // A siren cannot be deleted while a fire still points to it
        for (Fire fire : fireRepository.findBySirensContaining(siren)) {
            fire.getSirens().removeIf(s -> s.getId().equals(id));
            fireRepository.save(fire);
        }

        sirenRepository.delete(siren);
    }

    private Siren getSiren(Long id) {
        return sirenRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No siren with id " + id));
    }
}
