package com.nuttron.sqlgame.service;

import java.util.List;

import com.nuttron.sqlgame.entity.Left;
import com.nuttron.sqlgame.model.Table;

public interface EntityService {

	Left getEntity(String query) throws Exception;

	List<Object[]> executeAndGetResult(String query) throws Exception;

	Table getTable(String query) throws Exception;

}
