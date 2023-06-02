package io.github.mrpanupat.probe.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
public class ProbeController {

    private final HealthIndicator pingHealthContributor;
    private final InfoEndpoint infoEndpoint;

    @GetMapping("/health")
    public Health getHealthIndicator() {
        return pingHealthContributor.getHealth(false);
    }

    @GetMapping("/info")
    public Map<String, Object> getInfo() {
        return infoEndpoint.info();
    }

}
