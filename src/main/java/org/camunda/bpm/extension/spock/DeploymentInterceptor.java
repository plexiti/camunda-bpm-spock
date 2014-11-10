package org.camunda.bpm.extension.spock;

import groovy.lang.Script;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.impl.test.TestHelper;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.spockframework.mock.MockImplementation;
import org.spockframework.mock.MockNature;
import org.spockframework.runtime.extension.IMethodInterceptor;
import org.spockframework.runtime.extension.IMethodInvocation;
import spock.lang.Specification;

import java.util.Collections;
import java.util.Map;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class DeploymentInterceptor implements IMethodInterceptor {

  private final Deployment deployment;

  public DeploymentInterceptor(Deployment deployment) {
    this.deployment = deployment;
  }

  public static Object run(String resource) {
    return Scripts.script(resource).run();
  }

  public void intercept(final IMethodInvocation invocation) throws Throwable {
    DeploymentBuilder deploymentBuilder = processEngine().getRepositoryService()
      .createDeployment()
      .enableDuplicateFiltering()
      .name("SpockSpecificationDeployment");
    for (String resource: this.deployment.resources()) {
      if (resource.endsWith(".groovy")) {
        deploymentBuilder.addString(resource, String.format("%s.run('%s')", getClass().getName(), resource));
        Script mock = (Script) ((Specification) invocation.getInstance())
          .createMock(null, Script.class, MockNature.MOCK, MockImplementation.JAVA, Collections.<String, Object>emptyMap(), null);
        Scripts.set(resource, mock);
      } else {
        deploymentBuilder.addClasspathResource(resource);
      }
    }
    org.camunda.bpm.engine.repository.Deployment deployment = deploymentBuilder.deploy();
    invocation.proceed();
    processEngine().getRepositoryService().deleteDeployment(deployment.getId(), true);
    Scripts.cleanup();
  }

  private static ProcessEngine processEngine() {
    Map<String, ProcessEngine> processEngines = ProcessEngines.getProcessEngines();
    if (processEngines.size() == 1) {
      return processEngines.values().iterator().next();
    }
    return TestHelper.getProcessEngine("camunda.cfg.xml");
  }
  
}
