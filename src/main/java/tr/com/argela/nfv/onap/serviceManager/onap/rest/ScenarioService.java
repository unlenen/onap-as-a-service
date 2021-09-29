/*
# Copyright © 2021 Argela Technologies
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
 */
package tr.com.argela.nfv.onap.serviceManager.onap.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.OnapAdaptor;
import tr.com.argela.nfv.onap.serviceManager.onap.rest.model.Scenario;
import tr.com.argela.nfv.onap.serviceManager.onap.scenario.ServiceModelScenario;
import tr.com.argela.nfv.onap.serviceManager.onap.scenario.VFScenario;
import tr.com.argela.nfv.onap.serviceManager.onap.scenario.VSPScenario;
import tr.com.argela.nfv.onap.serviceManager.onap.scenario.VendorScenario;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@RestController
public class ScenarioService {

    @Autowired
    OnapAdaptor adaptor;

    @Autowired
    VendorScenario vendorScenario;

    @Autowired
    VSPScenario vspScenario;

    @Autowired
    VFScenario vfScenario;

    @Autowired
    ServiceModelScenario serviceModelScenario;

    Logger log = LoggerFactory.getLogger(ScenarioService.class);

    ObjectMapper mapper;

    public ScenarioService() {
        mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
    }

    @PostMapping(path = "/scenario/load", consumes = "application/x-yaml", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Scenario> loadScenario(@RequestBody String yaml) throws Exception {
        Scenario scenario = convertToYaml(yaml);
        runScenario(scenario);
        return ResponseEntity.ok(scenario);
    }

    private Scenario convertToYaml(String yaml) throws JsonProcessingException {
        Scenario scenario = mapper.readValue(yaml, Scenario.class);
        return scenario;
    }

    private void runScenario(Scenario scenario) throws Exception {

        vendorScenario.processVendor(scenario.getVendor());
        vspScenario.processVSPs(scenario.getVendor());
        vfScenario.processVFs(scenario);
        serviceModelScenario.processService(scenario.getService());
    }

}
