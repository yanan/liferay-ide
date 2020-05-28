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

package com.liferay.ide.gradle.core.tests;

import com.liferay.ide.core.IBundleProject;
import com.liferay.ide.core.LiferayCore;
import com.liferay.ide.gradle.core.tests.base.NewModuleGradleBase;
import com.liferay.ide.project.core.modules.NewLiferayModuleProjectOp;

import org.eclipse.core.resources.IProject;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Joye Luo
 * @author Terry Jia
 */
public class NewModuleWarGradleTests extends NewModuleGradleBase {

	@Test
	public void createLayoutTemplate() {
		NewLiferayModuleProjectOp op = NewLiferayModuleProjectOp.TYPE.instantiate();

		op.setProjectName(project.getName());
		op.setProjectProvider(provider());
		op.setProjectTemplateName("layout-template");

		createOrImportAndBuild(op, project.getName());

		waitForBuildAndValidation();

		deleteProject(project.getName());
	}

	@Ignore("ignore to wait new Spring MVC Portlet Wizard")
	@Test
	public void createSpringMvcPortlet() {
		NewLiferayModuleProjectOp op = NewLiferayModuleProjectOp.TYPE.instantiate();

		op.setProjectName(project.getName());
		op.setProjectProvider(provider());
		op.setProjectTemplateName("spring-mvc-portlet");

		createOrImportAndBuild(op, project.getName());

		deleteProject(project.getName());
	}

	@Ignore("ignore as IDE-4188")
	@Test
	public void createTheme() {
		NewLiferayModuleProjectOp op = NewLiferayModuleProjectOp.TYPE.instantiate();

		op.setProjectName(project.getName());
		op.setProjectProvider(provider());
		op.setProjectTemplateName("theme");

		IProject themeProject = createOrImportAndBuild(op, project.getName());

		IBundleProject bundleProject = LiferayCore.create(IBundleProject.class, themeProject);

		Assert.assertNotNull(bundleProject);

		deleteProject(project.getName());
	}

	@Test
	public void createWarHook() {
		NewLiferayModuleProjectOp op = NewLiferayModuleProjectOp.TYPE.instantiate();

		op.setProjectName(project.getName());
		op.setProjectProvider(provider());
		op.setProjectTemplateName("war-hook");

		createOrImportAndBuild(op, project.getName());

		waitForBuildAndValidation();

		deleteProject(project.getName());
	}

	@Test
	public void createWarMvcPortlet() {
		waitForBuildAndValidation();

		NewLiferayModuleProjectOp op = NewLiferayModuleProjectOp.TYPE.instantiate();

		op.setProjectName(project.getName());
		op.setProjectProvider(provider());
		op.setProjectTemplateName("war-mvc-portlet");

		createOrImportAndBuild(op, project.getName());

		deleteProject(project.getName());
	}

	@Override
	protected String shape() {
		return "war";
	}

}