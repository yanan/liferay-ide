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

package com.liferay.ide.upgrade.plan.core;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Gregory Amerson
 */
public abstract class BaseUpgradeTaskStep implements UpgradeTaskStep {

	@Activate
	public void activate(ComponentContext componentContext) {
		Dictionary<String, Object> properties = componentContext.getProperties();

		_description = _getProperty(properties, "description");
		_id = _getProperty(properties, "id");
		_requirement = _getProperty(properties, "requirement");
		_title = _getProperty(properties, "title");
		_url = _getProperty(properties, "url");

		BundleContext bundleContext = componentContext.getBundleContext();

		_upgradePlannerServiceTracker = new ServiceTracker<>(bundleContext, UpgradePlanner.class, null);

		_upgradePlannerServiceTracker.open();
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public String getId() {
		return _id;
	}

	@Override
	public UpgradeTaskStepRequirement getRequirement() {
		return UpgradeTaskStepRequirement.valueOf(UpgradeTaskStepRequirement.class, _requirement.toUpperCase());
	}

	@Override
	public String getTitle() {
		return _title;
	}

	public UpgradePlanner getUpgradePlanner() {
		UpgradePlanner upgradePlanner = _upgradePlannerServiceTracker.getService();

		return upgradePlanner;
	}

	@Override
	public String getUrl() {
		return _url;
	}

	@Override
	public String toString() {
		return getId();
	}

	private String _getProperty(Dictionary<String, Object> properties, String key) {
		Object value = properties.get(key);

		if (value instanceof String) {
			return (String)value;
		}

		return null;
	}

	private static ServiceTracker<UpgradePlanner, UpgradePlanner> _upgradePlannerServiceTracker;

	private String _description;
	private String _id;
	private String _requirement;
	private String _title;
	private String _url;

}