package com.in.logMonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LogMonitorClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogMonitorClientApplication.class, args);
	}

}
