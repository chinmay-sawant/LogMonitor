package com.in.logMonitor.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.in.logMonitor.config.PropConfig;

import lombok.Data;

@Component
@Data
public class FileService {

	@Autowired
	PropConfig propConfig;

	Map<String, List<String>> todaysError = new HashMap<String, List<String>>();

	public FileService(PropConfig propConfig) {
		this.propConfig = propConfig;
	}

	/*
	 * returns boolean and saves the output in arrayList which will be accessed via
	 * class vars
	 */
	public boolean openFile(String filepath) throws IOException {
		File file = new File(filepath);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String currentLine;
		boolean anyErrorFound = false;
		boolean errorFound = false;
		boolean statementText = false;
		List<String> erroredList = new ArrayList<String>();
		erroredList.clear();
		todaysError.clear();

		if (propConfig.getServerType().equalsIgnoreCase("POSTGRES")) {
			while ((currentLine = br.readLine()) != null) {
				String[] splitLine = currentLine.split(" ");
				try {

					if (splitLine[9].contains(propConfig.getLinematcher())
							|| splitLine[10].contains(propConfig.getLinematcher())
							|| splitLine[11].contains(propConfig.getLinematcher())) {
						erroredList.add(currentLine.toString());
						todaysError.put("POSTGRES", erroredList);
						errorFound = true;
						anyErrorFound = true;
					}

					if (errorFound && splitLine[9].contains("STATEMENT")
							|| errorFound && splitLine[10].contains("STATEMENT")
							|| errorFound && splitLine[11].contains("STATEMENT")) {
						statementText = true;
						erroredList.add(currentLine.toString());

					}

					if (errorFound && splitLine[9].contains("LOG") || errorFound && splitLine[10].contains("LOG")
							|| errorFound && splitLine[11].contains("LOG")) {
						errorFound = false;
						statementText = false;
						todaysError.put("POSTGRES", erroredList);
					}

				} catch (Exception e) {
					if (errorFound && statementText) {
						erroredList.add(currentLine.toString().trim());
					}
				}
			}

		}

		else if (propConfig.getServerType().equalsIgnoreCase("APACHE")) {
			while ((currentLine = br.readLine()) != null) {
				try {
					String[] splitLine = currentLine.split(" ");
					if (splitLine[3].contains(propConfig.getLinematcher())) {
						erroredList.add(currentLine.toString());
						todaysError.put("APACHE", erroredList);
						errorFound = true;
						anyErrorFound = true;
					}


					if (errorFound && splitLine[3].contains("INFO") || errorFound && splitLine[3].contains("WARN")) {
						errorFound = false;
						todaysError.put("APACHE", erroredList);
					}

				} catch (Exception e) {
					if (errorFound) {
						erroredList.add("    " + currentLine.toString().trim());
					}
				}

			}
		}

		br.close();

		return anyErrorFound;

	}

}
