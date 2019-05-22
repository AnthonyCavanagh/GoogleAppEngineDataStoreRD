package com.cav.googlecloud.model.data;

public class ProjectKey {

	private Long projectId;
	private Long stateId;
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	@Override
	public String toString() {
		return "StateKey [projectId=" + projectId + ", stateId=" + stateId + "]";
	}
}
