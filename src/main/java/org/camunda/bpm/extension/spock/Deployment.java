package org.camunda.bpm.extension.spock;

import org.spockframework.runtime.extension.ExtensionAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@ExtensionAnnotation(DeploymentExtension.class)
public @interface Deployment {
  
  /** Specify resources that make up the process definition. */
  String[] value() default {};

}
