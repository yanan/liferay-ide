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

package com.liferay.ide.upgrade.problems.ui.internal;

import com.liferay.ide.upgrade.plan.core.UpgradePlan;
import com.liferay.ide.upgrade.plan.core.UpgradePlanner;
import com.liferay.ide.upgrade.plan.core.UpgradeProblem;
import com.liferay.ide.upgrade.plan.ui.util.UIUtil;

import java.io.File;

import java.util.Collection;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.Viewer;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Seiphon Wang
 * @author Terry Jia
 * @author Simon Jiang
 */
public class AutoCorrectAction extends BaseAutoCorrectAction implements UpgradeProblemUISupport {

	public AutoCorrectAction(ISelectionProvider provider) {
		super(provider, "Correct automatically");

		Bundle bundle = FrameworkUtil.getBundle(AutoCorrectAction.class);

		_serviceTracker = new ServiceTracker<>(bundle.getBundleContext(), UpgradePlanner.class, null);

		_serviceTracker.open();
	}

	@Override
	public void run() {
		UpgradeProblem upgradeProblem = getUpgradeProblem(getSelection());

		File file = upgradeProblem.getResource();

		autoCorrect(file, upgradeProblem, true);

		UpgradePlanner upgradePlanner = _serviceTracker.getService();

		UpgradePlan currentUpgradePlan = upgradePlanner.getCurrentUpgradePlan();

		Collection<UpgradeProblem> upgradeProblems = currentUpgradePlan.getUpgradeProblems();

		upgradeProblems.remove(upgradeProblem);

		Viewer viewer = (Viewer)getSelectionProvider();

		UIUtil.async(viewer::refresh);
	}

	private final ServiceTracker<UpgradePlanner, UpgradePlanner> _serviceTracker;

}