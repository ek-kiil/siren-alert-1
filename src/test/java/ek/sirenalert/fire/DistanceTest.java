package ek.sirenalert.fire;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DistanceTest {

    @Test
    void theSamePointIsZeroKmAway() {
        double km = Distance.km(34.0094, -118.4973, 34.0094, -118.4973);

        assertEquals(0, km, 0.001);
    }

    @Test
    void oneDegreeOfLatitudeIsAbout111Km() {
        double km = Distance.km(34.0, -118.5, 35.0, -118.5);

        assertEquals(111.2, km, 0.1);
    }
}
