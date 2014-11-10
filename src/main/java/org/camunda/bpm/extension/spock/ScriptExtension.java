package org.camunda.bpm.extension.spock;

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension;
import org.spockframework.runtime.model.FeatureInfo;
import org.spockframework.runtime.model.SpecInfo;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ScriptExtension extends AbstractAnnotationDrivenExtension<Script> {

  @Override
  public void visitSpecAnnotation(Script script, SpecInfo spec) {
    for (FeatureInfo feature : spec.getFeatures()) {
      if (!feature.getFeatureMethod().getReflection().isAnnotationPresent(Script.class)) {
        visitFeatureAnnotation(script, feature);
      }
    }
  }
  
  @Override
  public void visitFeatureAnnotation(Script script, FeatureInfo feature) {
    feature.addIterationInterceptor(new ScriptInterceptor(script));
  }

}
