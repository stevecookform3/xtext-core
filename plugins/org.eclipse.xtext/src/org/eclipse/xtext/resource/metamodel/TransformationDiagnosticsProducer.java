/*******************************************************************************
 * Copyright (c) 2008 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.resource.metamodel;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.diagnostics.AbstractDiagnosticProducer;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.diagnostics.IDiagnosticConsumer;
import org.eclipse.xtext.parsetree.NodeAdapter;
import org.eclipse.xtext.parsetree.NodeUtil;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
public class TransformationDiagnosticsProducer extends AbstractDiagnosticProducer implements ErrorAcceptor {

	private TransformationErrorCode lastError;
	
	public TransformationDiagnosticsProducer(IDiagnosticConsumer consumer) {
		super(consumer);
	}

	@Override
	protected Diagnostic createDiagnostic(String message) {
		return new TransformationDiagnostic(getNode(), message, lastError);
	}

	public void acceptError(TransformationErrorCode errorCode, String message, EObject element) {
		setTarget(element, null);
		lastError = errorCode;
		addDiagnostic(message);	
		lastError = null;
	}
	
	@Override
	public void setTarget(EObject object, EStructuralFeature feature) {
		NodeAdapter adapter = NodeUtil.getNodeAdapter(object);
		while (adapter == null) {
			object = object.eContainer();
			if (object == null)
				break;
			adapter = NodeUtil.getNodeAdapter(object);
		}
		if (adapter == null)
			throw new IllegalStateException("Cannot find NodeAdapter for object: " + object.toString());
		setNode(adapter.getParserNode());
	}

}
