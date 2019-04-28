package com.nuttron.sqlgame.util;

import javax.persistence.metamodel.SingularAttribute;

public class Expression {

	@SuppressWarnings("rawtypes")
	private SingularAttribute propertyName;

	private Operator operator;

	private Object valueObject;

	@SuppressWarnings("rawtypes")
	public SingularAttribute getPropertyName() {
		return propertyName;
	}

	@SuppressWarnings("rawtypes")
	public void setPropertyName(SingularAttribute propertyName) {
		this.propertyName = propertyName;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public Object getValueObject() {
		return valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}

}
