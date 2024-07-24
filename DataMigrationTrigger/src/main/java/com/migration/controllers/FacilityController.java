package com.migration.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.migration.jwt.response.ResponseBody;
import com.migration.services.FacilityService;

@RestController
@RequestMapping("/api/v1/info")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FacilityController {
	@Autowired
	FacilityService service;
  
	@GetMapping("/facilities")
	public ResponseBody getFacilities() {
		return service.getFacilities();
	}

	@GetMapping("/facilities/{facilityName}")
	public ResponseBody getFacility(@PathVariable("facilityName") String facilityName) {
		return service.getFacility(facilityName);
	}
}
