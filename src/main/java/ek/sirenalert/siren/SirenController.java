package ek.sirenalert.siren;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sirens")
public class SirenController {

    private final SirenService sirenService;

    public SirenController(SirenService sirenService) {
        this.sirenService = sirenService;
    }

    @GetMapping
    public List<Siren> getSirens() {
        return sirenService.getSirens();
    }

    @PostMapping
    public Siren createSiren(@RequestBody Siren siren) {
        return sirenService.createSiren(siren);
    }

    @PutMapping("/{id}")
    public Siren updateSiren(@PathVariable Long id, @RequestBody Siren siren) {
        return sirenService.updateSiren(id, siren);
    }

    @DeleteMapping("/{id}")
    public void deleteSiren(@PathVariable Long id) {
        sirenService.deleteSiren(id);
    }
}
