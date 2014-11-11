package org.camunda.bpm.extension.spock

import groovy.transform.PackageScope;


/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class Scripts {

    static scripts = new ThreadLocal<Map<String, groovy.lang.Script>>();

    static groovy.lang.Script script(String resource) {
        return map().get(resource);
    }

    static groovy.lang.Script script() {
        if (map().size() == 1) {
            return map().entrySet().iterator().next().getValue();
        }
        throw new UnsupportedOperationException();
    }

    @PackageScope
    static void set(String resource, groovy.lang.Script script) {
        map().put(resource, script);
    }

    @PackageScope
    static Map<String, groovy.lang.Script> cleanup() {
        def map = new HashMap<String, groovy.lang.Script>()
        scripts.set(map);
        return map;
    }

    private static Map<String, groovy.lang.Script> map() {
        scripts.get() ?: cleanup()
    }

}
