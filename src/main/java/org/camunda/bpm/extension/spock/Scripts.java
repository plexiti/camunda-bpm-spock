package org.camunda.bpm.extension.spock;

import groovy.lang.Script;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class Scripts {

  static ThreadLocal<Map<String, Script>> scripts = new ThreadLocal<Map<String, Script>>();

  public static Script script(String resource) {
    return map().get(resource);
  }

  public static Script script() {
    if (map().size() == 1) {
      return map().entrySet().iterator().next().getValue();
    }
    throw new UnsupportedOperationException();
  }

  static void set(String resource, Script script) {
    map().put(resource, script);
  }

  static Map<String, Script> cleanup() {
    Map<String, Script> map;
    scripts.set(map = new HashMap<String, Script>());
    return map;
  }

  private static Map<String, Script> map() {
    return scripts.get() == null ? cleanup(): scripts.get();
  }

}
