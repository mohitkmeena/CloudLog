package com.mohit.scag.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mohit.scag.model.ActivityLog;
import com.mohit.scag.repository.ActivityLogRepository;

@Service
public class ActivityLogService {
    @Autowired
    private ActivityLogRepository activityLogRepository;

    public void logActivity(String username, String action) {
        ActivityLog log = new ActivityLog();
        log.setUsername(username);
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());
        activityLogRepository.save(log);
    }
}
