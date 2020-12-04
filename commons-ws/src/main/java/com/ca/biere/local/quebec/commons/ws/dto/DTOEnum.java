package com.ca.biere.local.quebec.commons.ws.dto;

import java.util.List;

public class DTOEnum<T> {
	
	private List<T> enumList;
	
	public DTOEnum(List<T> enumList) {
		super();
		this.enumList = enumList;
	}

	public List<T> getEnumList() {
		return enumList;
	}

	public void setEnumList(List<T> enumList) {
		this.enumList = enumList;
	}
	
}
