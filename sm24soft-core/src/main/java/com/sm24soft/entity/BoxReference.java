package com.sm24soft.entity;

public class BoxReference extends Entity {

	private String name;
	private float weightOfOneBox;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getWeightOfOneBox() {
		return weightOfOneBox;
	}

	public void setWeightOfOneBox(float weightOfOneBox) {
		this.weightOfOneBox = weightOfOneBox;
	}

}
