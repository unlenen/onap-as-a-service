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
package tr.com.argela.nfv.onap.serviceManager.onap.scenario;

import com.jayway.jsonpath.Criteria;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import java.util.LinkedHashMap;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tr.com.argela.nfv.onap.serviceManager.onap.rest.RuntimeService;
import tr.com.argela.nfv.onap.serviceManager.onap.rest.model.Service;
import tr.com.argela.nfv.onap.serviceManager.onap.rest.model.ServiceInstance;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@org.springframework.stereotype.Service
public class RuntimeScenario extends CommonScenario {

    Logger log = LoggerFactory.getLogger(RuntimeScenario.class);

    @Autowired
    RuntimeService runtimeService;

    public void processServiceInstances(Service service) throws Exception {

        for (ServiceInstance serviceInstance : service.getServiceInstances()) {
            if (!serviceInstanceExists(serviceInstance)) {
                serviceInstance.setService(service);
                createServiceInstance(serviceInstance);
            }
        }
    }

    private boolean serviceInstanceExists(ServiceInstance serviceInstance) throws Exception {
        String data = readResponseValidateOption(runtimeService.getServiceInstances(serviceInstance.getCustomer().getId()), false);
        Filter serviceInstanceNameFilter = Filter.filter(Criteria.where("name").eq(serviceInstance.getName()));
        DocumentContext rootContext = JsonPath.parse(data);
        net.minidev.json.JSONArray foundServiceInstances = rootContext.read("$[?]", serviceInstanceNameFilter);
        if (!foundServiceInstances.isEmpty()) {
            LinkedHashMap<String, String> serviceInstanceObj = (LinkedHashMap<String, String>) foundServiceInstances.get(0);
            serviceInstance.setId(serviceInstanceObj.get("id"));
            log.info("[Scenario][Runtime][ServiceInstance][Exists] " + serviceInstance);
            return true;
        }
        return false;
    }

    private void createServiceInstance(ServiceInstance serviceInstance) throws Exception {
        JSONObject root = new JSONObject(readResponse(runtimeService.createServiceInstance(serviceInstance.getName(),
                serviceInstance.getService().getInvariantUUID(),
                serviceInstance.getService().getUuid(),
                serviceInstance.getService().getName(),
                serviceInstance.getOwningEntity().getId(),
                serviceInstance.getOwningEntity().getName(),
                serviceInstance.getCustomer().getId(),
                serviceInstance.getProject()
        )));

        JSONObject requestReferences = root.getJSONObject("requestReferences");
        serviceInstance.setId(requestReferences.getString("instanceId"));
        serviceInstance.setReqId(requestReferences.getString("requestId"));
        serviceInstance.setReqUrl(requestReferences.getString("requestSelfLink"));
        log.info("[Scenario][Runtime][ServiceInstance][New] " + serviceInstance);
    }

}
