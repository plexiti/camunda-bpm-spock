package spock

import org.camunda.bpm.extension.spock.Deployment
import spock.lang.Unroll


/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class CheckOrderDecisionSpec extends DecisionSpec {

    @Deployment (["dmn/CheckOrderDecision.dmn"])
    @Unroll("If you have a status '#status' and an order amount of EUR #sum this is #result!") 
    void "Verify Order Decision By Example"() {
        
        expect:
        evaluate("checkOrder", [status: status, sum: sum]).result == result

        where:
        status   | sum     | result
        "gold"   | 354.12  | "ok"
        "silver" | 354.12  | "ok"
        "silver" | 1354.12 | "not ok"
        "bronze" | 354.12  | "not ok"

    }

    @Deployment (["dmn/CheckOrderDecision.dmn", "dmn/CheckOrderDecision-first.dmn"])
    @Unroll("Verify a matching result for status '#status' and order amount of EUR #sum!")
    void "Refactor Order Decision To Use Hit Policy First"() {

        expect:
        evaluate("checkOrder", [status: status, sum: sum]).result == evaluate("checkOrder-first", [status: status, sum: sum]).result

        where:
        status   | sum     
        "gold"   | 354.12  
        "silver" | 354.12  
        "silver" | 1354.12 
        "bronze" | 354.12 

    }
    
}
