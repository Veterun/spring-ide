/*******************************************************************************
 * Copyright (c) 2015, 2016 Pivotal Software, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Pivotal Software, Inc. - initial API and implementation
 *******************************************************************************/
package org.springframework.ide.eclipse.boot.dash.model;

import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunchManager;
import org.springframework.ide.eclipse.boot.dash.metadata.IPropertyStore;
import org.springframework.ide.eclipse.boot.dash.metadata.IScopedPropertyStore;
import org.springframework.ide.eclipse.boot.dash.model.runtargettypes.RunTargetType;
import org.springsource.ide.eclipse.commons.livexp.core.LiveExpression;

/**
 * @author Kris De Volder
 */
public interface BootDashModelContext {

	//TODO: many places where this being passed around it is accompanited by a BootDashViewModel.
	// These two parameters passed together represent the real 'BootDashModelContext'.
	//So the proper thing to do is:
	//
	//  - rename this interface to BootDashViewModelContext (it represents the context of the viewmodel not of the indivual sections within
	//  - create a new class or interface called BootDashModelContext which contains
	//      - a BootDashModelContext
	//      - a BootDashViewModel
	//  - where both of these types occur together, replace with a reference to the new BootDashViewModelContext

	IWorkspace getWorkspace();

	ILaunchManager getLaunchManager();

	IPath getStateLocation();

	IScopedPropertyStore<IProject> getProjectProperties();
	IScopedPropertyStore<RunTargetType> getRunTargetProperties();
	IPropertyStore getViewProperties();

	SecuredCredentialsStore getSecuredCredentialsStore();

	void log(Exception e);

	/**
	 *!!!!Warning!!!!<p>
	 * This is 'injected' but in fact the injected filter is only used for
	 * triggering refreshes. It is not actually used to filter projects.
	 * Projects are filtered indirectly via static method BootPropertyTester.isBootProject.
	 * that means that injecting a different filter than the one used by that method
	 * will not work as expected.
	 */
	LiveExpression<Pattern> getBootProjectExclusion();
}
