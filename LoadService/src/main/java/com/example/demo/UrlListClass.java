package com.example.demo;

import java.beans.JavaBean;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UrlListClass {
	List<String> urlList=new ArrayList<String>();
	List<String> downUrl=new ArrayList<String>();
	public UrlListClass() {
		
		urlList.add("http://localhost:8080");
		urlList.add("http://localhost:8081");
		urlList.add("http://localhost:8082");
//		urlList.add("http://localhost:8082" + request.getRequestURI());
	}

	public List<String> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}
	
	public void addDownUrl(String url) {
		downUrl.add(url);
	}
	
	public void removeDownUrl(String url) {
		downUrl.remove(url);
	}
	
	
}
