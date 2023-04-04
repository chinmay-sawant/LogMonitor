package com.in.logMonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LogMonitorMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogMonitorMasterApplication.class, args);
	}

}
