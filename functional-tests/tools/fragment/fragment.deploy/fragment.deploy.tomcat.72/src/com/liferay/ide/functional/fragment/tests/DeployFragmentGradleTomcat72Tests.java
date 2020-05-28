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

package com.liferay.ide.functional.fragment.tests;

import com.liferay.ide.functional.fragment.deploy.base.FragmentTomcat7xGradleDeployBase;
import com.liferay.ide.functional.liferay.support.server.PureTomcat72Support;
import com.liferay.ide.functional.liferay.support.server.ServerSupport;
import com.liferay.ide.functional.liferay.util.RuleUtil;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.rules.RuleChain;

/**
 * @author Lily Li
 */
@Ignore("ignore because blade 3.10.0 does not support the creation of gradle standalone")
public class DeployFragmentGradleTomcat72Tests extends FragmentTomcat7xGradleDeployBase {

	@ClassRule
	public static RuleChain chain = RuleUtil.getTomcat7xRunningRuleChain(bot, getServer());

	public static ServerSupport getServer() {
		if ((server == null) || !(server instanceof PureTomcat72Support)) {
			server = new PureTomcat72Support(bot);
		}

		return server;
	}

	@Test
	public void deployFragmentWithJsp() {
		super.deployFragmentWithJsp();
	}

}