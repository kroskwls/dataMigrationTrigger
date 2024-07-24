package com.migration.jwt.handler;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.migration.jwt.response.ResponseBody;

@ControllerAdvice
public class CommonExceptionHandler {
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> RuntimeException(final RuntimeException ex) {
        ResponseBody body = ResponseBody.builder()
				.status(HttpServletResponse.SC_BAD_REQUEST)
				.error("Bad Request")
				.build();
        
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<?> NotFoundException(final NoHandlerFoundException  ex) {
        ResponseBody body = ResponseBody.builder()
				.status(HttpServletResponse.SC_NOT_FOUND)
				.error("Not Found")
				.build();
        
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> BadCredentialsException(final BadCredentialsException ex) {
        ResponseBody body = ResponseBody.builder()
				.status(HttpServletResponse.SC_UNAUTHORIZED)
				.error("Unauthorized")
				.build();
        
		return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<?> HttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException ex) {
        ResponseBody body = ResponseBody.builder()
				.status(HttpServletResponse.SC_NOT_FOUND)
				.error("Not Found")
				.build();
        
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> Exception(final Exception ex) {
        ResponseBody body = ResponseBody.builder()
				.status(HttpServletResponse.SC_BAD_REQUEST)
				.error("Bad Request")
				.build();
        
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
}
