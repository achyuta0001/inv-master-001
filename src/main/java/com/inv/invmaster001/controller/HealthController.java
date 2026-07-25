package com.inv.invmaster001.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Lightweight, unauthenticated liveness probe. Used by an external keep-alive
 * pinger to prevent the free-tier host from spinning down (which otherwise
 * causes 60-180s cold starts on the first request).
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}
