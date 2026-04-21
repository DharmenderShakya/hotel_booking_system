package com.hotel_booking_system.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hotel_booking_system.response.ApiResponse;

import jakarta.persistence.NonUniqueResultException;
import jakarta.servlet.http.HttpServletRequest;


@RestControllerAdvice
public class ControllerAdviser extends ResponseEntityExceptionHandler {

	private static final Logger logger=LoggerFactory.getLogger(ControllerAdviser.class);
	
	 String timestamp = java.time.OffsetDateTime.now().toString();
	
	 private ResponseEntity<ApiResponse<Object>> buildResponse(String message, HttpStatus status) {
	        ApiResponse<Object> response = new ApiResponse<>(
	                false,
	                message,
	                null,
	                status.value(),
	                timestamp
	        );
	        return ResponseEntity.status(status).body(response);
	    }
	
	
	@ExceptionHandler(UnauthoriseException.class)
	public ResponseEntity<?> unAuthorisedException(UnauthoriseException unauthor,WebRequest weRequest,HttpServletRequest hRequest){
			
		logger.error("Loggin Error {} ",hRequest.getRequestURL(),unauthor);
		
		return buildResponse(unauthor.getMessage(), HttpStatus.FORBIDDEN);
		
	}
	
	@ExceptionHandler(NonUniqueResultException.class)
	public ResponseEntity<?> nonUniqueResultExceptionHandler(NonUniqueResultException unique,WebRequest request,HttpServletRequest request2){
		
		logger.error("Loggin Error {} ",request2.getRequestURL(),unique.getMessage());
		
		return buildResponse(unique.getMessage(), HttpStatus.BAD_REQUEST);
		
	}
	
	 @ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> badCredentialExceptionHandler(BadCredentialsException badCreden,WebRequest seRequest,HttpServletRequest httRequest){
		
		logger.error("Loggin Error {} ",httRequest.getRequestURL(),badCreden.getMessage());
		
		return buildResponse(badCreden.getMessage(), HttpStatus.UNAUTHORIZED);
		
	}
	
	 @ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> badRequestExceptionHandler(BadRequestException baException,WebRequest weRequest,HttpServletRequest request){
				
		logger.error("Loggin Error {} ",request.getRequestURL(),baException.getMessage());
		
		return buildResponse(baException.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	 @ExceptionHandler(Exception.class)
	public ResponseEntity<?> mainExceptionHandler(Exception baException,WebRequest weRequest,HttpServletRequest request){
		
		Map<String, Object> body=new LinkedHashMap<String,Object>();
		
		body.put("timeStamp", LocalDateTime.now());
		
		body.put("message", "Server error, Please contact IT Team");
		
		logger.error("Loggin Error {} ",request.getRequestURL(),baException.getMessage());
		
		return buildResponse(body.get("message").toString(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex,HttpServletRequest request,WebRequest weRequest) {
		
		logger.error("Loggin Error {} ",request.getRequestURL(),ex.getMessage());
		
		return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleService(ServiceException ex,WebRequest weRequest,HttpServletRequest request) {
		
		logger.error("Loggin Error {} ",request.getRequestURL(),ex.getMessage());
		
		return buildResponse(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        FieldError error=ex.getBindingResult().getFieldErrors().get(0);
        
        ApiResponse<Object> response = new ApiResponse<>(
                false,
                error.getDefaultMessage(),
                null,
                HttpStatus.BAD_REQUEST.value(),
                timestamp
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
  
	
}
