package ek.sirenalert;

import ek.sirenalert.siren.Siren;
import ek.sirenalert.siren.SirenRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Ten sirens around Santa Monica and Pacific Palisades, saved when the application starts
@Component
public class TestData implements CommandLineRunner {

    private final SirenRepository sirenRepository;

    public TestData(SirenRepository sirenRepository) {
        this.sirenRepository = sirenRepository;
    }

    @Override
    public void run(String... args) {
        sirenRepository.save(new Siren("Santa Monica Pier", 34.0094, -118.4973, false));
        sirenRepository.save(new Siren("Ocean Park", 34.0000, -118.4810, false));
        sirenRepository.save(new Siren("Montana Avenue", 34.0330, -118.5000, false));
        sirenRepository.save(new Siren("Brentwood", 34.0520, -118.4740, false));
        sirenRepository.save(new Siren("Pacific Palisades Village", 34.0480, -118.5265, false));
        sirenRepository.save(new Siren("Will Rogers State Beach", 34.0356, -118.5360, false));
        sirenRepository.save(new Siren("Temescal Canyon", 34.0520, -118.5290, true));
        sirenRepository.save(new Siren("Palisades Highlands", 34.0890, -118.5510, false));
        sirenRepository.save(new Siren("Topanga State Park", 34.0930, -118.5880, false));
        sirenRepository.save(new Siren("Malibu Pier", 34.0372, -118.6770, false));
    }
}
