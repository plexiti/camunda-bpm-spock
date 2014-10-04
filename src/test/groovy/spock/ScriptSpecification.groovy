package spock

import spock.lang.Specification

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
abstract class ScriptSpecification extends Specification {

    private GroovyShell shell

    void setup() {
        def binding = new Binding()
        this.class.declaredFields.findAll { !it.synthetic }.each {
            def value = this."$it.name"
            if (value == null) {
                this."$it.name" = Mock(it.type)
            }
            binding.setProperty(it.name, this."$it.name")
        }
        shell = new GroovyShell(binding)
    }
    
    def evaluate(String resource) {
        shell.evaluate(new File("src/main/resources/$resource"))
    }
    
}
