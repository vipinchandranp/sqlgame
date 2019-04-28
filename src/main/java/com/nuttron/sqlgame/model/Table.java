package com.nuttron.sqlgame.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Vipin
 */
public class Table {

	private HashMap<String, ArrayList<String>> colorMap = new HashMap<String, ArrayList<String>>();

	public HashMap<String, ArrayList<String>> getColorMap() {
		return colorMap;
	}

	public void setColorMap(HashMap<String, ArrayList<String>> colorMap) {
		this.colorMap = colorMap;
	}

}
