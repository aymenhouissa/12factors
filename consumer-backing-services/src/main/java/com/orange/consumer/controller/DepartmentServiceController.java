package com.orange.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.orange.consumer.delegate.ProjectServiceDelegate;

@RestController
public class DepartmentServiceController  {

	@Autowired
	ProjectServiceDelegate  projectServiceDelegate;

	@RequestMapping(value = "/department/{departmentname}", method = RequestMethod.GET)
	public String getProjects(@PathVariable String departmentname) {
		System.out.println("Going to call project service to get data!");
		return projectServiceDelegate.callProjectServiceAndGetData(departmentname);
	}
}
