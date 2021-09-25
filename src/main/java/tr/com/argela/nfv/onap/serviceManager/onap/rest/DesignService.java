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

import java.io.File;
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
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping(path = "/design/vendor/{vendorName}/{vendorDescription}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity setVendor(@PathVariable String vendorName, @PathVariable(required = false) String vendorDescription) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.DESIGN_VENDOR_NAME.name(), vendorName);
        parameters.put(OnapRequestParameters.DESIGN_VENDOR_DESC.name(), vendorDescription);
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.SDC_VENDOR_CREATE, parameters);
        log.info("[Design][Vendor][Set] vendorName:" + vendorName + ", id:" + adaptor.getResponseItem(data, "itemId"));
        return ResponseEntity.ok(data.toString());
    }

    @PutMapping(path = "/design/vendor-submit/{vendorId}/{vendorVersionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity submitVendor(@PathVariable String vendorId, @PathVariable String vendorVersionId) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.DESIGN_VENDOR_ID.name(), vendorId);
        parameters.put(OnapRequestParameters.DESIGN_VENDOR_VERSION_ID.name(), vendorVersionId);
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.SDC_VENDOR_SUBMIT, parameters);
        log.info("[Design][Vendor][Submit] vendorId:" + vendorId + " ,vendorVersionId:" + vendorVersionId);
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/design/vsps", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getVSPs() throws IOException {
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.SDC_VSPS);
        log.info("[Design][VSPs][Get] size:" + adaptor.getResponseSize(data, "results"));
        return ResponseEntity.ok(data.toString());
    }

    @PutMapping(path = "/design/vsp/{vendorId}/{vendorName}/{vspName}/{vspDescription}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity setVsp(@PathVariable String vendorId, @PathVariable String vendorName, @PathVariable String vspName, @PathVariable String vspDescription) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.DESIGN_VENDOR_ID.name(), vendorId);
        parameters.put(OnapRequestParameters.DESIGN_VENDOR_NAME.name(), vendorName);
        parameters.put(OnapRequestParameters.DESIGN_VSP_NAME.name(), vspName);
        parameters.put(OnapRequestParameters.DESIGN_VSP_DESC.name(), vspDescription);
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.SDC_VSP_CREATE, parameters);
        log.info("[Design][Vsp][Set] vendorId:" + vendorId + " , vendorName:" + vendorName + ", vspName:" + vspName + ", id:" + adaptor.getResponseItem(data, "itemId"));
        return ResponseEntity.ok(data.toString());
    }

    @PutMapping(path = "/design/vsp-file/{vspId}/{vspVersionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity uploadVSPFile(@PathVariable String vspId, @PathVariable String vspVersionId) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.DESIGN_VSP_ID.name(), vspId);
        parameters.put(OnapRequestParameters.DESIGN_VSP_VERSION_ID.name(), vspVersionId);
        parameters.put(OnapRequestParameters.FILE_PARAM_NAME.name(), "upload");
        parameters.put(OnapRequestParameters.FILE_NAME.name(), "vspFile.zip");
        parameters.put(OnapRequestParameters.FILE_PATH.name(), new File("d:\\ArgelaHelloWorld.zip").getPath());
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.SDC_VSP_UPLOAD_FILE, parameters);
        log.info("[Design][Vsp][FileUpload] vspId:" + vspId + " , vspVersionId:" + vspVersionId);
        return ResponseEntity.ok(data.toString());
    }
}
