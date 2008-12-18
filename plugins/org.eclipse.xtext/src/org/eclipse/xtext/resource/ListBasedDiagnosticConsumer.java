/*******************************************************************************
 * Copyright (c) 2008 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.diagnostics.IDiagnosticConsumer;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
public class ListBasedDiagnosticConsumer implements IDiagnosticConsumer {

	private final List<Diagnostic> diagnostics;

	private boolean diagnosticsConsumed;
	
	public ListBasedDiagnosticConsumer() {
		this.diagnostics = new ArrayList<Diagnostic>();
		this.diagnosticsConsumed = false;
	}
	
	public void consume(Diagnostic diagnostic) {
		boolean changed = this.diagnostics.add(diagnostic);
		diagnosticsConsumed |= changed;
	}

	public boolean hasConsumedDiagnostics() {
		return diagnosticsConsumed;
	}
	
	public List<Diagnostic> getResult() {
		return Collections.unmodifiableList(diagnostics);
	}
	
}
