package org.camunda.bpm.extension.spock;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.spockframework.mock.MockImplementation;
import org.spockframework.mock.MockNature;
import org.spockframework.runtime.extension.IMethodInterceptor;
import org.spockframework.runtime.extension.IMethodInvocation;
import spock.lang.Specification;

import java.lang.reflect.Field;
import java.util.Collections;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ScriptInterceptor implements IMethodInterceptor {

  private final Script script;

  public ScriptInterceptor(Script script) {
    this.script = script;
  }

  public void intercept(final IMethodInvocation invocation) throws Throwable {

    Binding binding = new Binding();
    for (Field field: invocation.getInstance().getClass().getDeclaredFields()) {
      if (!field.isSynthetic()) {
        Object value = field.get(invocation.getInstance());
        if (value == null) {
          value = ((Specification) invocation.getInstance())
            .createMock(field.getName(), field.getType(), MockNature.MOCK, MockImplementation.JAVA, Collections.<String,
              Object>emptyMap(), null);
          field.set(invocation.getInstance(), value);
        }
        binding.setProperty(field.getName(), value);
      }
    }
    Scripts.set(script.value(), new GroovyShell(binding).parse(getClass().getClassLoader().getResource(script.value()).toURI()));
    invocation.proceed();
    Scripts.cleanup();

  }

}
