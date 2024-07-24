package com.migration.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.migration.jwt.response.ResponseBody;
import com.migration.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User Information", description = "User API")
@RestController
@RequestMapping("/api/v1/info")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
	@Autowired
	UserService service;

	@Operation(summary = "모든 사용자 정보 조회", description = "모든 사용자 정보 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success (AES256 decoded)", content = {
        	@Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"status\": 200,\"error\": null,\"data\": [{\"userId\":\"member-1\",\"name\":\"사용자명-1\",\"gender\":\"남자\",\"birth\":\"19991225\"\"phone\":\"010-0000-0000\",\"email\":\"email@domain.com\"}]}"))
    	}),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
        	@Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"status\": 401, \"error\": \"Unauthorized\", \"data\": null}"))
        }),
        @ApiResponse(responseCode = "403", description = "Bad Request", content = {
        	@Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"status\": 403, \"error\": \"Bad Request\", \"data\": null}"))
        }),
        @ApiResponse(responseCode = "404", description = "Not Found", content = {
        	@Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"status\": 404, \"error\": \"Not Found\", \"data\": null}"))
        }),
    })
	@GetMapping("/users")
	public ResponseBody getUsers() {
		return service.getUsers();
	}
	
	@Operation(summary = "특정 사용자 정보 조회", description = "id 기반 사용자 정보 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success (AES256 decoded)", content = {
        	@Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"status\": 200,\"error\": null,\"data\": {\"userId\":\"member-1\",\"name\":\"사용자명-1\",\"gender\":\"남자\",\"birth\":\"19991225\"\"phone\":\"010-0000-0000\",\"email\":\"email@domain.com\"}}"))
    	}),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
        	@Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"status\": 401, \"error\": \"Unauthorized\", \"data\": null}"))
        }),
        @ApiResponse(responseCode = "403", description = "Bad Request", content = {
        	@Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"status\": 403, \"error\": \"Bad Request\", \"data\": null}"))
        }),
        @ApiResponse(responseCode = "404", description = "Not Found", content = {
        	@Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"status\": 404, \"error\": \"Not Found\", \"data\": null}"))
        }),
    })
	@GetMapping("/users/{id}")
	public ResponseBody getUser(@PathVariable("id") String userId) {
		return service.getUser(userId);
	}
}
