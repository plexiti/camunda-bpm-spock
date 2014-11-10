package org.camunda.bpm.extension.spock;

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension;
import org.spockframework.runtime.model.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class DeploymentExtension extends AbstractAnnotationDrivenExtension<Deployment> {

  @Override
  public void visitSpecAnnotation(Deployment deployment, SpecInfo spec) {
    for (FeatureInfo feature : spec.getFeatures()) {
      if (!feature.getFeatureMethod().getReflection().isAnnotationPresent(Deployment.class)) {
        visitFeatureAnnotation(deployment, feature);
      }
    }
  }
  
  @Override
  public void visitFeatureAnnotation(Deployment deployment, FeatureInfo feature) {
    feature.addIterationInterceptor(new DeploymentInterceptor(deployment));
  }

}
