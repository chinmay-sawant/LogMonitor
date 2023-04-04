package com.in.logMonitor.bean;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.in.logMonitor.config.PropConfig;
import com.in.logMonitor.models.LogMonitorData;
import com.in.logMonitor.services.FileService;
import com.in.logMonitor.services.RabbitMQSender;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FileBean {

	@Autowired
	PropConfig propConfig;

	@Autowired
	FileService fileService;

	@Autowired
	RabbitMQSender rabbitMQSender;

	String ipaddress;

	@Value("${logmonitor.projectname}")
	String projectname;

	Map<String, List<String>> last_todaysError = new HashMap<String, List<String>>();
	List<String> blankArray = new ArrayList<String>();

	@Bean
	public void setIpAddress() {
		try {
			ipaddress = InetAddress.getLocalHost().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FileBean(PropConfig propConfig, FileService fileService) {
		this.propConfig = propConfig;
		this.fileService = fileService;
	}

//@Scheduled(initialDelay = 20000L, fixedRate = 60000L)
	// Below function is for reading logs
	@Scheduled(fixedRateString = "${logmonitor.interval}")
	public void logCopyAndCheck() throws IOException, InterruptedException {

		Map<String, List<String>> todaysError = new HashMap<String, List<String>>();

		Date date = new Date();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		final String strDate = formatter.format(date).toString();
		String logPath = propConfig.getLogpath().toString();

		log.info("Root path to the log folder => {}", logPath);

		Set<String> listOfFiles = Stream.of(new File(propConfig.getLogpath()).listFiles())
				.filter(file -> !file.isDirectory()).map(File::getAbsolutePath).collect(Collectors.toSet());

		Set<String> FilteredlistOfFiles = listOfFiles.stream().filter(p -> p.contains(strDate))
				.collect(Collectors.toSet());

		// Static Adding Project File because we are already adding date files in the
		// filtered array on above line (This is added for APACHE)

		if (propConfig.getServerType().equalsIgnoreCase("APACHE")) {
			FilteredlistOfFiles
					.add(new File(propConfig.getLogpath()).toString() + "\\" + propConfig.getProjectname() + ".log");
		}
		for (String filepath : FilteredlistOfFiles) {

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formattedTimestamp = dateFormat.format(timestamp);
			log.info("Processing file => {}", filepath);
			String tempLogPath = "D:\\LogMonitor\\tempLogPath";
			Files.createDirectories(Paths.get(tempLogPath));
			String currentFileName = FilenameUtils.getName(filepath).toString();
			org.apache.commons.io.FileUtils.copyFile(new File(filepath),
					new File(tempLogPath + "\\" + currentFileName));

			try {
				/*
				 * reading the file using fileservice if any errors are found they will be
				 * posted to the rabbitMQ server
				 */
				if (fileService.openFile(filepath)) {

					todaysError = fileService.getTodaysError();
					if (!last_todaysError.containsKey(currentFileName)) {
						last_todaysError.put(currentFileName, blankArray);
					}

					for (Map.Entry<String, List<String>> entry : todaysError.entrySet()) {
						if (!last_todaysError.get(currentFileName).equals(entry.getValue())) {

							rabbitMQSender.send(new LogMonitorData(ipaddress, projectname, currentFileName,
									entry.getValue(), formattedTimestamp));
							last_todaysError.put(currentFileName, entry.getValue());
						} else {
							log.info("No New Error Found !! => {}", formattedTimestamp);
						}
					}

				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
