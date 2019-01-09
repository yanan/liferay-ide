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

package com.liferay.ide.gradle.ui;

import com.liferay.ide.core.IWorkspaceProject;
import com.liferay.ide.core.LiferayCore;
import com.liferay.ide.core.util.ListUtil;
import com.liferay.ide.gradle.core.LiferayGradleCore;
import com.liferay.ide.project.core.util.LiferayWorkspaceUtil;
import com.liferay.ide.ui.util.UIUtil;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.ide.IDE;

/**
 * @author Terry Jia
 */
public class LiferayWatchLabelProvider extends LabelProvider implements ILightweightLabelDecorator {

	@Override
	public void decorate(Object element, IDecoration decoration) {
		if (!(element instanceof IProject)) {
			return;
		}

		IProject workspaceProject = LiferayWorkspaceUtil.getWorkspaceProject();

		IWorkspaceProject iWorkspaceProject = LiferayCore.create(IWorkspaceProject.class, workspaceProject);

		IProject project = (IProject)element;

		if (iWorkspaceProject != null) {
			if (ListUtil.contains(iWorkspaceProject.watching(), project)) {
				decoration.addSuffix(" [watching]");
			}
			else {
				decoration.addSuffix("");
			}
		}
		else {
			String jobName = project.getName() + ":" + LiferayGradleCore.LIFERAY_WATCH;

			IJobManager jobManager = Job.getJobManager();

			Job[] jobs = jobManager.find(jobName);

			if (ListUtil.isNotEmpty(jobs)) {
				decoration.addSuffix(" [watching]");
			}
			else {
				decoration.addSuffix("");
			}
		}
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof IProject) {
			ISharedImages sharedImage = UIUtil.getSharedImages();

			return sharedImage.getImage(IDE.SharedImages.IMG_OBJ_PROJECT);
		}

		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof IProject) {
			IProject project = (IProject)element;

			return project.getName();
		}

		return null;
	}

}