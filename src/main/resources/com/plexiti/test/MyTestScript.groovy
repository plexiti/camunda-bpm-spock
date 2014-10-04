package com.plexiti.test

import com.plexiti.other.MyOtherClass
import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.ProcessEngineConfiguration

println("Here we are - script with $execution.id!")
def myOtherClass = new MyOtherClass()
def myJarClass = new ProcessEngineConfiguration() {
    @Override
    ProcessEngine buildProcessEngine() {
        return null
    }
}
processEngineConfiguration.processEngineName
false