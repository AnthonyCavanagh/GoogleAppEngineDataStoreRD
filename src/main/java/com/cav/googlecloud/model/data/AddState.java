package com.cav.googlecloud.model.data;

public class AddState {

	private Long projectId;
	private State state;
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "AddState [projectId=" + projectId + ", state=" + state + "]";
	}

}
