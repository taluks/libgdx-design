package com.libgdxdesign.core.proxy;

import com.libgdxdesign.core.model.Project;
import org.puremvc.java.patterns.proxy.Proxy;

public class ProjectProxy extends Proxy {

    public static final String NAME = ProjectProxy.class.getSimpleName();

    private final Project project = new Project();

    public ProjectProxy() {
        super(NAME);
    }

    public Project getProject() {
        return project;
    }
}
