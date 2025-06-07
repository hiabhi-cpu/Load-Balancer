package com.example.demo;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class LoadController {
	
	private final CloseableHttpClient client=HttpClients.createDefault();
	private int currentUrl=0;
	
	@GetMapping("/**")
	public ResponseEntity<?> proxy(HttpServletRequest request){
		List<String> urlList=new ArrayList<String>();
		urlList.add("http://localhost:8080" + request.getRequestURI());
		urlList.add("http://localhost:8081" + request.getRequestURI());
		
		String backendUrl=urlList.get(currentUrl);
		currentUrl=(currentUrl+1)%urlList.size();
		
	    System.out.println("Received request from: " + request.getRemoteAddr());
	    System.out.println(request.getProtocolRequestId());

	    HttpGet httpGet = new HttpGet(backendUrl);
	    try (CloseableHttpResponse response = client.execute(httpGet)) {
	        HttpEntity entity = response.getEntity();
	        String body = entity != null ? EntityUtils.toString(entity) : "";
//	        System.out.println(entity.getContent().readAllBytes());
	        return ResponseEntity.status(response.getStatusLine().getStatusCode()).body(body);
	    } catch (IOException e) {
	        return ResponseEntity.status(502).body("Failed to contact backend server.");
	    }
		
	}
	
}
