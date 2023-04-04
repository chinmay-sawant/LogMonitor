package com.in.logMonitor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.in.logMonitor.models.LogMonitorDataDB;

@Repository
public interface LogMonitorDBRepository extends JpaRepository<LogMonitorDataDB, Integer> {

	@Query(value = "SELECT id,timestamp,data,ipaddress,logname,projectname FROM todays_log_data order by id desc OFFSET ?1 ROWS FETCH NEXT 5 ROWS ONLY ", nativeQuery = true)
	List<LogMonitorDataDB> getAllData(Integer offset);
}
