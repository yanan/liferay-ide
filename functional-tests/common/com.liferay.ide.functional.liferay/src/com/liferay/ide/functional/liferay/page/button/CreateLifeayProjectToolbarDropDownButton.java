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

package com.liferay.ide.functional.liferay.page.button;

import com.liferay.ide.functional.swtbot.page.MenuItem;
import com.liferay.ide.functional.swtbot.page.ToolbarDropDownButton;

import org.eclipse.swtbot.swt.finder.SWTBot;

/**
 * @author Terry Jia
 * @author Vicky Wang
 * @author Ying Xu
 * @author Li Lu
 */
public class CreateLifeayProjectToolbarDropDownButton extends ToolbarDropDownButton {

	public CreateLifeayProjectToolbarDropDownButton(SWTBot bot) {
		super(bot, CREATE_A_NEW_LIFERAY_MODULE_PROJECT);

		_newLiferayServer = new MenuItem(bot, this, NEW_LIFERAY_SERVER);
		_newLiferayModuleFragment = new MenuItem(bot, this, NEW_LIFERAY_MODULE_PROJECT_FRAGMENT);
		_newLiferayWorkspaceProject = new MenuItem(bot, this, NEW_LIFERAY_WORPSPACE_PROJECT);
		_newLiferayComponentClass = new MenuItem(bot, this, NEW_LIFERAY_COMPONENT_CLASS);
		_newLiferayModule = new MenuItem(bot, this, NEW_LIFERAY_MODULE_PROJECT);
		_newLiferayJSFProject = new MenuItem(bot, this, NEW_LIFERAY_JSF_PROJECT);
		_newLiferayKaleoWorkflow = new MenuItem(bot, this, NEW_LIFERAY_KALEO_WORKFLOW);
		_newLiferayModulesExt = new MenuItem(bot, this, NEW_LIFERAY_MODULE_EXT_PROJECT);
		_newLiferaySpringMVCPortlet = new MenuItem(bot, this, NEW_LIFERAY_SPRING_MVC_PORTLET_PROJECT);
	}

	public MenuItem getNewLiferayComponentClass() {
		return _newLiferayComponentClass;
	}

	public MenuItem getNewLiferayJSFProject() {
		return _newLiferayJSFProject;
	}

	public MenuItem getNewLiferayKaleoWorkflow() {
		return _newLiferayKaleoWorkflow;
	}

	public MenuItem getNewLiferayModule() {
		return _newLiferayModule;
	}

	public MenuItem getNewLiferayModuleFragment() {
		return _newLiferayModuleFragment;
	}

	public MenuItem getNewLiferayModulesExt() {
		return _newLiferayModulesExt;
	}

	public MenuItem getNewLiferayServer() {
		return _newLiferayServer;
	}

	public MenuItem getNewLiferaySpringMvcPortletProject() {
		return _newLiferaySpringMVCPortlet;
	}

	public MenuItem getNewLiferayWorkspaceProject() {
		return _newLiferayWorkspaceProject;
	}

	private MenuItem _newLiferayComponentClass;
	private MenuItem _newLiferayJSFProject;
	private MenuItem _newLiferayKaleoWorkflow;
	private MenuItem _newLiferayModule;
	private MenuItem _newLiferayModuleFragment;
	private MenuItem _newLiferayModulesExt;
	private MenuItem _newLiferayServer;
	private MenuItem _newLiferaySpringMVCPortlet;
	private MenuItem _newLiferayWorkspaceProject;

}