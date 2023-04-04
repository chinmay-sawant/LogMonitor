package com.in.logMonitor.models;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "TodaysLogData", schema = "thrdexams")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogMonitorDataDB extends RepresentationModel<LogMonitorDataDB> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column(name = "ipaddress")
	private String ipaddress;

	@Column(name = "projectname")
	private String projectName;

	@Column(name = "logname")
	private String logname;

	// @ElementCollection
	@Column(length = 1000000000)
	private List<String> data;

	@Column(name = "Timestamp")
	private String currentTimestamp;

}

