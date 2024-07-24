package com.migration.jwt.handler;

import java.io.IOException;
import java.security.SignatureException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.migration.jwt.response.ResponseBody;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException
    ) throws IOException, ServletException {
    	Exception exception = (Exception) request.getAttribute("exception");
    	
        int status = HttpServletResponse.SC_UNAUTHORIZED;
        String error = "Unauthorized";
        String message = "Authorization Required";
        
		if (exception instanceof SignatureException
    		|| exception instanceof MalformedJwtException
    		|| exception instanceof UnsupportedJwtException
    		|| exception instanceof IllegalArgumentException
    		|| exception instanceof ExpiredJwtException
		) {
			status = HttpServletResponse.SC_NOT_FOUND;
			error = "Not Found";
			message = "Not Found";
        } 
        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status);
        
        ResponseBody body = ResponseBody.builder()
                .status(status)
                .error(message)
                .build();
        
        if (exception != null) {
        	log.error(exception.getMessage());
        	log.error("{}({}) error: {}", status, error, message);
        }
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
