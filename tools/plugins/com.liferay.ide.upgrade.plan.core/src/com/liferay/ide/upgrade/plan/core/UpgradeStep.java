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

import com.liferay.ide.core.util.ListUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.Adapters;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Gregory Amerson
 * @author Simon Jiang
 * @author Terry Jia
 */
public class UpgradeStep {

	public UpgradeStep(
		String title, String summary, String imagePath, String url, String requirement, UpgradeStepStatus status,
		String commandId, UpgradeStep parentUpgradeStep) {

		_title = title;

		if (!summary.startsWith("<form>")) {
			summary = "<form>" + summary;
		}

		if (!summary.endsWith("</form>")) {
			summary = summary + "</form>";
		}

		_summary = summary;
		_icon = imagePath;
		_url = url;
		_requirement = requirement;
		_status = status;
		_commandId = commandId;
		_parentUpgradeStep = parentUpgradeStep;

		Bundle bundle = FrameworkUtil.getBundle(UpgradeStep.class);

		_serviceTracker = new ServiceTracker<>(bundle.getBundleContext(), UpgradePlanner.class, null);

		_serviceTracker.open();
	}

	public void appendChild(UpgradeStep upgradeStep) {
		_children.add(upgradeStep);
	}

	public boolean completed() {
		if (ListUtil.isNotEmpty(_children)) {
			for (UpgradeStep childUpgradeStep : _children) {
				if (!childUpgradeStep.completed()) {
					return false;
				}
			}

			return true;
		}

		if ((getStatus() == UpgradeStepStatus.COMPLETED) || (getStatus() == UpgradeStepStatus.SKIPPED)) {
			return true;
		}

		return false;
	}

	public void dispose() {
		Stream<UpgradeStep> stream = _children.stream();

		stream.forEach(UpgradeStep::dispose);

		_serviceTracker.close();
	}

	public boolean enabled() {
		if (_parentUpgradeStep == null) {
			return true;
		}

		List<UpgradeStep> siblings = _parentUpgradeStep.getChildren();

		for (UpgradeStep sibling : siblings) {
			if (sibling.equals(this)) {
				break;
			}

			if (!sibling.completed()) {
				return false;
			}
		}

		if (_parentUpgradeStep.enabled()) {
			return true;
		}

		return false;
	}

	public boolean equals(Object object) {
		if ((object instanceof UpgradeStep) == false) {
			return false;
		}

		UpgradeStep baseUpgradeStep = Adapters.adapt(object, UpgradeStep.class);

		if (baseUpgradeStep == null) {
			return false;
		}

		if (isEqualIgnoreCase(_summary, baseUpgradeStep._summary) &&
			isEqualIgnoreCase(_requirement, baseUpgradeStep._requirement) &&
			isEqualIgnoreCase(_commandId, baseUpgradeStep._commandId) &&
			isEqualIgnoreCase(_url, baseUpgradeStep._url) && isEqual(_children, baseUpgradeStep._children) &&
			isEqualIgnoreCase(_title, baseUpgradeStep._title)) {

			return true;
		}

		return false;
	}

	public List<UpgradeStep> getChildren() {
		return _children;
	}

	public String getCommandId() {
		return _commandId;
	}

	public String getImagePath() {
		if (completed()) {
			return "icons/completed.gif";
		}
		else if (_status.equals(UpgradeStepStatus.FAILED)) {
			return "icons/failed.png";
		}

		// TODO need to use the special icon instead

		return "icons/step_default.gif";
	}

	public UpgradeStep getParent() {
		return _parentUpgradeStep;
	}

	public UpgradeStepRequirement getRequirement() {
		return UpgradeStepRequirement.valueOf(UpgradeStepRequirement.class, _requirement.toUpperCase());
	}

	public UpgradeStepStatus getStatus() {
		return _status;
	}

	public String getSummary() {
		return _summary;
	}

	public String getTitle() {
		return _title;
	}

	public String getUrl() {
		return _url;
	}

	public int hashCode() {
		int hash = 31;

		hash = 31 * hash + ((_summary != null) ? _summary.hashCode() : 0);

		Stream<UpgradeStep> stream = _children.stream();

		int childrenHashCodes = stream.map(
			Object::hashCode
		).reduce(
			0, Integer::sum
		).intValue();

		hash = 31 * hash + ((_requirement != null) ? _requirement.hashCode() : 0);
		hash = 31 * hash + childrenHashCodes;
		hash = 31 * hash + ((_url != null) ? _url.hashCode() : 0);
		hash = 31 * hash + ((_title != null) ? _title.hashCode() : 0);
		hash = 31 * hash + ((_commandId != null) ? _commandId.hashCode() : 0);

		return hash;
	}

	public <T extends UpgradeStep> boolean isEqual(Collection<T> source, Collection<T> target) {
		if (source == null) {
			if (target == null) {
				return true;
			}

			return false;
		}

		if (target == null) {
			return false;
		}

		if (source.size() != target.size()) {
			return false;
		}

		if (ListUtil.isEmpty(source) && ListUtil.isEmpty(target)) {
			return true;
		}

		Stream<T> targetStream = target.stream();

		Collection<String> targetTitles = targetStream.map(
			UpgradeStep::getTitle
		).collect(
			Collectors.toList()
		);

		Stream<T> sourceStream = source.stream();

		return sourceStream.map(
			UpgradeStep::getTitle
		).filter(
			targetTitles::contains
		).findAny(
		).isPresent();
	}

	public boolean isEqualIgnoreCase(String original, String target) {
		if (original != null) {
			return original.equalsIgnoreCase(target);
		}

		if (target == null) {
			return true;
		}

		return false;
	}

	public boolean restartable() {
		if (ListUtil.isEmpty(_children)) {
			return completed();
		}

		for (UpgradeStep childUpgradeStep : _children) {
			if (childUpgradeStep.completed()) {
				return true;
			}
		}

		return false;
	}

	public void setStatus(UpgradeStepStatus status) {
		UpgradeStepStatusChangedEvent upgradeStepStatusChangedEvent = new UpgradeStepStatusChangedEvent(
			this, _status, status);

		_status = status;

		UpgradePlanner upgradePlanner = _serviceTracker.getService();

		upgradePlanner.dispatch(upgradeStepStatusChangedEvent);
	}

	private List<UpgradeStep> _children = new ArrayList<>();
	private String _commandId;
	private String _icon;
	private UpgradeStep _parentUpgradeStep;
	private String _requirement;
	private final ServiceTracker<UpgradePlanner, UpgradePlanner> _serviceTracker;
	private UpgradeStepStatus _status = UpgradeStepStatus.INCOMPLETE;
	private String _summary;
	private String _title;
	private String _url;

}