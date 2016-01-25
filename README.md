# <img src="https://hsto.org/storage2/9a8/94f/3cc/9a894f3cc2204d959c37766c57cbd806.png" align="right" width="190" height="190"></img>Camunda BPM Spock

This repository serves to evaluate the [Spock Test Framework](https://github.com/spockframework/spock) for 
[Camunda BPM](https://github.com/camunda/camunda-bpm-platform) in general and DMN testing in particular. 
It could become a new Camunda BPM Community Extension in case there exists some interest and I receive some positive feedback.

**Please :star: star this repository, if you like the basic approach of Camunda BPM Spock or would like to see further development!**

Now consider e.g. the following **DMN** - for now in german unfortunately... 

![DMN definition](/src/test/resources/dmn/MitarbeiterBestimmung.png)

... and the following **Spock Test** class testing this DMN ...

```groovy
class Prüfbeispiele_zur_Bestimmung_eines_Mitarbeiters extends DecisionSpec {                             
                                                                                                         
    @Deployment(["dmn/MitarbeiterBestimmung.dmn"])                                                       
                                                                                                         
    @Unroll("Im Bereich '#Schadenart' entscheidet Mitarbeiter #MitarbeiterErwartet für Fälle            
             in Höhe von EUR #Schadenhoehe #MitVierAugenPrinzip 4-Augen-Prinzip")                        
                                                                                                         
    void "Prüfbeispiele zur Bestimmung eines Mitarbeiters"() {                                           
                                                                                                         
        when: "Das Regelwerk zur Bestimmung eines Mitarbeiters ausgewertet wird ..."                     
                                                                                                         
            Map decision = unique(type: Schadenart, expenditure: Schadenhoehe)                           
                                                                                                         
        then: "entscheidet je nach Schadenart und -höhe ein erwarteter Mitarbeiter ..."                  
                                                                                                         
            decision['employee'] == MitarbeiterErwartet                                                  
                                                                                                         
        and: "ebenfalls nach Schadenart und -höhe entscheidet dieser wie erwartet allein oder auch nicht"
                                                                                                         
            decision['4eyes'] == VierAugenErwartet                                                       
                                                                                                         
        where: "Fallsituation und Zuständigkeit des Mitarbeiter wie folgt"                               
                                                                                                         
            Schadenart    | Schadenhoehe | MitarbeiterErwartet | VierAugenErwartet                       
            "Unfall KFZ"  | 100          | "Müller"            | false                                   
            "Unfall KFZ"  | 1500         | "Meier"             | false                                   
            "Unfall KFZ"  | 15000        | "Schmid"            | true                                    
            "Haftpflicht" | 399          | "Mustermann"        | false                                   
            "Haftpflicht" | 400          | "Sonnenschein"      | true                                    
                                                                                                         
    }                                                                                                    
                                                                                                         
}                                                                                                        
```

... which would lead to the following **Test Report**:

![DMN Report](/src/test/resources/dmn/report.png)
![DMN error detail](/src/test/resources/dmn/detail.png)

Quite nice, isn't it?

The nature of Groovy as being extremely accessible for Java Developers as well as flexible enough to define "domain 
specific languages" like e.g. used here by [Spock](https://github.com/spockframework/spock) for directly compilable 
"Given, When, Then, Where"-Scenarios and business readable, compilable Data Tables is particularly 
interesting in the area of DMN testing. This area demands readability and maintainability of tests for both developers as 
well as more business oriented staff like e.g. business analysts. I suspect that at least in some organisational setups it 
will be prefereable to maintain **one shared base** of **DMN tests** over splitting the responsibility for such tests into 
more developer oriented tests implemented with e.g. JUnit on the development side and more business oriented tests 
implemented with testing tools like e.g. Fitnesse on the business side.

Therefore I propose here to consider **DMN testing with Spock** as a viable alternative to other approaches. And aside that, I also propose to live long and prosper!
