/*******************************************************************************
 * Copyright (c) 2015 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.generator.trace.internal;

import org.eclipse.xtext.generator.trace.AbsoluteURI;
import org.eclipse.xtext.generator.trace.ITraceForURIProvider;
import org.eclipse.xtext.generator.trace.SourceRelativeURI;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
public interface IPlatformSpecificTraceProvider<
		PlatformResource,
		Trace extends IPlatformSpecificTrace<PlatformResource, ?, ?, Trace>> 
	extends ITraceForURIProvider {

	/**
	 * Returns the trace information to the sources that were used as input for the given derived resource.
	 * May return <code>null</code> if no such data is available for the given storage.
	 * @param derivedResource the resource whose source trace is requested. May not be <code>null</code>.
	 * @return the trace to the source or <code>null</code>.
	 */
	Trace getTraceToSource(PlatformResource derivedResource);
	
	/**
	 * Returns the trace information to the targets that were generated from the given source.
	 * May return <code>null</code> if no such data is available for the given storage.
	 * @param sourceResource the resource whose target trace is requested. May not be <code>null</code>.
	 * @return the trace to the generation targets or <code>null</code>.
	 */
	Trace getTraceToTarget(PlatformResource sourceResource);
	
	@Override
	Trace getTraceToSource(AbsoluteURI absoluteDerivedResource, IWorkspaceConfig context);
	
	@Override
	Trace getTraceToSource(SourceRelativeURI srcRelativeDerivedResource, IProjectConfig project);
	
	@Override
	Trace getTraceToTarget(AbsoluteURI absoluteSourceResource, IWorkspaceConfig context);
	
	@Override
	Trace getTraceToTarget(SourceRelativeURI srcRelativeSourceResource, IProjectConfig project);
	
}