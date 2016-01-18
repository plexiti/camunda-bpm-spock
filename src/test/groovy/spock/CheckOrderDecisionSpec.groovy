package spock

import org.camunda.bpm.extension.spock.Deployment
import spock.lang.Unroll


/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class CheckOrderDecisionSpec extends DecisionSpec {

    @Deployment (["dmn/CheckOrderDecision.dmn"])
    @Unroll("If you have a status '#status' and an order amount of EUR #sum this is #result!") 
    void "Check Order Decision"() {
        
        expect:
        evaluate("checkOrder", [status: status, sum: sum]).result == result

        where:
        status   | sum     | result
        "gold"   | 354.12  | "ok"
        "silver" | 354.12  | "ok"
        "silver" | 1354.12 | "not ok"
        "bronze" | 354.12  | "not ok"

    }

}
