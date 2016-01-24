package spock

import spock.lang.Specification

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.processEngine

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
abstract class DecisionSpec extends Specification {

    Map<String, Object> find(Map<String, Object> input) {
        def key = processEngine().repositoryService.createDecisionDefinitionQuery().singleResult().key
        find(key, input)
    }

    Map<String, Object> find(String decision, Map<String, Object> input) {
        processEngine().decisionService.evaluateDecisionTableByKey(decision, input).singleResult
    }

    Map<String, Object> find(String decision, Integer version, Map<String, Object> input) {
        processEngine().decisionService.evaluateDecisionTableByKeyAndVersion(decision, version, input).singleResult
    }

    Map<String, List> list(Map<String, Object> input) {
        def key = processEngine().repositoryService.createDecisionDefinitionQuery().singleResult().key
        list(key, input)
    }

    Map<String, List> list(String decision, Map<String, Object> input) {
        def result = processEngine().decisionService.evaluateDecisionTableByKey(decision, input).resultList
        result[0]?.keySet()?.collectEntries{key->[key, result.collect {it.get(key)}]}
    }

    Map<String, List> list(String decision, Integer version, Map<String, Object> input) {
        def result = processEngine().decisionService.evaluateDecisionTableByKeyAndVersion(decision, version, input).resultList
        result[0]?.keySet()?.collectEntries{key->[key, result.collect {it.get(key)}]}
    }

}
