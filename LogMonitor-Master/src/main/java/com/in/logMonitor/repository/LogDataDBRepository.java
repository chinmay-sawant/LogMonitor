package com.in.logMonitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in.logMonitor.models.LogMonitorDataDB;

@Repository
public interface LogDataDBRepository extends JpaRepository<LogMonitorDataDB, Integer> {

}
