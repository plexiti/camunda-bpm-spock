package org.camunda.bpm.extension.spock

import org.spockframework.runtime.extension.IMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation
import spock.lang.Specification

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
class ScriptInterceptor implements IMethodInterceptor {

    private final Script script;

    public ScriptInterceptor(Script script) {
        this.script = script;
    }

    public void intercept(final IMethodInvocation invocation) {
        def binding = Mocks.mock(invocation.instance as Specification)
        def shell = new GroovyShell(binding)
        def resource = script.value()
        def script = shell.parse(ClassLoader.getSystemResource(resource).toURI())
        Scripts.set(resource, script as groovy.lang.Script)
        invocation.proceed()
        Scripts.cleanup()
    }

}
