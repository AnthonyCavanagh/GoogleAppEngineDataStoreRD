package com.cav.googlecloud.model.data;

import java.util.ArrayList;
import java.util.List;

public class AddFields {
	
	private long id;
	private List <Field> fields = new ArrayList<Field>();
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	@Override
	public String toString() {
		return "AddFields [id=" + id + ", fields=" + fields + "]";
	}
}
