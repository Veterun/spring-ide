/*
 * Copyright 2002-2006 the original author or authors.
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

package org.springframework.ide.eclipse.beans.core.internal.model;

import org.springframework.beans.factory.config.ConstructorArgumentValues.ValueHolder;
import org.springframework.ide.eclipse.beans.core.model.IBean;
import org.springframework.ide.eclipse.beans.core.model.IBeanConstructorArgument;
import org.springframework.ide.eclipse.beans.core.model.IBeansModelElementTypes;
import org.springframework.ide.eclipse.core.model.AbstractSourceModelElement;

/**
 * This class defines a constructor argument within a Spring beans
 * configuration.
 * @author Torsten Juergeleit
 */
public class BeanConstructorArgument extends AbstractSourceModelElement
										  implements IBeanConstructorArgument {
	private int index;
	private String type;
	private Object value;
	
	public BeanConstructorArgument(IBean bean, ValueHolder vHolder) {
		this(bean, -1, vHolder);
	}

	public BeanConstructorArgument(IBean bean, int index,
			ValueHolder vHolder) {
		super(bean, createName(index, vHolder));
		setSourceRange(vHolder);
		this.index = index;
		this.type = vHolder.getType();
		this.value = vHolder.getValue();
	}

	protected static final String createName(int index, ValueHolder vHolder) {
		StringBuffer buf = new StringBuffer();
		if (index >= 0) {
			buf.append(index);
		}
		if (vHolder.getType() != null) {
			if (buf.length() > 0) {
				buf.append(" - ");
			}
			buf.append(vHolder.getType());
		}
		if (buf.length() == 0) {
			buf.append(vHolder.getValue());
		}
		return buf.toString();
	}

	public int getElementType() {
		return IBeansModelElementTypes.CONSTRUCTOR_ARGUMENT_TYPE;
	}

	public int getIndex() {
		return index;
	}

	public String getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}

	public String toString() {
		StringBuffer text = new StringBuffer(getElementName());
		text.append(" (");
		text.append(getElementStartLine());
		text.append("): index=");
		text.append(index);
		text.append(", type=");
		text.append(type);
		text.append(", value=");
		text.append(value);
		return text.toString();
	}
}
