package spock

import spock.lang.Specification

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.processEngine

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
abstract class DecisionSpec extends Specification {

    def evaluate(String decision, Map<String, Object> input) {
        def test = processEngine().decisionService.evaluateDecisionTableByKey(decision, input).singleResult
        for (String key: test.keySet()) 
            println "$key: ${test.getEntry(key)}"
        return test
    }

}
