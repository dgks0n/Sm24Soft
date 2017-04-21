package com.sm24soft.entity;

import com.sm24soft.common.util.StringFormatUtil;

public class Entity implements IEntity {

	private String id;

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getIdWithPADZero() {
		return StringFormatUtil.formatString(8, getId());
	}

}
