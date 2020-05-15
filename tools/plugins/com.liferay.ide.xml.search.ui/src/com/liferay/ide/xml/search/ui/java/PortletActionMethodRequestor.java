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

package com.liferay.ide.xml.search.ui.java;

import java.util.Objects;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IMemberValuePair;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.wst.xml.search.editor.searchers.javamethod.requestor.AbstractJavaMethodRequestor;
import org.eclipse.wst.xml.search.editor.searchers.javamethod.requestor.IJavaMethodRequestor;

/**
 * @author Gregory Amerson
 */
public class PortletActionMethodRequestor extends AbstractJavaMethodRequestor {

	public static final IJavaMethodRequestor INSTANCE = new PortletActionMethodRequestor();

	@Override
	protected IStatus doValidate(IMethod method) {
		String[] parameterTypes = method.getParameterTypes();

		boolean valid = false;

		if ((parameterTypes != null) && (parameterTypes.length == 2) &&
			(Objects.equals(parameterTypes[0], "QActionRequest;") ||
			 Objects.equals(parameterTypes[0], "Qjavax.portlet.ActionRequest;")) &&
			(Objects.equals(parameterTypes[1], "QActionResponse;") ||
			 Objects.equals(parameterTypes[1], "Qjavax.portlet.ActionResponse;"))) {

			valid = true;
		}

		if (valid) {
			return Status.OK_STATUS;
		}

		return null;
	}

	@Override
	protected String formatMethodName(Object selectedNode, IMethod method) {
		String retval = null;

		if (_hasProcessActionAnnotation(method)) {
			IAnnotation annotation = method.getAnnotation("ProcessAction");

			IMemberValuePair pair = _findNamePair(annotation);

			if (pair != null) {
				Object value = pair.getValue();

				if (value != null) {
					retval = value.toString();
				}
			}
		}
		else {
			retval = method.getElementName();
		}

		return retval;
	}

	private IMemberValuePair _findNamePair(IAnnotation annotation) {
		IMemberValuePair retval = null;

		try {
			IMemberValuePair[] pairs = annotation.getMemberValuePairs();

			for (IMemberValuePair pair : pairs) {
				if (Objects.equals("name", pair.getMemberName())) {
					retval = pair;

					break;
				}
			}
		}
		catch (JavaModelException jme) {
		}

		return retval;
	}

	private boolean _hasProcessActionAnnotation(IMethod method) {
		try {
			IAnnotation[] annots = method.getAnnotations();

			for (IAnnotation annot : annots) {
				if (Objects.equals("ProcessAction", annot.getElementName())) {
					return true;
				}
			}
		}
		catch (JavaModelException jme) {
		}

		return false;
	}

}