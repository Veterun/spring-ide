/*******************************************************************************
 * Copyright (c) 2015 Pivotal, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Pivotal, Inc. - initial API and implementation
 *******************************************************************************/
package org.springframework.ide.eclipse.boot.properties.editor.preferences;

import org.springframework.ide.eclipse.boot.properties.editor.reconciling.SpringPropertiesProblemType;

/**
 * @author Kris De Volder
 */
public class SpringYamlEditorPreferencesPage extends AbstractPropertiesEditorPreferencesPage {

	protected SpringPropertiesProblemType[] getProblemTypes() {
		return SpringPropertiesProblemType.FOR_YAML;
	}

	@Override
	protected String getEnableProjectPreferencesKey() {
		return ProblemSeverityPreferencesUtil.ENABLE_PROJECT_PREFERENCES(EditorType.YAML);
	}

}
