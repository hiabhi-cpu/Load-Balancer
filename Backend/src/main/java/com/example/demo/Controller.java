package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class Controller {

	@GetMapping("/*")
	public ResponseEntity<String> hello(HttpServletRequest request) {
		
		System.out.println(request.getRequestURI());
		System.out.println(request.getRemoteAddr());
		return new ResponseEntity<String>("Hello From Backend ServerHello From Backend Server",HttpStatus.OK);
	}
	
}
