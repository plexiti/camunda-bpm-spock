package spock

import org.camunda.bpm.extension.spock.Deployment
import spock.lang.Specification

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.*
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*
import static org.camunda.bpm.extension.spock.Scripts.*

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
@Deployment ([
    "dmn/CheckOrderDecision.dmn",
])
public class CheckOrderDecisionSpec extends Specification {

    void "The decision can be deployed"() {
        expect: 
        true
    }

}
