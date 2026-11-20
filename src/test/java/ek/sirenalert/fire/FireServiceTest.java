package ek.sirenalert.fire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ek.sirenalert.siren.Siren;
import ek.sirenalert.siren.SirenRepository;
import ek.sirenalert.siren.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

// Integration tests: the service with the real repositories and H2.
// @Transactional rolls the database back after each test.
@SpringBootTest
@Transactional
class FireServiceTest {

    @Autowired
    FireService fireService;

    @Autowired
    SirenRepository sirenRepository;

    Siren near;
    Siren far;
    Siren outOfService;

    @BeforeEach
    void setUp() {
        sirenRepository.deleteAll();
        near = sirenRepository.save(new Siren("Near", 34.02, -118.50, false));
        far = sirenRepository.save(new Siren("Far", 34.50, -118.50, false));
        outOfService = sirenRepository.save(new Siren("Out of service", 34.02, -118.50, true));
    }

    @Test
    void aFireSetsOffTheSirensWithin10Km() {
        Fire fire = fireService.reportFire(new Fire(34.01, -118.49));

        assertEquals(1, fire.getSirens().size());
        assertEquals(Status.FARE, near.getStatus());
        assertEquals(Status.FRED, far.getStatus());
    }

    @Test
    void aSirenOutOfServiceIsNotSetOff() {
        fireService.reportFire(new Fire(34.01, -118.49));

        assertEquals(Status.FRED, outOfService.getStatus());
    }

    @Test
    void closingAFireSetsItsSirensBackToFred() {
        Fire fire = fireService.reportFire(new Fire(34.01, -118.49));

        Fire closed = fireService.closeFire(fire.getId());

        assertTrue(closed.isClosed());
        assertEquals(Status.FRED, near.getStatus());
    }
}
