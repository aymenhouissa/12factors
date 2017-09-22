package com.orange.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.orange.consumer.delegate.ProjectServiceDelegate;

@RestController
public class DepartmentServiceController  {

	@Autowired
	ProjectServiceDelegate  projectServiceDelegate;
	
	@Autowired
	RetryTemplate retryTemplate;

	@RequestMapping(value = "/department/{departmentname}", method = RequestMethod.GET)
	public String getProjects(@PathVariable String departmentname) {
		System.out.println("Going to call project service to get data!");
		return projectServiceDelegate.callProjectServiceAndGetData(departmentname);
	}
	
	@RequestMapping(value = "/department/retry/{departmentname}", method = RequestMethod.GET)
	public String getProjectsWithRetry(@PathVariable String departmentname) {
		System.out.println("Going to call project service to get data!");
		return projectServiceDelegate.callProjectServiceAndGetDataWithRetry(departmentname);
	}
	
	@RequestMapping(value = "/department/retryTemplate/{departmentname}", method = RequestMethod.GET)
	public String getProjectsWithRetryTemplate(@PathVariable String departmentname) {
		
		
		return retryTemplate.execute(new RetryCallback<String, RuntimeException>() {
		    @Override
		    public String doWithRetry(RetryContext arg0) {
		    	return projectServiceDelegate.callProjectServiceAndGetDataWithRetryTemplate(departmentname);
		    }
		}, new RecoveryCallback<String>() {

			@Override
			public String recover(RetryContext context) throws Exception {
				return "Service is down, please try later";
			}
		});
	}
}
