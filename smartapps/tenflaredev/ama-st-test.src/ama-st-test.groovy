/**
 *  AMA ST Test
 *
 *  Copyright 2018 Steve Hosterman
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "AMA ST Test",
    namespace: "TenflareDev",
    author: "Steve Hosterman",
    description: "Cloud integration for AMA platform with SmartThings",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png") {
    appSetting "APIKey"
    appSetting "Secret"
}


preferences {
  section ("Allow external service to control these things...") {
    input "switches", "capability.switch", multiple: true, required: true
    input "contactSensors", "capability.contactSensor",title: "Open/close sensors", multiple: true
    input "motionSensors", "capability.motionSensor",title: "Motion sensors?", multiple: true
  }
}

mappings {
  path("/switches") {
    action: [
      GET: "listSwitches"
    ]
  }
  path("/switches/:command") {
    action: [
      PUT: "updateSwitches"
    ]
  }
    path("/contactSensors") {
    action: [
      GET: "listContactSensors"
    ]
  }
  path("/contactSensors/:command") {
    action: [
      PUT: "updateContactSensors"
    ]
  }
    path("/motionSensors") {
    action: [
      GET: "listMotionSensors"
    ]
  }
  path("/motionSensors/:command") {
    action: [
      PUT: "updateMotionSensors"
    ]
  }
}

// returns a list like
// [[name: "kitchen lamp", value: "off"], [name: "bathroom", value: "on"]]
def listSwitches() {

    def resp = []
    switches.each {
        resp << [name: it.displayName, value: it.currentValue("switch")]
    }
    return resp
}

void updateSwitches() {
    // use the built-in request object to get the command parameter
    def command = params.command

    // all switches have the comand
    // execute the command on all switches
    // (note we can do this on the array - the command will be invoked on every element
    switch(command) {
        case "on":
            switches.on()
            break
        case "off":
            switches.off()
            break
        default:
            httpError(400, "$command is not a valid command for all switches specified")
    }
    

}

def listContactSensors() {

    def resp = []
    contactSensors.each {
        resp << [name: it.displayName, value: it.currentValue("contact")]
    }
    return resp
}

def listMotionSensors() {

    def resp = []
    motionSensors.each {
        resp << [name: it.displayName, value: it.currentValue("motion")]
    }
    return resp
}
def installed() {}

def updated() {}