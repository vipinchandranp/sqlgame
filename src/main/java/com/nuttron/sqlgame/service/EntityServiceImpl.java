package com.nuttron.sqlgame.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuttron.sqlgame.dao.EntityDAO;
import com.nuttron.sqlgame.entity.Left;
import com.nuttron.sqlgame.model.Table;

@Service
public class EntityServiceImpl implements EntityService {

	@Autowired
	EntityDAO entityDAO;

	@Override
	public Left getEntity(String query) throws Exception {
		Left left = new Left();
		left.setA( "one" );
		List leftEntities = entityDAO.getEntity( left );
		return (Left) leftEntities.get( 0 );
	}

	@Override
	public List<Object[]> executeAndGetResult(String query) throws Exception {
		return entityDAO.executeAndGetResult( query );
	}

	@Override
	public Table getTable(String query) throws Exception {
		Table table = new Table();
		List<Object[]> executeAndGetResult = entityDAO.executeAndGetResult( query );
		int i = 0;
		for ( Object[] objList : executeAndGetResult ) {
			ArrayList<String> colorRowList = new ArrayList<String>();
			for ( Object obj : objList ) {
				colorRowList.add( (String) obj );
			}
			table.getColorMap().put( String.valueOf( i ), colorRowList );
			i++;
		}
		return table;
	}

}
