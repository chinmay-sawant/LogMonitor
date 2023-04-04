package com.in.logMonitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "logmonitor")
@Data

public class PropConfig {
	private String projectname;
	private String logpath;
	private String linematcher;
	private String serverType;
}
