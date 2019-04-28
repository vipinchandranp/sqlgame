package com.nuttron.sqlgame.util;

import java.util.Arrays;

public enum Operator {

	EQUAL("EQUAL"), NOT_EQUAL("NOT_EQUAL"), CONTAINS("CONTAINS"), NOT_CONTAINS("NOT_CONTAINS"), IN("IN"), NOT_IN(
			"NOT_IN"), BEGINS_WITH("BEGINS_WITH"), ENDS_WITH("ENDS_WITH");

	private String value;

	private Operator(String value) {
		this.value = value;
	}

	public static Operator fromValue(String value) {
		for (Operator operaor : values()) {
			if (operaor.value.equalsIgnoreCase(value)) {
				return operaor;
			}
		}
		throw new IllegalArgumentException(
				"Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
	}
}
