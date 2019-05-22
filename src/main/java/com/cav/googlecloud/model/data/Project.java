package com.cav.googlecloud.model.data;

import java.util.ArrayList;
import java.util.List;

public class Project {

	private Long id;
	private String name;
	private State state;
	private List<Field> fields = new ArrayList<Field>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", state=" + state + ", fields=" + fields + "]";
	}
}
