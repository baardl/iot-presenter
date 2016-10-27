package io.baardl.presenter.health;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthResource {

    @RequestMapping(value = "", method = RequestMethod.GET)
    String health() {
        return "ok";
    }
}
