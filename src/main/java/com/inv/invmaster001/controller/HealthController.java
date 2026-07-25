package com.inv.invmaster001.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Lightweight, unauthenticated liveness probe. Handy for uptime checks; can
 * also be used by an external keep-alive pinger if cold starts become a problem
 * (free-tier hosts sleep when idle, adding 60-180s to the first request).
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}
