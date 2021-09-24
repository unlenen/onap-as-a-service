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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.model.OnapRequest;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.OnapAdaptor;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.model.OnapRequestParameters;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@RestController
@Slf4j
public class RuntimeService {

    @Autowired
    OnapAdaptor adaptor;

    @GetMapping(path = "/runtime/service-instances/{customerName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getServiceInstances(@PathVariable String customerName) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.BUSINESS_CUSTOMER_NAME.name(), customerName);
        JSONArray data = (JSONArray) adaptor.call(OnapRequest.RUNTIME_SERVICE_INSTANCES, parameters);
        log.info("[Runtime][ServiceInstances][Get] customerName:" + customerName + ", size:" + data.length());
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/runtime/service-instance/{serviceInstanceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getServiceInstanceDetails(@PathVariable String serviceInstanceId) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.RUNTIME_SERVICE_INSTANCE_ID.name(), serviceInstanceId);
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.RUNTIME_SERVICE_INSTANCE_DETAIL, parameters);
        log.info("[Runtime][ServiceInstanceDetail][Get] service-instance-id:" + serviceInstanceId + " , response:" + data.toString());
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/runtime/vnfs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getVNFs() throws IOException {
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.RUNTIME_VNFS);
        log.info("[Runtime][Vnfs][Get] size: " + adaptor.getResponseSize(data, "generic-vnf"));
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/runtime/vnf/{vnfId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getVNFDetail(@PathVariable String vnfId) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.RUNTIME_VNF_ID.name(), vnfId);
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.RUNTIME_VNF_DETAIL, parameters);
        log.info("[Runtime][VnfDetail][Get] vnfId:" + vnfId + " , response: " + data);
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/runtime/vf-modules/{vnfId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getVFModules(@PathVariable String vnfId) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.RUNTIME_VNF_ID.name(), vnfId);
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.RUNTIME_VFMODULES, parameters);
        log.info("[Runtime][VFModules][Get] vnfId:" + vnfId + " , size: " + adaptor.getResponseSize(data, "vf-module"));
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/runtime/vf-module/{vnfId}/{vfModuleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getVFModuleDetail(@PathVariable String vnfId, @PathVariable String vfModuleId) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.RUNTIME_VNF_ID.name(), vnfId);
        parameters.put(OnapRequestParameters.RUNTIME_VF_MODULE_ID.name(), vfModuleId);
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.RUNTIME_VFMODULE_DETAIL, parameters);
        log.info("[Runtime][VFModuleDetail][Get] vnfId:" + vnfId + ",vfModuleId:" + vfModuleId + " , response:" + data.toString());
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/runtime/vf-module-properties/{vfModuleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getVFModuleDetail(@PathVariable String vfModuleId) throws IOException {
        Map<String, String> parameters = new HashMap<>();

        parameters.put(OnapRequestParameters.RUNTIME_VF_MODULE_ID.name(), vfModuleId);
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.RUNTIME_VFMODULE_INSTANTIATE_DETAIL, parameters);
        log.info("[Runtime][VFModuleDetail][Get] vfModuleId:" + vfModuleId + " , response:" + data.toString());
        return ResponseEntity.ok(data.toString());
    }

}
