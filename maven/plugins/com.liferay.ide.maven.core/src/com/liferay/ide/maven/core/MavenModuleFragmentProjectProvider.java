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

package com.liferay.ide.maven.core;

import com.liferay.ide.core.util.CoreUtil;
import com.liferay.ide.core.workspace.LiferayWorkspaceUtil;
import com.liferay.ide.project.core.NewLiferayProjectProvider;
import com.liferay.ide.project.core.modules.BladeCLI;
import com.liferay.ide.project.core.modules.fragment.NewModuleFragmentOp;
import com.liferay.ide.project.core.modules.fragment.NewModuleFragmentOpMethods;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.sapphire.modeling.Path;
import org.eclipse.sapphire.platform.PathBridge;

/**
 * @author Joye Luo
 * @author Simon Jiang
 */
public class MavenModuleFragmentProjectProvider
	extends LiferayMavenProjectProvider implements NewLiferayProjectProvider<NewModuleFragmentOp> {

	@Override
	public IStatus createNewProject(NewModuleFragmentOp op, IProgressMonitor monitor)
		throws CoreException, InterruptedException {

		IStatus retval = Status.OK_STATUS;

		String projectName = get(op.getProjectName());

		Path location = get(op.getLocation());

		String[] bsnAndVersion = NewModuleFragmentOpMethods.getBsnAndVersion(op);

		String bundleSymbolicName = bsnAndVersion[0];
		String version = bsnAndVersion[1];

		StringBuilder sb = new StringBuilder();

		sb.append("create ");
		sb.append("-q ");

		IProject liferayWorkspaceProject = LiferayWorkspaceUtil.getWorkspaceProject();

		if (liferayWorkspaceProject != null) {
			sb.append("--base \"");

			IPath workspaceLocation = liferayWorkspaceProject.getLocation();

			sb.append(workspaceLocation.toOSString());

			sb.append("\" ");
		}

		sb.append("-d \"");

		File file = location.toFile();

		sb.append(file.getAbsolutePath());

		sb.append("\" ");
		sb.append("-b ");
		sb.append("maven ");
		sb.append("-t ");
		sb.append("fragment ");

		if (!bundleSymbolicName.equals("")) {
			sb.append("-h ");
			sb.append(bundleSymbolicName);
			sb.append(" ");
		}

		if (!version.equals("")) {
			sb.append("-H ");
			sb.append(version);
			sb.append(" ");
		}

		sb.append("\"");
		sb.append(projectName);
		sb.append("\" ");

		try {
			BladeCLI.execute(sb.toString());
		}
		catch (Exception e) {
			return LiferayMavenCore.createErrorStatus("fail to create liferay module fragment project.", e);
		}

		NewModuleFragmentOpMethods.copyOverrideFiles(op);

		IPath l = PathBridge.create(location);

		IPath projectLocation = l.append(projectName);

		NewModuleFragmentOpMethods.storeRuntimeInfo(op);

		CoreUtil.openProject(projectName, projectLocation, monitor);

		MavenUtil.updateProjectConfiguration(projectName, projectLocation.toOSString(), monitor);

		return retval;
	}

}