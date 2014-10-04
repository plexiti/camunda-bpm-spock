package spock

import org.camunda.bpm.engine.ProcessEngineConfiguration
import org.camunda.bpm.engine.runtime.Execution

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class MyScriptSpec extends ScriptSpecification {

    Execution execution
    ProcessEngineConfiguration processEngineConfiguration
    
    void testScriptWithAssert() {
        when:
        def result = evaluate("com/plexiti/test/MyTestScript.groovy")
        then:
        result == false
        1 * execution.id >> "SampleId"
        1 * processEngineConfiguration.processEngineName >> "default"
        0 * _
    }

    void testScriptWithAssert2() {
        when:
        def result = evaluate("com/plexiti/test/MyTestScript.groovy")
        then:
        result == false
        1 * execution.id >> "SampleId"
        1 * processEngineConfiguration.processEngineName >> "default"
        0 * _
    }

}
