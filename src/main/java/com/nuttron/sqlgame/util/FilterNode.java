package com.nuttron.sqlgame.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

public class FilterNode {

	@PersistenceContext
	EntityManager em;

	@SuppressWarnings("rawtypes")
	private Class entityClass;

	private ArrayList<Expression> expressions = new ArrayList<Expression>();

	@SuppressWarnings("rawtypes")
	private ArrayList<SetAttribute> childFields = new ArrayList<SetAttribute>();

	@SuppressWarnings("rawtypes")
	private ArrayList<SingularAttribute> siblingFields = new ArrayList<SingularAttribute>();

	private HashMap<Object, FilterNode> childNodeMap = new HashMap<Object, FilterNode>();

	public FilterNode(Class<?> className, EntityManager em) {
		this.entityClass = className;
		this.em = em;
	}

	@SuppressWarnings("rawtypes")
	public ArrayList<SetAttribute> getChildFields() {
		return childFields;
	}

	@SuppressWarnings("rawtypes")
	public void setChildFields(ArrayList<SetAttribute> childFields) {
		this.childFields = childFields;
	}

	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		return entityClass;
	}

	@SuppressWarnings("rawtypes")
	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}

	public ArrayList<Expression> getExpressions() {
		return expressions;
	}

	public void setExpressions(ArrayList<Expression> expressions) {
		this.expressions = expressions;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addCondition(String propertyName, Operator operator, Object valueObj) {
		Expression e = new Expression();
		EntityType entity = em.getMetamodel().entity( this.entityClass );
		e.setPropertyName( entity.getSingularAttribute( propertyName ) );
		e.setOperator( operator );
		e.setValueObject( valueObj );
		getExpressions().add( e );

	}

	@SuppressWarnings("rawtypes")
	public void addChild(SetAttribute child, FilterNode clientAccountRelationshipNode) {
		getChildFields().add( child );
		getChildNodeMap().put( child, clientAccountRelationshipNode );
	}

	@SuppressWarnings("rawtypes")
	public ArrayList<SingularAttribute> getSiblingFields() {
		return siblingFields;
	}

	@SuppressWarnings("rawtypes")
	public void setSiblingFields(ArrayList<SingularAttribute> siblingFields) {
		this.siblingFields = siblingFields;
	}

	@SuppressWarnings("rawtypes")
	public void addSibling(SingularAttribute singularAttribute, FilterNode clientEmploymentRelationship) {
		getSiblingFields().add( singularAttribute );
		getChildNodeMap().put( singularAttribute, clientEmploymentRelationship );
	}

	public HashMap<Object, FilterNode> getChildNodeMap() {
		return childNodeMap;
	}

	public void setChildNodeMap(HashMap<Object, FilterNode> childNodeMap) {
		this.childNodeMap = childNodeMap;
	}
}
