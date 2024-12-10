package com.mohit.scag.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ThreatDetectionService {
    public boolean isThreat(String ipAddress) {
        // Mock API call
        List<String> maliciousIPs = List.of("192.168.1.100", "10.0.0.50");
        return maliciousIPs.contains(ipAddress);
    }
}

