package spock

import org.camunda.bpm.extension.spock.*
import spock.lang.Specification

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*
import static org.camunda.bpm.extension.spock.Scripts.*

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
@Deployment ([
    "bpmn/MyTestProcess.bpmn",
    "bpmn/MyTestScript.groovy",
])
public class MyProcessSpec extends Specification {

    void "The process can be started"() {
        when: 
        def pi = runtimeService().startProcessInstanceByKey('MyTestProcess')
        then: 
        assertThat(pi).isNotEnded()
        and:
        0 * _
    }

    void "The process can be fully executed and ended"() {
        given: 
        def pi = runtimeService().startProcessInstanceByKey('MyTestProcess')
        when:
        complete(task(pi))
        then:
        assertThat(pi).isEnded()
        and:
        2 * script().run() >> { true } >> { false }
        0 * _
    }

}
