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

package com.liferay.ide.portlet.core.model;

import com.liferay.ide.portlet.core.model.internal.QNameTextNodeValueBinding;
import com.liferay.ide.portlet.core.model.internal.QNamesPossibleValuesService;

import org.eclipse.sapphire.Element;
import org.eclipse.sapphire.ElementType;
import org.eclipse.sapphire.PossibleValues;
import org.eclipse.sapphire.Unique;
import org.eclipse.sapphire.Value;
import org.eclipse.sapphire.ValueProperty;
import org.eclipse.sapphire.modeling.annotations.Enablement;
import org.eclipse.sapphire.modeling.annotations.Label;
import org.eclipse.sapphire.modeling.annotations.Service;
import org.eclipse.sapphire.modeling.xml.annotations.CustomXmlValueBinding;
import org.eclipse.sapphire.modeling.xml.annotations.XmlBinding;

/**
 * @author Kamesh Sampath
 */
public interface EventDefinitionRef extends Describeable, Element, Identifiable {

	public ElementType TYPE = new ElementType(EventDefinitionRef.class);

	public Value<String> getName();

	public Value<String> getQname();

	public void setName(String value);

	public void setQname(String value);

	@Enablement(expr = "${Qname == 'Q_NAME'}")
	@Label(standard = "Name")
	@PossibleValues(property = "/EventDefinitions/Name")
	@Unique
	@XmlBinding(path = "name")
	public ValueProperty PROP_NAME = new ValueProperty(TYPE, "Name");

	@CustomXmlValueBinding(impl = QNameTextNodeValueBinding.class, params = "qname")
	@Label(standard = "Qname")
	@Service(impl = QNamesPossibleValuesService.class, params = {@Service.Param(name = "0", value = "Q_NAME")})
	@Unique
	@XmlBinding(path = "qname")
	public ValueProperty PROP_Q_NAME = new ValueProperty(TYPE, "Qname");

}