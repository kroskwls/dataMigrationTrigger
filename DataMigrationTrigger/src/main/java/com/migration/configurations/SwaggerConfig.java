package com.migration.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.jsonwebtoken.lang.Arrays;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@OpenAPIDefinition(
	info = @Info(
		title = "성남시 로봇사업 데이터플랫폼", 
		description = "데이터플랫폼 API 연동 가이드", 
		version = "1.0"
	)
)
@Configuration
public class SwaggerConfig {
	@Bean
	@Profile("!Prod")
	public OpenAPI openAPI() {
		String jwtSchemeName = "Access Token";
		SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
		SecurityScheme securityScheme = new SecurityScheme()
			.name(jwtSchemeName)
			.type(SecurityScheme.Type.HTTP)
			.scheme("Bearer")
			.bearerFormat("JWT");
		Components components = new Components().addSecuritySchemes(jwtSchemeName, securityScheme);
		
		Server localServer = new Server();
		localServer.setDescription("Dataplatform API server");
		localServer.setUrl("http://127.0.0.1:8080");
		
		return new OpenAPI()
			.addSecurityItem(securityRequirement)
			.components(components)
			.servers(Arrays.asList(new Server[]{ localServer }));
	}
}
