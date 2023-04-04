package com.in.logMonitor.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.in.logMonitor.models.LogMonitorData;
import com.in.logMonitor.models.LogMonitorDataDB;
import com.in.logMonitor.repository.LogDataDBRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RabbitMQConsumer {

	@Autowired
	LogDataDBRepository logDataDBRepository;

	public RabbitMQConsumer(LogDataDBRepository logDataDBRepository) {
		this.logDataDBRepository = logDataDBRepository;
	}
	List<LogMonitorDataDB> LogsPool = new ArrayList<LogMonitorDataDB>();

	// @Scheduled(fixedRate = 5000L)
	@RabbitListener(queues = "${logmonitor.rabbitmq.queue}")
	public void consume(LogMonitorData logMonitorData) {
		LogsPool.add(new LogMonitorDataDB(null, logMonitorData.getIpaddress(), logMonitorData.getProjectName(),
				logMonitorData.getLogname(), logMonitorData.getData(), logMonitorData.getCurrentTimestamp()));
		log.info("Consuming Data from rabbitMQ and adding to logPool =======> {}", LogsPool.size());
		// log.info(logMonitorData.toString());
	}
	
	@Scheduled(fixedRateString = "${logmonitor.savetodb-interval}", initialDelayString = "${logmonitor.savetodb-initialdelay}")
	public void saveLogPoolToDB() {
		logDataDBRepository.saveAll(LogsPool);
		LogsPool.clear();

		log.info("================");
		log.info("LogsPool Delete " + LogsPool.toString());
	}
	

}
