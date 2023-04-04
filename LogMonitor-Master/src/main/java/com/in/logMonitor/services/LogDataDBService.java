package com.in.logMonitor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in.logMonitor.models.LogMonitorDataDB;
import com.in.logMonitor.repository.LogDataDBRepository;

@Service
public class LogDataDBService {

	@Autowired
	LogDataDBRepository logDataDBRepository;

	public LogDataDBService(LogDataDBRepository logDataDBRepository) {
		this.logDataDBRepository = logDataDBRepository;
	}

	public void saveLogDataToDB(List<LogMonitorDataDB> listLogDataDB) {
		logDataDBRepository.saveAll(listLogDataDB);
	}
}
