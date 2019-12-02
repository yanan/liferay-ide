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

package com.liferay.ide.functional.release.tests;

import com.liferay.ide.functional.liferay.SwtbotBase;
import com.liferay.ide.functional.liferay.support.project.ProjectSupport;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Ashley Yuan
 */
public class GradleStandaloneProjectsTests extends SwtbotBase {

	//create all gradle standalone projects
	@Test
	public void createActivator() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), ACTIVATOR, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createApi() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), API, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createContentTargetingReport() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), CONTENT_TARGETING_REPORT, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createContentTargetingRule() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), CONTENT_TARGETING_RULE, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createContentTargetingTrackingAction() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), CONTENT_TARGETING_TRACKING_ACTION, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createControlMenuEntry() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), CONTROL_MENU_ENTRY, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createFormField() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), FORM_FIELD, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createFreemarkerPortlet() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), FREEMARKER_PORTLET, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createLayoutTemplate() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), LAYOUT_TEMPLATE, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createMvcPortlet() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), MVC_PORTLET, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createNpmAngularPortlet() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), NPM_ANGULAR_PORTLET, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createNpmReactPortlet() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), NPM_REACT_PORTLET, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createNpmVuejsPortlet() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), NPM_VUEJS_PORTLET, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createPanelApp() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), PANEL_APP, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createPortletConfigurationIcon() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), PORTLET_CONFIGURATION_ICON, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createPortletProvider() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), PORTLET_PROVIDER, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createPortletToolbarContributor() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), PORTLET_PROVIDER, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createRest() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), REST, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createService() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), SERVICE, "7.1");

		wizardAction.next();

		wizardAction.newModuleInfo.openSelectServiceDialog();

		dialogAction.prepareText("*lifecycleAction");

		dialogAction.confirm();

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));

		viewAction.project.openFile(
			project.getName(), "src/main/java", project.getName(), project.getCapitalName() + ".java");

		viewAction.project.implementMethods(
			project.getName(), "src/main/java", project.getName(), project.getCapitalName() + ".java");

		dialogAction.confirm();

		editorAction.save();

		editorAction.close();
	}

	@Test
	public void createServiceBuilder() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), SERVICE_BUILDER, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createServiceWrapper() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), SERVICE_WRAPPER, "7.1");

		wizardAction.next();

		wizardAction.newModuleInfo.openSelectServiceDialog();

		dialogAction.prepareText("*AccountLocalServiceWrapper");

		dialogAction.confirm();

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createSimulationPanelEntry() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), SIMULATION_PANEL_ENTRY, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createSocialBookMark() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), SOCIAL_BOOKMARK, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createTemplateContextContributor() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), TEMPLATE_CONTEXT_CONCONTRIBUTOR, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createTheme() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), THEME, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));

		viewAction.project.openFile(project.getName(), "src", "main", "webapp", "css", "_custom.scss");

		editorAction.setText("#wrapper{ background:yellow; }");

		editorAction.close();
	}

	@Test
	public void createThemeContributor() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), THEME_CONTRIBUTOR, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createWarCoreExt() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), WAR_CORE_EXT, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createWarHook() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), WAR_HOOK, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Test
	public void createWarMvcPortlet() {
		wizardAction.openNewLiferayModuleWizard();

		wizardAction.newModule.prepareGradle(project.getName(), WAR_MVC_PORTLET, "7.1");

		wizardAction.finish();

		jobAction.waitForNoRunningJobs();

		Assert.assertTrue(viewAction.project.visibleFileTry(project.getName()));
	}

	@Rule
	public ProjectSupport project = new ProjectSupport(bot);

}