package com.libgdxdesign.manager;

import com.libgdxdesign.model.Project;

public class ProjectManager {

	private Project project = new Project();

	public ProjectManager() {

	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
