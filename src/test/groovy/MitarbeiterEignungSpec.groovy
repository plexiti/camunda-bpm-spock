import org.camunda.bpm.extension.spock.Deployment
import spock.DecisionSpec
import spock.lang.Unroll

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
class Prüfbeispiele_zur_Bestimmung_der_Eignung_von_Mitarbeitern extends DecisionSpec {

    @Deployment(["dmn/MitarbeiterEignung.dmn"])

    @Unroll("Im Bereich '#Schadenart' entscheiden die Mitarbeiter #Mitarbeiter für Fälle \
             in Höhe von EUR #Schadenhoehe #MitVierAugenPrinzip 4-Augen-Prinzip")

    void "Prüfbeispiele zur Bestimmung der Eignung von Mitarbeitern"() {

        expect: "Je nach Schadenart und -höhe entscheidet einer der geeigneten Mitarbeiter"
        list([type: Schadenart, expenditure: Schadenhoehe]).collect{it.employee} == Mitarbeiter

        and: "Je nach Schadenart und -höhe entscheiden diese allein oder auch nicht"
        list([type: Schadenart, expenditure: Schadenhoehe]).collect{it.'4eyes'} == VierAugenPrinzip

        where: "Fallsituation und Eignung der Mitarbeiter wie folgt"
        Schadenart    | Schadenhoehe | Mitarbeiter                     | VierAugenPrinzip
        "Unfall KFZ"  | 100          | ["Müller", "Meier"]             | [true, false]
        "Unfall KFZ"  | 1500         | ["Meier"]                       | [false]
        "Unfall KFZ"  | 15000        | ["Schmidt"]                     | [true]
        "Haftpflicht" | 400          | ["Mustermann"]                  | [false]
        "Haftpflicht" | 800          | ["Mustermann", "Sonnenschein"]  | [false, false]
        "Haftpflicht" | 4000         | ["Sonnenschein"]                | [false]
        "Haftpflicht" | 8000         | ["Sonnenschein", "Regenmacher"] | [true, true]
        
        and:
        MitVierAugenPrinzip = VierAugenPrinzip.collect{it ? "mit" : "ohne"}

    }

}
