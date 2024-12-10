package com.mohit.scag.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mohit.scag.model.ActivityLog;
@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
}

