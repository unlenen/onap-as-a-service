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
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.model.OnapRequest;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.OnapAdaptor;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@RestController
@Slf4j
public class DesignService {

    @Autowired
    OnapAdaptor adaptor;

    @GetMapping(path = "/design/service-models", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getServiceModels() throws IOException {
        JSONArray data = (JSONArray) adaptor.call(OnapRequest.SDC_SERVICE_MODELS);
        log.info("[Design][ServiceModels][Get] size:" + data.length());
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/design/vfs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getVFs() throws IOException {
        JSONArray data = (JSONArray) adaptor.call(OnapRequest.SDC_SERVICE_MODELS);
        log.info("[Design][VFs][Get] size:" + data.length());
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/design/vendors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getVendors() throws IOException {
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.SDC_VENDORS);
        log.info("[Design][Vendors][Get] size:" + adaptor.getResponseSize(data, "results"));
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/design/vsps", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getVSPs() throws IOException {
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.SDC_VSPS);
        log.info("[Design][VSPs][Get] size:" + adaptor.getResponseSize(data, "results"));
        return ResponseEntity.ok(data.toString());
    }
}
