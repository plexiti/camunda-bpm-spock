package spock

import org.camunda.bpm.engine.repository.DeploymentBuilder

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*
import spock.lang.Specification

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
abstract class ProcessSpecification extends Specification {

    private static ThreadLocal<Map<String, Script>> scripts = new ThreadLocal<Map<String, Script>>()
    
    static {
        scripts.set(new HashMap<String, Script>())
    }
    
    void cleanup() {
        undeploy()
        scripts.set(new HashMap<String, Script>())
    }

    private final static String deploymentName = "SpockSpecificationDeployment";

    /**
     * Helper method to easily deploy process definitions
     * and other class path resources.
     * @param resources Class path resources to be deployed
     */
    public void deploy(String... resources) {
        DeploymentBuilder deployment = repositoryService()
            .createDeployment()
            .enableDuplicateFiltering()
            .name(deploymentName);
        for (String resource: resources) {
            if (resource.endsWith(".groovy")) {
                deployment.addString(resource, "${ProcessSpecification.class.name}.call('$resource')")
                scripts.get().put(resource, Mock(Script))
            } else {
                deployment.addClasspathResource(resource);
            }
        }
        deployment.deploy();
    }

    /**
     * Helper method to easily undeploy process definitions
     */
    public void undeploy() {
        repositoryService().deleteDeployment(deploymentName, true);
    }
    
    public static def call(String resource) {
        script(resource).run()
    }

    static Script script(String resource) {
        scripts.get().get(resource)
    }

}
