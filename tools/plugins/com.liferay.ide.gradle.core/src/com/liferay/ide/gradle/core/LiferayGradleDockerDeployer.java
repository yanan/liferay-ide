/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.ide.gradle.core;

import com.liferay.ide.project.core.util.LiferayWorkspaceUtil;
import com.liferay.ide.server.core.portal.docker.IDockerPublisher;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.IServer;

/**
 * @author Simon Jiang
 */
public class LiferayGradleDockerDeployer implements IDockerPublisher {

	public LiferayGradleDockerDeployer() {
	}

	@Override
	public boolean canPublishModule(IServer server, IModule module) {
		IProject project = module.getProject();

		boolean inLiferayWorkspace = LiferayWorkspaceUtil.inLiferayWorkspace(project);

		boolean gradleProject = GradleUtil.isGradleProject(project);

		if (inLiferayWorkspace && gradleProject) {
			return true;
		}

		return false;
	}

	@Override
	public void dockerDeploy(IProject project, IProgressMonitor monitor) {
		try {
			GradleUtil.runGradleTask(project, new String[] {"dockerDeploy"}, monitor);
		}
		catch (Exception e) {
			LiferayGradleCore.logError(e);
		}
	}

}