package spock

import spock.lang.Specification

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.processEngine

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
abstract class DecisionSpec extends Specification {

    Map evaluate(Map<String, Object> input) {
        def key = processEngine().repositoryService.createDecisionDefinitionQuery().singleResult().key
        evaluate(key, input)
    }

    Map evaluate(String decision, Map<String, Object> input) {
        def test = processEngine().decisionService.evaluateDecisionTableByKey(decision, input).singleResult
        return test as Map
    }

    Map evaluate(String decision, Integer version, Map<String, Object> input) {
        def test = processEngine().decisionService.evaluateDecisionTableByKeyAndVersion(decision, version, input).singleResult
        return test as Map
    }

}
