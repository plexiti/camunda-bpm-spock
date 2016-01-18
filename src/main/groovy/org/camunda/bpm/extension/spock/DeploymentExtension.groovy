package org.camunda.bpm.extension.spock

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.SpecInfo;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class DeploymentExtension extends AbstractAnnotationDrivenExtension<Deployment> {

    public void visitSpecAnnotation(Deployment deployment, SpecInfo spec) {
        spec.features.findAll { !it.featureMethod.reflection.isAnnotationPresent(Deployment.class) }.each {
            visitFeatureAnnotation(deployment, it);
        }
    }

    public void visitFeatureAnnotation(Deployment deployment, FeatureInfo feature) {
        feature.addIterationInterceptor(new DeploymentInterceptor(deployment));
    }

}
