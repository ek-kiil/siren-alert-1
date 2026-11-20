package ek.sirenalert.fire;

import ek.sirenalert.siren.Siren;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FireRepository extends JpaRepository<Fire, Long> {

    List<Fire> findByClosedFalse();

    List<Fire> findBySirensContaining(Siren siren);
}
