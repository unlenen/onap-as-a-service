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
import tr.com.argela.nfv.onap.serviceManager.onap.rest.model.Tenant;
import tr.com.argela.nfv.onap.serviceManager.onap.rest.model.VFModule;
import tr.com.argela.nfv.onap.serviceManager.onap.rest.model.VNF;

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
            serviceInstance.setService(service);
            if (!serviceInstanceExists(serviceInstance)) {
                createServiceInstance(serviceInstance);
                waitForComplete(serviceInstance.getReqUrl(), serviceInstance);
            }
            processVNFs(serviceInstance);
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

    private void waitForComplete(String reqUrl, Object obj) throws Exception {
        int tryCount = 0;
        while (true) {
            String currentStatus = "";
            JSONObject root = new JSONObject(readResponse(runtimeService.getActionStatus(reqUrl)));
            if (root.has("request")) {
                JSONObject request = root.getJSONObject("request");
                if (request.has("requestStatus")) {
                    JSONObject requestStatus = request.getJSONObject("requestStatus");
                    currentStatus = requestStatus.getString("requestState");
                    if ("COMPLETE".equals(currentStatus)) {
                        log.info("[Scenario][Runtime][ActionWaiting][Complete] url:" + reqUrl + " , data:" + obj);
                        break;
                    }
                }
            }
            tryCount++;
            Thread.sleep(1000);
            if (tryCount % 5 == 0) {
                log.info("[Scenario][Runtime][ActionWaiting][" + currentStatus + "] url:" + reqUrl + " , data:" + obj);
            }
        }
    }

    private void processVNFs(ServiceInstance serviceInstance) throws Exception {
        for (VNF vnf : serviceInstance.getVnfs()) {
            vnf.setServiceInstance(serviceInstance);
            vnf.setVf(vnf.getServiceInstance().getService().getVFByName(vnf.getVf().getName()));
            Tenant tenant = vnf.getServiceInstance().getService().getScenario().getTenantMapById().get(vnf.getTenant().getId());
            vnf.setTenant(tenant);
            if (!vnfExists(vnf)) {
                createVNF(vnf);
                waitForComplete(vnf.getReqUrl(), vnf);
            }
            processVFModules(vnf);
        }
    }

    private boolean vnfExists(VNF vnf) throws Exception {
        String data = readResponseValidateOption(runtimeService.getVNFDetailByService(
                vnf.getServiceInstance().getService().getUniqueId(),
                vnf.getName()),
                false
        );
        Filter vnfName = Filter.filter(Criteria.where("vnf-name").eq(vnf.getName()));
        DocumentContext rootContext = JsonPath.parse(data);
        net.minidev.json.JSONArray foundVnf = rootContext.read("$['generic-vnf'][?]", vnfName);
        if (!foundVnf.isEmpty()) {
            LinkedHashMap<String, String> vnfObj = (LinkedHashMap<String, String>) foundVnf.get(0);
            vnf.setId(vnfObj.get("vnf-id"));
            vnf.setType(vnfObj.get("vnf-type"));
            vnf.setType(vnfObj.get("vnf-type"));
            vnf.getVf().setModelCustomizationUUID(vnfObj.get("model-customization-id"));

            log.info("[Scenario][Runtime][VNF][Exists] " + vnf);
            return true;
        }
        return false;
    }

    private void createVNF(VNF vnf) throws Exception {

        JSONObject root = new JSONObject(readResponse(runtimeService.createVNF(vnf.getName(),
                vnf.getServiceInstance().getId(),
                vnf.getServiceInstance().getService().getName(),
                vnf.getServiceInstance().getService().getInvariantUUID(),
                vnf.getServiceInstance().getService().getUuid(),
                vnf.getServiceInstance().getService().getUniqueId(),
                vnf.getTenant().getCloudRegion().getCloudOwner(),
                vnf.getTenant().getCloudRegion().getName(),
                vnf.getTenant().getId(),
                vnf.getVf().getName(),
                vnf.getVf().getModelName(),
                vnf.getVf().getInvariantUUID(),
                vnf.getVf().getUuid(),
                vnf.getLineOfBusiness(),
                vnf.getPlatform())));

        JSONObject requestReferences = root.getJSONObject("requestReferences");
        vnf.setId(requestReferences.getString("instanceId"));
        vnf.setReqId(requestReferences.getString("requestId"));
        vnf.setReqUrl(requestReferences.getString("requestSelfLink"));
        log.info("[Scenario][Runtime][VNF][New] " + vnf);
    }

    private void processVFModules(VNF vnf) throws Exception {
        if (vnf.getVfModules() != null) {
            for (VFModule vfModule : vnf.getVfModules()) {
                vfModule.setVnf(vnf);
                vfModule.setProfile(vnf.getServiceInstance().getService().getScenario().getProfileMapByName().get(vfModule.getProfile().getName()));
                if (!vfModuleExists(vfModule)) {
                    preloadVFModule(vfModule);
                    createVFModule(vfModule);
                    waitForComplete(vfModule.getReqUrl(), vfModule);
                }
            }
        } else {
            log.info("[Scenario][Runtime][VNF][No VF-Module] " + vnf);
        }
    }

    private boolean vfModuleExists(VFModule vfModule) throws Exception {
        JSONObject root = new JSONObject(readResponseValidateOption(runtimeService.getVFModules(vfModule.getVnf().getId()), false));
        if (root.has("error")) {
            return false;
        }
        log.info("[Scenario][Runtime][VF-Module][Exists] " + vfModule);
        return true;
    }

    private void preloadVFModule(VFModule vfModule) throws Exception {

        JSONObject root = new JSONObject(readResponse(runtimeService.preloadVFModule(
                vfModule.getVnf().getName(),
                vfModule.getVnf().getVf().getModelName(),
                vfModule.getName(),
                vfModule.getVnf().getVf().getModelType(),
                vfModule.getAvailabilityZone(),
                vfModule.getProfile()
        )));
        String preloadResult = "fail";
        if (root.has("output")) {
            preloadResult = root.getJSONObject("output").getString("response-message");

        }
        log.info("[Scenario][Runtime][VF-Module][Preload] status:" + preloadResult + " " + vfModule);
    }

    private void createVFModule(VFModule vfModule) throws Exception {
        VNF vnf = vfModule.getVnf();
        JSONObject root = new JSONObject(readResponse(runtimeService.createVfModule(
                vfModule.getVnf().getId(),
                vfModule.getName(),
                vnf.getServiceInstance().getId(),
                vnf.getServiceInstance().getService().getName(),
                vnf.getServiceInstance().getService().getInvariantUUID(),
                vnf.getServiceInstance().getService().getUuid(),
                vnf.getTenant().getCloudRegion().getCloudOwner(),
                vnf.getTenant().getCloudRegion().getName(),
                vnf.getTenant().getId(),
                vnf.getVf().getName(),
                vnf.getVf().getModelUUID(),
                vnf.getVf().getModelType(),
                vnf.getVf().getModelName(),
                vnf.getVf().getModelInvariantUUID(),
                vnf.getVf().getUuid(),
                vnf.getVf().getModelCustomizationUUID()
        )));

        JSONObject requestReferences = root.getJSONObject("requestReferences");
        vfModule.setId(requestReferences.getString("instanceId"));
        vfModule.setReqId(requestReferences.getString("requestId"));
        vfModule.setReqUrl(requestReferences.getString("requestSelfLink"));
        log.info("[Scenario][Runtime][VFModule][New] " + vfModule);
    }

}
