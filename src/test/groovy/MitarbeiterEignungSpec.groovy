import org.camunda.bpm.extension.spock.Deployment
import spock.DecisionSpec
import spock.lang.Unroll

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
class Prüfbeispiele_zur_Bestimmung_der_Eignung_von_Mitarbeitern extends DecisionSpec {

    @Deployment(["dmn/MitarbeiterEignung.dmn"])

    @Unroll("Im Bereich '#Schadenart' entscheiden die Mitarbeiter #MitarbeiterErwartet für Fälle \
             in Höhe von EUR #Schadenhoehe #MitVierAugenPrinzip 4-Augen-Prinzip")

    void "Prüfbeispiele zur Bestimmung der Eignung von Mitarbeitern"() {

        when: "Das Regelwerk zur Bestimmung der Eignung von Mitarbeitern ausgewertet wird ..."

            Map decision = collect(type: Schadenart, expenditure: Schadenhoehe)

        then: "entscheidet je nach Schadenart und -höhe einer der erwarteten Mitarbeiter ..."

            decision['employee'] == MitarbeiterErwartet

        and: "ebenfalls nach Schadenart und -höhe entscheiden diese wie erwartet allein oder auch nicht"

            decision['4eyes'] == VierAugenErwartet

        where: "Fallsituation und Eignung der Mitarbeiter wie folgt"

            Schadenart    | Schadenhoehe | MitarbeiterErwartet             | VierAugenErwartet
            "Unfall KFZ"  | 100          | ["Müller", "Meier"]             | [true, false]
            "Unfall KFZ"  | 1500         | ["Meier"]                       | [false]
            "Unfall KFZ"  | 15000        | ["Schmidt"]                     | [true]
            "Haftpflicht" | 400          | ["Mustermann"]                  | [false]
            "Haftpflicht" | 800          | ["Mustermann", "Sonnenschein"]  | [false, false]
            "Haftpflicht" | 4000         | ["Sonnenschein"]                | [false]
            "Haftpflicht" | 8000         | ["Sonnenschein", "Regenmacher"] | [true, true]

        and: "Zur Ausgabe übersetze die Notwendigkeit für vier Augen in ein besser lesbares Wort"

            MitVierAugenPrinzip = VierAugenErwartet.collect { it ? "mit" : "ohne" }

    }

}
