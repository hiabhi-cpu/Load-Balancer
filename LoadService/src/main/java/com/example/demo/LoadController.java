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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class LoadController {

	UrlListClass urlListClass=new UrlListClass();
	private final CloseableHttpClient client=HttpClients.createDefault();
	private int currentUrl=0;
	
	public LoadController() {
		// TODO Auto-generated constructor stub
		Runnable r = new Runnable() {
	         public void run() {
	             runYourBackgroundTaskHere();
	         }
	     };
	     new Thread(r).start();
	}
	
	@GetMapping("/**")
	public ResponseEntity<?> proxy(HttpServletRequest request){
		
		List<String> urlList=urlListClass.urlList;
	
		
		if(urlListClass.downUrl.size()==urlListClass.urlList.size()) {
			return ResponseEntity.status(502).body("No backend servers are up");
		}
		
		String backendUrl=urlList.get(currentUrl);
		
		if(urlListClass.downUrl.size()!=0) {
			while(urlListClass.downUrl.contains(backendUrl)) {
				currentUrl=(currentUrl+1)%urlList.size();
				backendUrl=urlList.get(currentUrl);
			}
		}
		
		currentUrl=(currentUrl+1)%urlList.size();
		
		
		
	    System.out.println("Received request from: " + request.getRemoteAddr());
	    System.out.println(request.getProtocolRequestId());

	    HttpGet httpGet = new HttpGet(backendUrl+request.getRequestURI());
	    try (CloseableHttpResponse response = client.execute(httpGet)) {
	        HttpEntity entity = response.getEntity();
	        String body = entity != null ? EntityUtils.toString(entity) : "";
//	        System.out.println(entity.getContent().readAllBytes());
	        return ResponseEntity.status(response.getStatusLine().getStatusCode()).body(body);
	    } catch (IOException e) {
	        return ResponseEntity.status(502).body("Failed to contact backend server.");
	    }
		
	}

	
	protected void runYourBackgroundTaskHere() {
		while(true) {
			
			try {
				
				for(String urlString:urlListClass.getUrlList()) {
					System.out.println("Health checking for: "+urlString);
					if(getStatus(urlString)!=200) {
						urlListClass.addDownUrl(urlString);
						System.out.println("Down");
					}else {
						urlListClass.removeDownUrl(urlString);
						System.out.println("Up");
					}
					
				}
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		// TODO Auto-generated method stub
		
	private  int getStatus(String backendUrl) {
		HttpGet httpGet = new HttpGet(backendUrl);
	    try (CloseableHttpResponse response = client.execute(httpGet)) {
	        return response.getStatusLine().getStatusCode();
	    } catch (IOException e) {
	        return 502;
	    }
	}
	
	
}
