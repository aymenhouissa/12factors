package com.orange.consumer.delegate;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ProjectServiceDelegate {

	@Autowired
	RestTemplate restTemplate;

	@Value("${project.service.url}")
	String projectServiceUrl;

	/**
	 * Using Histrix and fallback method
	 * @param departmentname
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "callProjectServiceAndGetData_Fallback")
	public String callProjectServiceAndGetData(String departmentname) {

		System.out.println("Getting department details for " + departmentname);

		String response = restTemplate.exchange(
				projectServiceUrl + "/department/{departmentname}/projects",
				HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
				}, departmentname).getBody();

		System.out.println("Response Received as " + response + " -  "
				+ new Date());

		return "NORMAL FLOW !!! - Department Name -  " + departmentname
				+ " :::  " + " Project Details " + response + " -  "
				+ new Date();
	}

	@SuppressWarnings("unused")
	private String callProjectServiceAndGetData_Fallback(String departmentname) {

		System.out
				.println("Project Service is down!!! fallback route enabled...");

		return "CIRCUIT BREAKER ENABLED!!! No Response From Project Service at this moment. "
				+ " Service will be back shortly - " + new Date();
	}
	
	
	/**
	 * Using Retryable
	 * @param departmentname
	 * @return
	 * @throws ResourceAccessException
	 */
	@Retryable(value = { ResourceAccessException.class }, maxAttempts = 2, backoff = @Backoff(delay = 5000))
	public String callProjectServiceAndGetDataWithRetry(String departmentname)
			throws ResourceAccessException {

		System.out.println("Getting department details for " + departmentname);

		String response = restTemplate.exchange(
				projectServiceUrl + "/department/{departmentname}/projects",
				HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
				}, departmentname).getBody();

		System.out.println("Response Received as " + response + " -  "
				+ new Date());

		return "NORMAL FLOW !!! - Department Name -  " + departmentname
				+ " :::  " + " Project Details " + response + " -  "
				+ new Date();
	}

	@Recover
	public String recover(ResourceAccessException e, String departmentname) {
		System.out
				.println("Project Service is down!!! fallback route enabled...");

		return "Recover function !!! No Response From Project Service at this moment. "
				+ " Service will be back shortly - " + new Date();
	}
	
	public String callProjectServiceAndGetDataWithRetryTemplate(String departmentname) {

		System.out.println("Getting department details for " + departmentname);

		String response = restTemplate.exchange(
				projectServiceUrl + "/department/{departmentname}/projects",
				HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
				}, departmentname).getBody();

		System.out.println("Response Received as " + response + " -  "
				+ new Date());

		return "NORMAL FLOW !!! - Department Name -  " + departmentname
				+ " :::  " + " Project Details " + response + " -  "
				+ new Date();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
