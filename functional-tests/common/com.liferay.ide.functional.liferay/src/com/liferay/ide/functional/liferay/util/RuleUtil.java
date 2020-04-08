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

package com.liferay.ide.functional.liferay.util;

import com.liferay.ide.functional.liferay.support.sdk.Sdk62Support;
import com.liferay.ide.functional.liferay.support.sdk.SdkSupport;
import com.liferay.ide.functional.liferay.support.server.LiferaryWorkspaceRunningTomcat7xSupport;
import com.liferay.ide.functional.liferay.support.server.LiferaryWorkspaceTomcat70Support;
import com.liferay.ide.functional.liferay.support.server.LiferaryWorkspaceTomcat71Support;
import com.liferay.ide.functional.liferay.support.server.ServerRunningSupport;
import com.liferay.ide.functional.liferay.support.server.ServerSupport;
import com.liferay.ide.functional.liferay.support.server.Tomcat62Support;
import com.liferay.ide.functional.liferay.support.server.Tomcat7xSupport;
import com.liferay.ide.functional.liferay.support.workspace.LiferayWorkspaceSupport;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;

import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

/**
 * @author Terry Jia
 */
public class RuleUtil {

	public static RuleChain getRuleChain(TestRule... rules) {
		RuleChain chain = RuleChain.outerRule(rules[0]);

		if (rules.length > 1) {
			for (int i = 1; i < rules.length; i++) {
				chain = chain.around(rules[i]);
			}
		}

		return chain;
	}

	public static RuleChain getTomcat7xRuleChain(SWTWorkbenchBot bot, ServerSupport server) {
		return getRuleChain(server, new Tomcat7xSupport(bot, server));
	}

	public static RuleChain getTomcat7xRunningLiferayWokrspaceRuleChain(
		SWTWorkbenchBot bot, LiferayWorkspaceSupport workspace, LiferaryWorkspaceTomcat71Support server) {

		LiferaryWorkspaceRunningTomcat7xSupport runningServer = new LiferaryWorkspaceRunningTomcat7xSupport(
			bot, server);

		return getRuleChain(workspace, server, runningServer);
	}

	public static RuleChain getTomcat7xRunningRuleChain(SWTWorkbenchBot bot, ServerSupport server) {
		return getRuleChain(server, new Tomcat7xSupport(bot, server), new ServerRunningSupport(bot, server));
	}

	public static RuleChain getTomcat7xRunningSdkRuleChain(SWTWorkbenchBot bot, ServerSupport server) {
		return getRuleChain(
			server, new Tomcat7xSupport(bot, server), new SdkSupport(bot, server),
			new ServerRunningSupport(bot, server));
	}

	public static RuleChain getTomcat7xSdkRuleChain(SWTWorkbenchBot bot, ServerSupport server) {
		return getRuleChain(server, new Tomcat7xSupport(bot, server), new SdkSupport(bot, server));
	}

	public static RuleChain getTomcat62SdkRuleChain(SWTWorkbenchBot bot, ServerSupport server) {
		return getRuleChain(server, new Tomcat62Support(bot, server), new Sdk62Support(bot, server));
	}

	public static RuleChain getTomcat70LiferayWorkspaceRuleChain(
		SWTWorkbenchBot bot, LiferayWorkspaceSupport workspace, LiferaryWorkspaceTomcat70Support server) {

		return getRuleChain(workspace, server);
	}

	public static RuleChain getTomcat71LiferayWorkspaceRuleChain(
		SWTWorkbenchBot bot, LiferayWorkspaceSupport workspace, LiferaryWorkspaceTomcat71Support server) {

		return getRuleChain(workspace, server);
	}

}