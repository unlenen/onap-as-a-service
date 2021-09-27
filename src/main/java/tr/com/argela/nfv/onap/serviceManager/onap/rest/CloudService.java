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
import java.util.UUID;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.model.OnapRequest;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.OnapAdaptor;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.model.OnapRequestParameters;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@RestController
public class CloudService {

    @Autowired
    OnapAdaptor adaptor;

    Logger log = LoggerFactory.getLogger(CloudService.class);

    @GetMapping(path = "/cloud/complexs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCloudComplex() throws IOException {
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.CLOUD_COMPLEX);
        log.info("[Cloud][Complex][Get] size:" + adaptor.getResponseSize(data, "complex"));
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/cloud/regions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCloudRegions() throws IOException {
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.CLOUD_REGION);
        log.info("[Cloud][Region][Get] size:" + adaptor.getResponseSize(data, "cloud-region"));
        return ResponseEntity.ok(data.toString());
    }

    @PutMapping(path = "/cloud/openstack/{name}/{cloudOwner}/{complexName}/{osDomain}/{osDefaultProject}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createOpenstackRegion(@PathVariable String name, @PathVariable String cloudOwner, @PathVariable String complexName,
            @PathVariable String osDomain, @PathVariable String osDefaultProject,
            @RequestParam(name = "keystoneURL") String osKeystoneURL, @RequestParam(name = "user") String osUser, @RequestParam(name = "password") String osPassword) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        
        UUID esrUUID = UUID.randomUUID();
        
        parameters.put(OnapRequestParameters.CLOUD_ESR_UUID.name(), esrUUID.toString());
        parameters.put(OnapRequestParameters.CLOUD_OS_NAME.name(), name);
        parameters.put(OnapRequestParameters.CLOUD_OWNER.name(), cloudOwner);
        parameters.put(OnapRequestParameters.CLOUD_COMPLEX_NAME.name(), complexName);
        parameters.put(OnapRequestParameters.CLOUD_COMPLEX_NAME.name(), complexName);
        parameters.put(OnapRequestParameters.CLOUD_OS_KEYSTONE_URL.name(), osKeystoneURL);
        parameters.put(OnapRequestParameters.CLOUD_OS_USER.name(), osUser);
        parameters.put(OnapRequestParameters.CLOUD_OS_PASSWORD.name(), osPassword);
        parameters.put(OnapRequestParameters.CLOUD_OS_DOMAIN.name(), osDomain);
        parameters.put(OnapRequestParameters.CLOUD_OS_PROJECT.name(), osDefaultProject);
        String data = (String) adaptor.call(OnapRequest.CLOUD_OS_CREATE, parameters);
        log.info("[Cloud][OpenstackRegion][Create] name: " + name + " , keystone:" + osKeystoneURL + ", domain:" + osDomain + " , osProject:" + osDefaultProject + ",osUser:" + osUser + ", osPass:" + osPassword);
        return ResponseEntity.ok(data);
    }

    @GetMapping(path = "/cloud/tenants/{cloudOwner}/{cloudRegion}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCloudTenants(@PathVariable String cloudOwner, @PathVariable String cloudRegion) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.CLOUD_OWNER.name(), cloudOwner);
        parameters.put(OnapRequestParameters.CLOUD_REGION.name(), cloudRegion);
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.CLOUD_TENANT, parameters);
        log.info("[Cloud][Tenant][Get] size:" + adaptor.getResponseSize(data, "tenant"));
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/cloud/availability-zones/{cloudOwner}/{cloudRegion}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCloudAvailabilityZones(@PathVariable String cloudOwner, @PathVariable String cloudRegion) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.CLOUD_OWNER.name(), cloudOwner);
        parameters.put(OnapRequestParameters.CLOUD_REGION.name(), cloudRegion);
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.CLOUD_AVAILABILITY_ZONE, parameters);
        log.info("[Cloud][AvailabilityZone][Get] size:" + adaptor.getResponseSize(data, "availability-zone"));
        return ResponseEntity.ok(data.toString());
    }
}
