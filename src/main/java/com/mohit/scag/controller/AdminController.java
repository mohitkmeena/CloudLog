package com.mohit.scag.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mohit.scag.model.ActivityLog;
import com.mohit.scag.repository.ActivityLogRepository;
import com.mohit.scag.service.ActivityLogService;

@RestController

public class AdminController {
    @Autowired
    private ActivityLogRepository activityLogRepository;
  @Autowired
  private ActivityLogService activityLogService;
    @GetMapping("/logs")
    public List<ActivityLog> getAllLogs(Principal principal) {
        activityLogService.logActivity(principal.getName(), "admin activity accessed");
        return activityLogRepository.findAll();
    }
   

}
