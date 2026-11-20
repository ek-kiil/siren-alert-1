package ek.sirenalert.fire;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fires")
public class FireController {

    private final FireService fireService;

    public FireController(FireService fireService) {
        this.fireService = fireService;
    }

    // GET /fires gives all fires, GET /fires?status=active only those that are not closed
    @GetMapping
    public List<Fire> getFires(@RequestParam(required = false) String status) {
        if ("active".equals(status)) {
            return fireService.getActiveFires();
        }
        return fireService.getFires();
    }

    @PostMapping
    public Fire reportFire(@RequestBody Fire fire) {
        return fireService.reportFire(fire);
    }

    @PutMapping("/{id}/closure")
    public Fire closeFire(@PathVariable Long id) {
        return fireService.closeFire(id);
    }
}
