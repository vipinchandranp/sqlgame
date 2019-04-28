package com.nuttron.sqlgame.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nuttron.sqlgame.service.EntityService;

@RestController
public class SQLGameController {

	@Autowired
	EntityService entityService;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public @ResponseBody List home(@RequestBody String query, HttpServletResponse response, HttpServletRequest request) throws Exception {
		List table = entityService.executeAndGetResult( query );
		return table;
	}
}
