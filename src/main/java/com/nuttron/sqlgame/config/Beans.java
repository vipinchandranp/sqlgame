package com.nuttron.sqlgame.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.nuttron.sqlgame.dao.EntityDAO;
import com.nuttron.sqlgame.dao.EntityDAOImpl;
import com.nuttron.sqlgame.service.EntityService;
import com.nuttron.sqlgame.service.EntityServiceImpl;

@Configuration
@Component
@ComponentScan(basePackages = {
		"com.nuttron.sqlgame.service",
		"com.nuttron.sqlgame.dao",
		"com.nuttron.sqlgame.config",
		"com.nuttron.sqlgame.controller",
		"com.nuttron.sqlgame.entity"

})
public class Beans {

/*	@Bean
	public EntityService getEntityService() {
		return new EntityServiceImpl();
	}

	@Bean
	public EntityDAO getEntityDAO() {
		return new EntityDAOImpl();
	}*/
}
