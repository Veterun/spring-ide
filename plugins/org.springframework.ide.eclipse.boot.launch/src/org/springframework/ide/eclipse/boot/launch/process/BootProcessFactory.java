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
package org.springframework.ide.eclipse.boot.launch.process;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.IProcessFactory;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.RuntimeProcess;
import org.springframework.ide.eclipse.boot.core.BootActivator;
import org.springframework.ide.eclipse.boot.launch.BootLaunchConfigurationDelegate;
import org.springframework.ide.eclipse.boot.launch.util.SpringApplicationLifeCycleClientManager;
import org.springframework.ide.eclipse.boot.launch.util.SpringApplicationLifecycleClient;
import org.springframework.ide.eclipse.boot.util.RetryUtil;

public class BootProcessFactory implements IProcessFactory {

	private static final boolean DEBUG = false;

	private static void debug(String string) {
		if (DEBUG) {
			System.out.println(string);
		}
	}

	@Override
	public IProcess newProcess(ILaunch launch, final Process process, final String label, Map<String, String> attributes) {

		final int jmxPort = getJMXPort(launch);
		final long timeout = getNiceTerminationTimeout(launch);
		if (jmxPort>0) {
			return new RuntimeProcess(launch, process, label, attributes) {

				SpringApplicationLifeCycleClientManager clientMgr = new SpringApplicationLifeCycleClientManager(jmxPort);

				@Override
				public void terminate() throws DebugException {
					if (!terminateNicely()) {
						//Let eclipse try it more aggressively.
						super.terminate();
					}
				}

				private boolean terminateNicely() {
					if (isTerminated()) {
						return true;
					}
					try {
						debug("Trying to terminate nicely: "+label);
						SpringApplicationLifecycleClient client = clientMgr.getLifeCycleClient();
						if (client!=null) {
							debug("Asking JMX client to 'stop'");
							client.stop();
							debug("Asking JMX client to 'stop' -> SUCCESS");
						} else {
							//This case happens if process is already being terminated in another way.
							// This happens for debug processes where debug target kills vm before
							// asking process to terminate.
							debug("PROBLEM? Couldn't get JMX client");
							throw new IOException("Couldn't get JMX client.");
						}
						//Wait for a bit, but not forever until process dies.
						RetryUtil.retry(100, timeout, new Callable<Void>() {
							public Void call() throws Exception {
								debug("process exited?");
								process.exitValue(); //throws if process not ready
								debug("process exited? -> YES");
								return null;
							}
						});
						debug("SUCCESS terminate nicely: "+label);
						return true;
					} catch (Exception e) {
						BootActivator.log(e);
					} finally {
						clientMgr.disposeClient();
					}
					return false;
				}

			};
		} else {
			//Use eclipse 'regular' runtime process
			return new RuntimeProcess(launch, process, label, attributes);
		}
	}

	private long getNiceTerminationTimeout(ILaunch launch) {
		ILaunchConfiguration conf = launch.getLaunchConfiguration();
		if (conf!=null) {
			return BootLaunchConfigurationDelegate.getTerminationTimeoutAsLong(conf);
		}
		return BootLaunchConfigurationDelegate.DEFAULT_TERMINATION_TIMEOUT;
	}

	private int getJMXPort(ILaunch launch) {
		ILaunchConfiguration conf = launch.getLaunchConfiguration();
		if (conf!=null && BootLaunchConfigurationDelegate.canUseLifeCycle(conf)) {
			return BootLaunchConfigurationDelegate.getJMXPortAsInt(conf);
		}
		return -1;
	}

}
