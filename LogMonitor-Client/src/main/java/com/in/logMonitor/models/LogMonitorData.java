package com.in.logMonitor.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogMonitorData {
	private String ipaddress;
	private String projectName;
	private String logname;
	private List<String> data;
	private String currentTimestamp;

}

