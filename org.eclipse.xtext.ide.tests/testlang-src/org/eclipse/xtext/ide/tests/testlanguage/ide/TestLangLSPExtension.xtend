/*******************************************************************************
 * Copyright (c) 2016, 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.ide.tests.testlanguage.ide

import com.google.inject.ImplementedBy
import java.util.List
import java.util.concurrent.CompletableFuture
import org.eclipse.lsp4j.Position
import org.eclipse.lsp4j.jsonrpc.Endpoint
import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethodProvider
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints
import org.eclipse.xtext.ide.server.ILanguageServerAccess
import org.eclipse.xtext.ide.server.ILanguageServerAccess.IBuildListener
import org.eclipse.xtext.ide.server.ILanguageServerExtension
import org.eclipse.xtext.resource.IResourceDescription.Delta
import org.eclipse.xtend.lib.annotations.ToString

/**
 * @author efftinge - Initial contribution and API
 */
@ImplementedBy(TestLangLSPExtension.Impl)
interface TestLangLSPExtension extends ILanguageServerExtension {
	
	@JsonRequest
	def CompletableFuture<TextOfLineResult> getTextOfLine(TextOfLineParam param)

	static class TextOfLineResult {
		public String text
	}
	static class TextOfLineParam {
		public String uri
		public int line
	}
	
	@ToString
	static class BuildNotification {
		public String message
	}
	
	static interface CustomClient {
		@JsonNotification
		def void buildHappened(BuildNotification notification)
	}
	
	static class Impl implements ILanguageServerExtension, TestLangLSPExtension, IBuildListener, JsonRpcMethodProvider {

		ILanguageServerAccess access
	
		CustomClient client
	
		override getTextOfLine(TextOfLineParam param) {
			return access.doRead(param.uri) [ ctx |
				val start = ctx.document.getOffSet(new Position(param.line, 0 ))
				val end = ctx.document.getOffSet(new Position(param.line + 1, 0 )) -System.lineSeparator.length
				return new TextOfLineResult => [
					text = ctx.document.contents.substring(start, end)
				]
			]
		}
		
		override initialize(ILanguageServerAccess access) {
			this.access = access
			this.access.addBuildListener(this)
			this.client = ServiceEndpoints.toServiceObject(access.languageClient as Endpoint, CustomClient)
		}
		
		override afterBuild(List<Delta> deltas) {
			client.buildHappened(new BuildNotification => [
				message = "Built "+deltas.map[uri.toString].join(', ')
			]) 
		}
		
		override supportedMethods() {
			val result = newHashMap
			result.putAll(ServiceEndpoints.getSupportedMethods(this.getClass))
			// register client side
			result.putAll(ServiceEndpoints.getSupportedMethods(CustomClient))
			return result
		}
		
	}
}
