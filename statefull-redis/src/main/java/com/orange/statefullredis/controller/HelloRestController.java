package com.orange.statefullredis.controller;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestController {

	@GetMapping("/")
	String uid(HttpSession session) {
		UUID uid = (UUID) session.getAttribute("uid");
		if (uid == null) {
			uid = UUID.randomUUID();
		}
		session.setAttribute("uid", uid);
		Integer index = 0;
		if (session.getAttribute("index") == null) {
			session.setAttribute("index", 0);
		}
		else {
			index = Integer.valueOf(String.valueOf((session.getAttribute("index"))));
			session.setAttribute("index", ++index);
		}
		String instance = "az";
		return "Session Id = " + uid.toString() + "br/> Instance Id =" + instance + "<br/> Key = counter, Value = " + index;
	}

}