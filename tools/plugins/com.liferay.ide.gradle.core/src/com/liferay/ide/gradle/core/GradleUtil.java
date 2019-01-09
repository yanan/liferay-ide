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

import com.liferay.ide.core.ILiferayProjectProvider;
import com.liferay.ide.core.util.CoreUtil;
import com.liferay.ide.core.util.FileUtil;
import com.liferay.ide.gradle.core.parser.GradleDependency;
import com.liferay.ide.gradle.core.parser.GradleDependencyUpdater;

import java.io.IOException;

import java.util.List;
import java.util.Optional;

import org.codehaus.groovy.control.MultipleCompilationErrorsException;

import org.eclipse.buildship.core.BuildConfiguration;
import org.eclipse.buildship.core.BuildConfiguration.BuildConfigurationBuilder;
import org.eclipse.buildship.core.GradleBuild;
import org.eclipse.buildship.core.GradleCore;
import org.eclipse.buildship.core.GradleDistribution;
import org.eclipse.buildship.core.GradleWorkspace;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import org.gradle.tooling.CancellationTokenSource;
import org.gradle.tooling.GradleConnector;

import org.osgi.framework.Version;

/**
 * @author Andy Wu
 * @author Lovett Li
 * @author Charles Wu
 * @author Simon Jiang
 */
public class GradleUtil {

	public static boolean isBuildFile(IFile buildFile) {
		if (FileUtil.exists(buildFile) && "build.gradle".equals(buildFile.getName()) &&
			buildFile.getParent() instanceof IProject) {

			return true;
		}

		return false;
	}

	public static boolean isGradleProject(Object resource) throws CoreException {
		IProject project = null;

		if (resource instanceof IFile) {
			project = ((IFile)resource).getProject();
		}
		else if (resource instanceof IProject) {
			project = (IProject)resource;
		}

		return project.hasNature("org.eclipse.buildship.core.gradleprojectnature");
	}

	public static boolean isWatchableProject(IFile buildFile) {
		if (FileUtil.notExists(buildFile)) {
			return false;
		}

		boolean watchable = false;

		try {
			GradleDependencyUpdater updater = new GradleDependencyUpdater(buildFile);

			List<GradleDependency> dependencies = updater.getAllBuildDependencies();

			for (GradleDependency dependency : dependencies) {
				String group = dependency.getGroup();
				String name = dependency.getName();
				Version version = new Version("0");
				String dependencyVersion = dependency.getVersion();

				try {
					if ((dependencyVersion != null) && !dependencyVersion.equals("")) {
						version = Version.parseVersion(dependencyVersion);
					}

					if ("com.liferay".equals(group) && "com.liferay.gradle.plugins".equals(name) &&
						(CoreUtil.compareVersions(version, new Version("3.11.0")) >= 0)) {

						watchable = true;

						break;
					}

					if ("com.liferay".equals(group) && "com.liferay.gradle.plugins.workspace".equals(name) &&
						(CoreUtil.compareVersions(version, new Version("1.9.2")) >= 0)) {

						watchable = true;

						break;
					}
				}
				catch (IllegalArgumentException iae) {
				}
			}
		}
		catch (IOException ioe) {
		}
		catch (MultipleCompilationErrorsException mcee) {
		}

		return watchable;
	}

	public static void refreshProject(IProject project) {
		sychronizeProject(project.getLocation(), new NullProgressMonitor());
	}

	public static void runGradleTask(IProject project, String task, IProgressMonitor monitor) throws CoreException {
		CancellationTokenSource cancellationTokenSource = GradleConnector.newCancellationTokenSource();

		runGradleTask(project, new String[] {task}, new String[0], cancellationTokenSource, monitor);
	}

	public static void runGradleTask(
			IProject project, String[] tasks, String[] arguments, CancellationTokenSource cancellationTokenSource,
			IProgressMonitor monitor)
		throws CoreException {

		if ((project == null) || (project.getLocation() == null)) {
			return;
		}

		GradleWorkspace workspace = GradleCore.getWorkspace();

		Optional<GradleBuild> gradleBuildOpt = workspace.getBuild(project);

		GradleBuild gradleBuild = gradleBuildOpt.get();

		try {
			gradleBuild.withConnection(
				connection -> {
					connection.newBuild(
					).addArguments(
						arguments
					).forTasks(
						tasks
					).withCancellationToken(
						cancellationTokenSource.token()
					).run();

					return null;
				},
				monitor);
		}
		catch (Exception e) {
			LiferayGradleCore.logError(e);
		}
	}

	public static IStatus sychronizeProject(IPath dir, IProgressMonitor monitor) {
		if (FileUtil.notExists(dir)) {
			return LiferayGradleCore.createErrorStatus("Unable to find gradle project at " + dir);
		}

		BuildConfigurationBuilder gradleBuilder = BuildConfiguration.forRootProjectDirectory(dir.toFile());

		gradleBuilder.overrideWorkspaceConfiguration(true);
		gradleBuilder.autoSync(true);
		gradleBuilder.buildScansEnabled(false);
		gradleBuilder.gradleDistribution(GradleDistribution.fromBuild());
		gradleBuilder.showConsoleView(true);
		gradleBuilder.showExecutionsView(true);

		BuildConfiguration configuration = gradleBuilder.build();

		GradleWorkspace workspace = GradleCore.getWorkspace();

		GradleBuild gradleBuild = workspace.createBuild(configuration);

		Job synchronizeJob = new Job("Liferay sychronized project job") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				gradleBuild.synchronize(monitor);

				return Status.OK_STATUS;
			}

		};

		synchronizeJob.setProperty(ILiferayProjectProvider.LIFERAY_PROJECT_JOB, new Object());

		synchronizeJob.setProgressGroup(monitor, IProgressMonitor.UNKNOWN);

		synchronizeJob.schedule();

		return Status.OK_STATUS;
	}

}