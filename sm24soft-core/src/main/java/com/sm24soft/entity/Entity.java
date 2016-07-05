package com.sm24soft.entity;

public abstract class Entity implements IEntity {

	private String id;

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
