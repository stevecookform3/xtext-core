/*
* generated by Xtext
*/
package org.eclipse.xtext.parser.bug419429;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class Bug419429StandaloneSetup extends Bug419429StandaloneSetupGenerated{

	public static void doSetup() {
		new Bug419429StandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}
