package spock

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class MyProcessSpec extends ProcessSpecification {
    
    def key = "MyTestProcess"
    
    def setup() {
        deploy(
            "${key}.bpmn",
            "com/plexiti/test/MyTestScript.groovy", 
        )
    }
    
    void "The process can be started"() {
        when: 
        def pi = runtimeService().startProcessInstanceByKey(key)
        then: 
        assertThat(pi).isNotEnded()
        and:
        0 * _
    }

    void "The process can be fully executed and ended"() {
        given:
        def pi = runtimeService().startProcessInstanceByKey(key)
        when:
        complete(task(pi))
        then:
        assertThat(pi).isEnded()
        and:
        2 * script("com/plexiti/test/MyTestScript.groovy").run() >> { true } >> { false }
        0 * _
    }

}
