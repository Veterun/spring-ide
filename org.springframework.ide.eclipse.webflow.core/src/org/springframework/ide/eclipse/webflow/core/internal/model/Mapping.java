/*
 * Copyright 2002-2007 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.ide.eclipse.webflow.core.internal.model;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.springframework.ide.eclipse.webflow.core.model.IMapping;
import org.springframework.ide.eclipse.webflow.core.model.IWebflowModelElement;
import org.springframework.ide.eclipse.webflow.core.model.IWebflowModelElementVisitor;

/**
 * 
 * 
 * @author Christian Dupuis
 * @since 2.0
 */
@SuppressWarnings("restriction")
public class Mapping extends AbstractModelElement implements IMapping {

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return getAttribute("name");
	}

	/**
	 * Gets the required.
	 * 
	 * @return the required
	 */
	public boolean getRequired() {
		return Boolean.valueOf(getAttribute("required"));
	}

	/**
	 * Gets the scope.
	 * 
	 * @return the scope
	 */
	public String getScope() {
		return getAttribute("scope");
	}

	/**
	 * Sets the name.
	 * 
	 * @param name the name
	 */
	public void setName(String name) {
		setAttribute("name", name);
	}

	/**
	 * Sets the required.
	 * 
	 * @param required the required
	 */
	public void setRequired(boolean required) {
		if (required) {
			setAttribute("required", "true");
		}
		else {
			setAttribute("required", "false");
		}
	}

	/**
	 * Sets the scope.
	 * 
	 * @param scope the scope
	 */
	public void setScope(String scope) {
		setAttribute("scope", scope);
	}

	/**
	 * Gets the from.
	 * 
	 * @return the from
	 */
	public String getFrom() {
		return getAttribute("from");
	}

	/**
	 * Gets the source.
	 * 
	 * @return the source
	 */
	public String getSource() {
		return getAttribute("source");
	}

	/**
	 * Gets the target.
	 * 
	 * @return the target
	 */
	public String getTarget() {
		return getAttribute("target");
	}

	/**
	 * Gets the target collection.
	 * 
	 * @return the target collection
	 */
	public String getTargetCollection() {
		return getAttribute("target-collection");
	}

	/**
	 * Gets the to.
	 * 
	 * @return the to
	 */
	public String getTo() {
		return getAttribute("to");
	}

	/**
	 * Sets the from.
	 * 
	 * @param value the from
	 */
	public void setFrom(String value) {
		setAttribute("from", value);
	}

	/**
	 * Sets the source.
	 * 
	 * @param value the source
	 */
	public void setSource(String value) {
		setAttribute("source", value);
	}

	/**
	 * Sets the target.
	 * 
	 * @param value the target
	 */
	public void setTarget(String value) {
		setAttribute("target", value);
	}

	/**
	 * Sets the target collection.
	 * 
	 * @param value the target collection
	 */
	public void setTargetCollection(String value) {
		setAttribute("target-collection", value);
	}

	/**
	 * Sets the to.
	 * 
	 * @param value the to
	 */
	public void setTo(String value) {
		setAttribute("to", value);
	}

	/**
	 * Creates the new.
	 * 
	 * @param parent the parent
	 */
	public void createNew(IWebflowModelElement parent) {
		IDOMNode node = (IDOMNode) parent.getNode().getOwnerDocument()
				.createElement("mapping");
		init(node, parent);
	}

	public void accept(IWebflowModelElementVisitor visitor,
			IProgressMonitor monitor) {
		visitor.visit(this, monitor);
	}
}
