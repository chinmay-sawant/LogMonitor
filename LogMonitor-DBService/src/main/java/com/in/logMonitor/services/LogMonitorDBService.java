package com.in.logMonitor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.in.logMonitor.models.LogMonitorDataDB;
import com.in.logMonitor.repository.LogMonitorDBRepository;

@Service
@Component
public class LogMonitorDBService {

	@Autowired
	LogMonitorDBRepository logMonitorDBRepository;

	public LogMonitorDBService(LogMonitorDBRepository logMonitorDBRepository) {
		this.logMonitorDBRepository = logMonitorDBRepository;

	}

	public List<LogMonitorDataDB> getAllErrors() {
		Pageable paging = PageRequest.of(2, 3);
		// List<LogMonitorDataDB> getAllErros =
		// logMonitorDBRepository.findAll(paging).getContent();
		List<LogMonitorDataDB> getAllErros = logMonitorDBRepository.getAllData(0);
		return getAllErros;

	}

	public LogMonitorDataDB getSingleError(int id) {
		return logMonitorDBRepository.findById(id).get();
	}

}
