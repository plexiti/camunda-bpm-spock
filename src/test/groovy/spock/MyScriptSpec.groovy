package spock

import org.camunda.bpm.engine.ProcessEngineConfiguration
import org.camunda.bpm.engine.runtime.Execution
import org.camunda.bpm.extension.spock.Script
import org.camunda.bpm.extension.spock.Scripts
import spock.lang.Specification

import static org.camunda.bpm.extension.spock.Scripts.script

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
@Script('bpmn/MyTestScript.groovy')
public class MyScriptSpec extends Specification {

    public Execution execution
    public ProcessEngineConfiguration processEngineConfiguration
    
    void testScriptWithAssert() {
        when:
        def result = script().run()
        then:
        result == false
        1 * execution.id >> "SampleId"
        1 * processEngineConfiguration.processEngineName >> "default"
        0 * _
    }

    void testScriptWithAssert2() {
        when:
        def result = script().run()
        then:
        result == false
        1 * execution.id >> "SampleId"
        1 * processEngineConfiguration.processEngineName >> "default"
        0 * _
    }

}
