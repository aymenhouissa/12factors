package com.orange.provider.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.orange.provider.domain.Project;

@RestController
public class ProjectServiceController {

	private static Map<String, List<Project>> projectDB = new HashMap<String, List<Project>>();
	 
    static {
        projectDB = new HashMap<String, List<Project>>();
 
        List<Project> lst = new ArrayList<Project>();
        Project std = new Project("Project 1", "1.0.0");
        lst.add(std);
        std = new Project("Project 2", "1.1.0");
        lst.add(std);
 
        projectDB.put("DSIR", lst);
 
        lst = new ArrayList<Project>();
        std = new Project("Project 3", "2.5.6");
        lst.add(std);
        std = new Project("Project 4", "4.5.6");
        lst.add(std);
 
        projectDB.put("DSPIAR", lst);
 
    }
 
    @RequestMapping(value = "/department/{departmentname}/projects", method = RequestMethod.GET)
    public List<Project> getProjects(@PathVariable String departmentname) {
        System.out.println("Getting department details for " + departmentname);
 
        List<Project> projectList = projectDB.get(departmentname);
        if (projectList == null) {
            projectList = new ArrayList<Project>();
            Project std = new Project("Not Found", "N/A");
            projectList.add(std);
        }
        return projectList;
    }
}
