package org.camunda.bpm.extension.spock

import spock.lang.Specification

import static org.spockframework.mock.MockImplementation.JAVA
import static org.spockframework.mock.MockNature.MOCK

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
class Mocks {

    static Binding mock(Specification instance) {
        def binding = new Binding()
        org.camunda.bpm.engine.test.mock.Mocks.reset()
        instance.class.declaredFields.findAll { !it.synthetic }.each {
            def name = it.name
            def value = instance."$name"
            if (!(value || it.type == Object)) {
                value = instance.createMock(it.name, it.type, MOCK, JAVA, [:], null)
                instance."$name" = value
            }
            binding.setProperty(name, value)
            org.camunda.bpm.engine.test.mock.Mocks.register(name, value)
        }
        return binding
    }

}
