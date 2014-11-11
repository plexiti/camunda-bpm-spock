package org.camunda.bpm.extension.spock

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.SpecInfo

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
class ScriptExtension extends AbstractAnnotationDrivenExtension<Script> {

    public void visitSpecAnnotation(Script script, SpecInfo spec) {
        spec.features.findAll { !it.featureMethod.reflection.isAnnotationPresent(Script.class) }.each {
            visitFeatureAnnotation(script, it);
        }
    }

    public void visitFeatureAnnotation(Script script, FeatureInfo feature) {
        feature.addIterationInterceptor(new ScriptInterceptor(script));
    }
    
}
