package com.mohit.scag.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mohit.scag.service.ThreatDetectionService;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ThreatDetectionFilter extends OncePerRequestFilter {
    @Autowired
    private ThreatDetectionService threatDetectionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String ipAddress = request.getRemoteAddr();
        if (threatDetectionService.isThreat(ipAddress)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Access denied: Malicious activity detected");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
