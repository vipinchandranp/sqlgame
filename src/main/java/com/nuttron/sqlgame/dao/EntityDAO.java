package com.nuttron.sqlgame.dao;

import java.util.List;

import com.nuttron.sqlgame.util.FilterNode;

public interface EntityDAO {

	@SuppressWarnings("rawtypes")
	List getEntity(FilterNode filterNode) throws Exception;

	FilterNode getFilterNodeFromEntity(Object entityObject, Object parentEntityObject,
			FilterNode childFilterNode) throws Exception;

	@SuppressWarnings("rawtypes")
	List getEntity(Object entityObject) throws Exception;

	List<Object[]> executeAndGetResult(String query) throws Exception;

}
