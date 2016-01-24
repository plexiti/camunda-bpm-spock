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

    List<Map<String, Object>> list(Map<String, Object> input) {
        def key = processEngine().repositoryService.createDecisionDefinitionQuery().singleResult().key
        list(key, input)
    }

    List<Map<String, Object>> list(String decision, Map<String, Object> input) {
        def test = processEngine().decisionService.evaluateDecisionTableByKey(decision, input).resultList
        return test as List<Map<String, Object>>
    }

    List<Map<String, Object>> list(String decision, Integer version, Map<String, Object> input) {
        def test = processEngine().decisionService.evaluateDecisionTableByKeyAndVersion(decision, version, input).resultList
        return test as List<Map<String, Object>>
    }

}
