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

package com.liferay.ide.gradle.action;

import com.liferay.ide.core.ILiferayProjectProvider;
import com.liferay.ide.core.util.FileUtil;
import com.liferay.ide.core.util.ListUtil;
import com.liferay.ide.gradle.core.GradleUtil;
import com.liferay.ide.gradle.ui.GradleUI;
import com.liferay.ide.ui.action.AbstractObjectAction;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * @author Lovett Li
 * @author Terry Jia
 * @author Charles Wu
 * @author Simon Jiang
 */
public abstract class GradleTaskAction extends AbstractObjectAction {

	public GradleTaskAction() {
	}

	public void run(IAction action) {
		if (fSelection instanceof IStructuredSelection) {
			if (FileUtil.notExists(gradleBuildFile)) {
				return;
			}

			beforeAction();

			Job job = new Job(project.getName() + " - " + getGradleTask()) {

				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try {
						monitor.beginTask(getGradleTask(), 100);

						monitor.worked(20);

						GradleUtil.runGradleTask(project, getGradleTask(), monitor);

						monitor.worked(80);
					}
					catch (Exception e) {
						return GradleUI.createErrorStatus("Error running Gradle goal " + getGradleTask(), e);
					}

					return Status.OK_STATUS;
				}

			};

			job.addJobChangeListener(
				new JobChangeAdapter() {

					@Override
					public void done(IJobChangeEvent event) {
						try {
							project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());

							afterAction();
						}
						catch (CoreException ce) {
							GradleUI.logError(ce);
						}
					}

				});

			job.setProperty(ILiferayProjectProvider.LIFERAY_PROJECT_JOB, new Object());

			job.schedule();
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		super.selectionChanged(action, selection);

		if (fSelection instanceof IStructuredSelection) {
			Object[] elems = ((IStructuredSelection)fSelection).toArray();

			if (ListUtil.isNotEmpty(elems)) {
				Object elem = elems[0];

				if (elem instanceof IFile) {
					gradleBuildFile = (IFile)elem;

					project = gradleBuildFile.getProject();
				}
				else if (elem instanceof IProject) {
					project = (IProject)elem;

					gradleBuildFile = project.getFile("build.gradle");
				}
			}
		}
	}

	protected abstract String getGradleTask();

	protected IFile gradleBuildFile = null;
	protected IProject project = null;

}