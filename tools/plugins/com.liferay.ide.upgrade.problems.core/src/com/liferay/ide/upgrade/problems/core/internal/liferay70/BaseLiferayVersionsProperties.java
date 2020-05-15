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

package com.liferay.ide.upgrade.problems.core.internal.liferay70;

import com.liferay.ide.core.util.ListUtil;
import com.liferay.ide.upgrade.plan.core.UpgradeProblem;
import com.liferay.ide.upgrade.problems.core.AutoFileMigrateException;
import com.liferay.ide.upgrade.problems.core.AutoFileMigrator;
import com.liferay.ide.upgrade.problems.core.FileSearchResult;
import com.liferay.ide.upgrade.problems.core.internal.PropertiesFileChecker;
import com.liferay.ide.upgrade.problems.core.internal.PropertiesFileMigrator;

import java.io.ByteArrayInputStream;
import java.io.File;

import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.apache.commons.io.FileUtils;

/**
 * @author Gregory Amerson
 * @author Simon Jiang
 */
public abstract class BaseLiferayVersionsProperties extends PropertiesFileMigrator implements AutoFileMigrator {

	public BaseLiferayVersionsProperties(String oldVersionPattern, String newVersion) {
		_oldVersionPattern = oldVersionPattern;
		_newVersion = newVersion;
	}

	@Override
	public List<UpgradeProblem> analyze(File file) {
		List<UpgradeProblem> problems = new ArrayList<>();

		if (Objects.equals("liferay-plugin-package.properties", file.getName())) {
			PropertiesFileChecker propertiesFileChecker = new PropertiesFileChecker(file);

			List<PropertiesFileChecker.KeyInfo> keys = propertiesFileChecker.getInfos("liferay-versions");

			if (ListUtil.isNotEmpty(keys)) {
				PropertiesFileChecker.KeyInfo key = keys.get(0);

				String versions = key.value;

				if (!versions.matches(_oldVersionPattern)) {
					List<FileSearchResult> results = propertiesFileChecker.findProperties("liferay-versions");

					if (results != null) {
						String sectionHtml = problemSummary;

						for (FileSearchResult searchResult : results) {
							searchResult.autoCorrectContext = _PREFIX + "liferay-versions";

							problems.add(
								new UpgradeProblem(
									problemTitle, problemSummary, problemType, problemTickets, version, file,
									searchResult.startLine, searchResult.startOffset, searchResult.endOffset,
									sectionHtml, searchResult.autoCorrectContext, UpgradeProblem.STATUS_NOT_RESOLVED,
									UpgradeProblem.DEFAULT_MARKER_ID, UpgradeProblem.MARKER_ERROR));
						}
					}
				}
			}
		}

		return problems;
	}

	@Override
	public int correctProblems(File file, Collection<UpgradeProblem> upgradeProblems) throws AutoFileMigrateException {
		try {
			String contents = new String(Files.readAllBytes(file.toPath()));

			int problemsFixed = 0;

			for (UpgradeProblem upgradeProblem : upgradeProblems) {
				if (upgradeProblem.getAutoCorrectContext() instanceof String) {
					String propertyData = upgradeProblem.getAutoCorrectContext();

					if ((propertyData != null) && propertyData.startsWith(_PREFIX)) {
						String propertyValue = propertyData.substring(_PREFIX.length());

						contents = contents.replaceAll(propertyValue + ".*", propertyValue + "=" + _newVersion);

						problemsFixed++;
					}
				}
			}

			if (problemsFixed > 0) {
				try (ByteArrayInputStream input = new ByteArrayInputStream(contents.getBytes())) {
					FileUtils.copyInputStreamToFile(input, file);
				}
			}

			return problemsFixed;
		}
		catch (Exception e) {
		}

		return 0;
	}

	@Override
	protected void addPropertiesToSearch(List<String> properties) {
	}

	private static final String _PREFIX = "property:";

	private String _newVersion;
	private String _oldVersionPattern;

}