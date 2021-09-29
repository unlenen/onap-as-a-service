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
import java.util.Locale;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tr.com.argela.nfv.onap.serviceManager.onap.rest.DesignService;
import tr.com.argela.nfv.onap.serviceManager.onap.rest.model.Service;
import tr.com.argela.nfv.onap.serviceManager.onap.rest.model.VF;
import tr.com.argela.nfv.onap.serviceManager.onap.rest.model.constant.EntityStatus;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@org.springframework.stereotype.Service
public class ServiceModelScenario extends CommonScenario {

    Logger log = LoggerFactory.getLogger(ServiceModelScenario.class);
    @Autowired
    DesignService designService;

    public void processService(Service service) throws Exception {
        if (serviceExists(service)) {
            readServiceUniqueId(service);
        } else {
            createService(service);
        }

        for (VF vf : service.getVfs()) {
            addVfToService(vf);
        }
    }

    private boolean serviceExists(Service service) throws Exception {
        String root = readResponse(designService.getServiceModels(), true);
        Filter serviceName = Filter.filter(Criteria.where("name").eq(service.getName()));
        DocumentContext rootContext = JsonPath.parse(root);
        net.minidev.json.JSONArray services = rootContext.read("$[?]", serviceName);
        if (!services.isEmpty()) {
            LinkedHashMap<String, String> vfObject = (LinkedHashMap<String, String>) services.get(0);
            service.setUuid(vfObject.get("uuid"));
            service.setInvariantUUID(vfObject.get("invariantUUID"));
            service.setVersionStatus(EntityStatus.valueOf(vfObject.get("lifecycleState").toUpperCase(Locale.ENGLISH)));
            log.info("[Scenario][Service][Exists] service:" + service.getName() + " , uuid : " + service.getUuid() + " , invariantUUID :" + service.getInvariantUUID() + " , serviceStatus:" + service.getVersionStatus());
        }
        return !services.isEmpty();
    }

    private void createService(Service service) throws Exception {
        JSONObject root = new JSONObject(readResponse(designService.createServiceModel(service.getName(), service.getDescription())));
        service.setInvariantUUID(root.getString("invariantUUID"));
        service.setUniqueId(root.getString("uniqueId"));
        service.setUuid(root.getString("uuid"));
        service.setVersionStatus(EntityStatus.valueOf(root.getString("lifecycleState").toUpperCase(Locale.ENGLISH)));
        log.info("[Scenario][Service][New] service:" + service.getName() + " , uuid : " + service.getUuid() + " , invariantUUID :" + service.getInvariantUUID() + " , uniqueId:" + service.getUniqueId() + " , serviceStatus:" + service.getVersionStatus());
    }

    private void addVfToService(VF vf) throws Exception {
        Service service = vf.getService();
        log.info("[Scenario][Service][AddVf] service:" + service.getName() + " , uuid : " + service.getUuid() + " , invariantUUID :" + service.getInvariantUUID() + " , uniqueId:" + service.getUniqueId() + " , serviceStatus:" + service.getVersionStatus() + " , vf:" + vf.getName() + " , vfUniqueId:" + vf.getUniqueId());
        String root = readResponse(designService.addVFtoServiceModel(vf.getService().getUniqueId(), vf.getUniqueId(), vf.getName()));
        System.out.println(root);
    }

    private void readServiceUniqueId(Service service) throws Exception {
        JSONObject root = new JSONObject(readResponse(designService.getServiceModelUniqueId(service.getUuid())));
        service.setUniqueId(root.getString("uniqueId"));
        service.setVersionStatus(EntityStatus.valueOf(root.getString("lifecycleState").toUpperCase(Locale.ENGLISH)));
        log.info("[Scenario][VF][Exists][UniqueId] service:" + service.getName() + " , uuid : " + service.getUuid() + " , invariantUUID :" + service.getInvariantUUID() + " , uniqueId:" + service.getUniqueId());
    }

}
