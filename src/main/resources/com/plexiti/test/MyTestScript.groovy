package com.plexiti.test

import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.ProcessEngineConfiguration

println("Here we are - script with $execution.id!")
def myJarClass = new ProcessEngineConfiguration() {
    @Override
    ProcessEngine buildProcessEngine() {
        return null
    }
}
processEngineConfiguration.processEngineName
false