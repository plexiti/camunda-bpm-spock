package org.camunda.bpm.extension.spock

import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.ProcessEngines
import org.camunda.bpm.engine.impl.test.TestHelper
import org.camunda.bpm.engine.repository.DeploymentBuilder
import org.spockframework.runtime.extension.IMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation

import static org.spockframework.mock.MockImplementation.JAVA
import static org.spockframework.mock.MockNature.MOCK;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class DeploymentInterceptor implements IMethodInterceptor {

    final Deployment deployment;

    public DeploymentInterceptor(Deployment deployment) {
        this.deployment = deployment;
    }

    public static Object run(String resource) {
        return Scripts.script(resource).run();
    }

    public void intercept(final IMethodInvocation invocation) throws Throwable {
        DeploymentBuilder deploymentBuilder =
                processEngine().repositoryService
                        .createDeployment()
                        .enableDuplicateFiltering()
                        .name("SpockSpecificationDeployment")
        deployment.value().each { String resource ->
            if (resource.endsWith(".groovy")) {
                deploymentBuilder.addString(resource, String.format("%s.run('%s')", DeploymentInterceptor.class.name, resource))
                groovy.lang.Script mock = invocation.getInstance().createMock(null, groovy.lang.Script, MOCK, JAVA, [:], null)
                Scripts.set(resource, mock);
            } else {
                deploymentBuilder.addClasspathResource(resource);
            }
        }
        def deployment = deploymentBuilder.deploy()
        invocation.proceed();
        processEngine().repositoryService.deleteDeployment(deployment.id, true);
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
