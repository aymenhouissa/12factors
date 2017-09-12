package com.orange.consumer.delegate;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ProjectServiceDelegate {

	@Autowired
    RestTemplate restTemplate;
	
	@Value("${project.service.url}")
	String projectServiceUrl;
     
    @HystrixCommand(fallbackMethod = "callProjectServiceAndGetData_Fallback")
    public String callProjectServiceAndGetData(String departmentname) {
 
        System.out.println("Getting department details for " + departmentname);
 
        String response = restTemplate
                .exchange(projectServiceUrl + "/department/{departmentname}/projects"
                , HttpMethod.GET
                , null
                , new ParameterizedTypeReference<String>() {
            }, departmentname).getBody();
 
        System.out.println("Response Received as " + response + " -  " + new Date());
 
        return "NORMAL FLOW !!! - Department Name -  " + departmentname + " :::  " +
                    " Project Details " + response + " -  " + new Date();
    }
     
    @SuppressWarnings("unused")
    private String callProjectServiceAndGetData_Fallback(String departmentname) {
 
        System.out.println("Project Service is down!!! fallback route enabled...");
 
        return "CIRCUIT BREAKER ENABLED!!! No Response From Project Service at this moment. " +
                    " Service will be back shortly - " + new Date();
    }
 
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
