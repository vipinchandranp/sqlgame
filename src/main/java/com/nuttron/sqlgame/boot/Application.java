package com.nuttron.sqlgame.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"com.nuttron.sqlgame.service",
		"com.nuttron.sqlgame.dao",
		"com.nuttron.sqlgame.config",
		"com.nuttron.sqlgame.controller",
		"com.nuttron.sqlgame.entity"

})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run( Application.class, args );
	}
}
