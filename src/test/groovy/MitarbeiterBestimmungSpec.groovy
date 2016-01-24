

import org.camunda.bpm.extension.spock.Deployment
import spock.DecisionSpec
import spock.lang.Unroll;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
class Prüfbeispiele_zur_Bestimmung_eines_Mitarbeiters extends DecisionSpec {

    @Deployment(["dmn/MitarbeiterBestimmung.dmn"])

    @Unroll("Im Bereich '#Schadenart' entscheidet Mitarbeiter #Mitarbeiter für Fälle \
             in Höhe von EUR #Schadenhoehe #MitVierAugenPrinzip 4-Augen-Prinzip")

    void "Prüfbeispiele zur Bestimmung eines Mitarbeiters"() {
        
        when: "Das Regelwerk zur Bestimmung eines Mitarbeiters ausgewertet wird ..."

            def decision = find(type: Schadenart, expenditure: Schadenhoehe)
        
        then: "entscheidet je nach Schadenart und -höhe ein bestimmter Mitarbeiter ..."

            decision.'employee' == Mitarbeiter

        and: "ebenfalls nach Schadenart und -höhe entscheidet dieser allein oder auch nicht"

            decision.'4eyes' == VierAugenPrinzip

        where: "Fallsituation und Zuständigkeit des Mitarbeiter wie folgt"

            Schadenart    | Schadenhoehe | Mitarbeiter    | VierAugenPrinzip
            "Unfall KFZ"  | 100          | "Müller"       | false            
            "Unfall KFZ"  | 1500         | "Meier"        | false            
            "Unfall KFZ"  | 15000        | "Schmidt"      | true             
            "Haftpflicht" | 399          | "Mustermann"   | false
            "Haftpflicht" | 400          | "Sonnenschein" | true

        and: "Zur Ausgabe übersetze die Notwendigkeit für vier Augen in ein besser lesbares Wort"

            MitVierAugenPrinzip = VierAugenPrinzip ? "mit" : "ohne"

    }

}
