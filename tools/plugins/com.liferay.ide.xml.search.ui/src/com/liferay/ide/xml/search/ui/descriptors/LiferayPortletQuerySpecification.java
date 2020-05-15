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

package com.liferay.ide.xml.search.ui.descriptors;

import org.eclipse.wst.xml.search.core.queryspecifications.requestor.IXMLSearchRequestor;

/**
 * Query specification to search liferay-portlet.xml descriptors.
 * @author Gregory Amerson
 */
public class LiferayPortletQuerySpecification extends AbstractWebInfQuerySpecification {

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.wst.xml.search.core.resource.IResourceRequestorProvider#getRequestor()
	 */
	public IXMLSearchRequestor getRequestor() {
		return LiferayPortletSearchRequestor.instance;
	}

}