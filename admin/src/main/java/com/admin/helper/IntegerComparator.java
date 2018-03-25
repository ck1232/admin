package com.admin.helper;

import java.util.Comparator;

public class IntegerComparator implements Comparator<Object> {
	private String variableName;
	private int inverseInt = 1;
	public IntegerComparator(String variableName) {
		this.variableName = variableName;
	}
	
	public IntegerComparator(String variableName, boolean inverse) {
		this.variableName = variableName;
		if(inverse){
			inverseInt = -1;
		}
	}

	@Override
	public int compare(Object o1, Object o2) {
		Integer v1 = (Integer)GeneralUtils.getObjectProprty(o1, variableName);
		Integer v2 = (Integer)GeneralUtils.getObjectProprty(o2, variableName);
		if(v1 != null && v2 != null){
			return v1.compareTo(v2)*inverseInt;
		}else if(v1 != null){
			return -1 * inverseInt;
		}else if(v2 != null){
			return 1 * inverseInt;
		}
		return 0;
	}

}
