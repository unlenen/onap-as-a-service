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
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@Slf4j
public class CloudService {

    @Autowired
    OnapAdaptor adaptor;

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
