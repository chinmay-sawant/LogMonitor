package com.in.logMonitor.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in.logMonitor.models.LogMonitorDataDB;
import com.in.logMonitor.services.LogMonitorDBService;

@RestController
@RequestMapping(value = "/errors/")
public class LogMonitorController {

	LogMonitorDBService logMonitorDBService;

	public LogMonitorController(LogMonitorDBService logMonitorDBService) {
		this.logMonitorDBService = logMonitorDBService;
	}

	@GetMapping(value = "/getAllValues")
	public List<LogMonitorDataDB> getAllErrors() {
		
		List<LogMonitorDataDB> allDataError = logMonitorDBService.getAllErrors();
		for (LogMonitorDataDB dataDB : allDataError) {
			Integer id = dataDB.getId();
			Link selfLink = linkTo(LogMonitorController.class).slash(id).withRel("GetByID");
			dataDB.add(selfLink);
		}
		return allDataError;

	}

	@GetMapping(value = "/{id}")
	public EntityModel<LogMonitorDataDB> getById(@PathVariable("id") Integer id) {
		EntityModel<LogMonitorDataDB> resource = EntityModel.of(logMonitorDBService.getSingleError(id));
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllErrors());
		resource.add(link.withRel("all-errors"));
		return resource;
	}

}
