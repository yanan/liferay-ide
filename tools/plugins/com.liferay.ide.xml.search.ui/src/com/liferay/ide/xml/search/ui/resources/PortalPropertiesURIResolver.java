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

package com.liferay.ide.xml.search.ui.resources;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.wst.xml.search.core.resource.ResourceBaseURIResolver;

/**
 * @author Kuo Zhang
 */
@SuppressWarnings("serial")
public class PortalPropertiesURIResolver extends ResourceBaseURIResolver {

	public static final PortalPropertiesURIResolver INSTANCE = new PortalPropertiesURIResolver();

	public PortalPropertiesURIResolver() {
	}

	@Override
	public boolean accept(
		Object selectedNode, IResource rootContainer, IResource file, String matching, boolean fullMatch) {

		String extension = file.getFileExtension();

		if ((extension == null) || !getExtensions().contains(extension.toLowerCase())) {
			return false;
		}

		if (matching != null) {
			String uri = resolve(selectedNode, rootContainer, file);

			if (fullMatch) {
				return uri.equals(matching);
			}

			return uri.startsWith(matching);
		}

		return false;
	}

	protected Set<String> getExtensions() {
		return _extensions;
	}

	private static final Set<String> _extensions = new HashSet<String>() {
		{
			add("properties");
		}
	};

}