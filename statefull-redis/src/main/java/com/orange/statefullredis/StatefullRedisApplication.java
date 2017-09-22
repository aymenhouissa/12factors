package com.orange.statefullredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

@SpringBootApplication
@EnableRedisHttpSession
public class StatefullRedisApplication extends AbstractHttpSessionApplicationInitializer {

	public static void main(String[] args) {
		SpringApplication.run(StatefullRedisApplication.class, args);
	}
	
	@Bean
	public static ConfigureRedisAction configureRedisAction() {
	    return ConfigureRedisAction.NO_OP;
	}
}
