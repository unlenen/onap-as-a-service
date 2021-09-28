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
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class DesignService {

    @Autowired
    OnapAdaptor adaptor;

    Logger log = LoggerFactory.getLogger(DesignService.class);

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
    public ResponseEntity createVendor(@PathVariable String vendorName, @PathVariable(required = false) String vendorDescription) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.DESIGN_VENDOR_NAME.name(), vendorName);
        parameters.put(OnapRequestParameters.DESIGN_VENDOR_DESC.name(), vendorDescription);
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.SDC_VENDOR_CREATE, parameters);
        log.info("[Design][Vendor][Create] " + parameters + ", response --> id:" + adaptor.getResponseItem(data, "itemId"));
        return ResponseEntity.ok(data.toString());
    }

    @PutMapping(path = "/design/vendor-submit/{vendorId}/{vendorVersionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity submitVendor(@PathVariable String vendorId, @PathVariable String vendorVersionId) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.DESIGN_VENDOR_ID.name(), vendorId);
        parameters.put(OnapRequestParameters.DESIGN_VENDOR_VERSION_ID.name(), vendorVersionId);
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.SDC_VENDOR_SUBMIT, parameters);
        log.info("[Design][Vendor][Submit] " + parameters);
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/design/vsps", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getVSPs() throws IOException {
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.SDC_VSPS);
        log.info("[Design][VSPs][Get] response --> size:" + adaptor.getResponseSize(data, "results"));
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/design/vsp-versions/{vspId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getVSPVersion(@PathVariable String vspId) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.DESIGN_VSP_ID.name(), vspId);
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.SDC_VSP_VERSION, parameters);
        log.info("[Design][VSP-Version][Get]" + parameters + ", response --> size:" + adaptor.getResponseSize(data, "results"));
        return ResponseEntity.ok(data.toString());
    }

    @PutMapping(path = "/design/vsp/{vendorId}/{vendorName}/{vspName}/{vspDescription}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createVsp(@PathVariable String vendorId, @PathVariable String vendorName, @PathVariable String vspName, @PathVariable String vspDescription) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.DESIGN_VENDOR_ID.name(), vendorId);
        parameters.put(OnapRequestParameters.DESIGN_VENDOR_NAME.name(), vendorName);
        parameters.put(OnapRequestParameters.DESIGN_VSP_NAME.name(), vspName);
        parameters.put(OnapRequestParameters.DESIGN_VSP_DESC.name(), vspDescription);
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.SDC_VSP_CREATE, parameters);
        log.info("[Design][Vsp][Create] " + parameters + " , response --> id:" + adaptor.getResponseItem(data, "itemId"));
        return ResponseEntity.ok(data.toString());
    }

    @PutMapping(path = "/design/vsp-file-upload/{vspId}/{vspVersionId}/{vspFileLocalPath}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity uploadVSPFile(@PathVariable String vspId, @PathVariable String vspVersionId, @PathVariable String vspFileLocalPath) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.DESIGN_VSP_ID.name(), vspId);
        parameters.put(OnapRequestParameters.DESIGN_VSP_VERSION_ID.name(), vspVersionId);
        parameters.put(OnapRequestParameters.FILE_PARAM_NAME.name(), "upload");
        parameters.put(OnapRequestParameters.FILE_PATH.name(), vspFileLocalPath);
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.SDC_VSP_UPLOAD_FILE, parameters);
        log.info("[Design][Vsp][FileUpload] " + parameters + " ,response:" + data);
        return ResponseEntity.ok(data.toString());
    }

    @PutMapping(path = "/design/vsp-file-process/{vspId}/{vspVersionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity processVSPFile(@PathVariable String vspId, @PathVariable String vspVersionId) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.DESIGN_VSP_ID.name(), vspId);
        parameters.put(OnapRequestParameters.DESIGN_VSP_VERSION_ID.name(), vspVersionId);

        JSONObject data = (JSONObject) adaptor.call(OnapRequest.SDC_VSP_PROCESS_FILE, parameters);
        log.info("[Design][Vsp][FileProcess] " + parameters + " ,response:" + data);
        return ResponseEntity.ok(data.toString());
    }

    @PutMapping(path = "/design/vsp-submit/{vspId}/{vspVersionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity submitVSP(@PathVariable String vspId, @PathVariable String vspVersionId) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.DESIGN_VSP_ID.name(), vspId);
        parameters.put(OnapRequestParameters.DESIGN_VSP_VERSION_ID.name(), vspVersionId);

        JSONObject data = (JSONObject) adaptor.call(OnapRequest.SDC_VSP_SUBMIT, parameters);
        log.info("[Design][Vsp][Submit] " + parameters + ",response:" + data);
        return ResponseEntity.ok(data.toString());
    }

    @PutMapping(path = "/design/vsp-csar/{vspId}/{vspVersionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity csarVSP(@PathVariable String vspId, @PathVariable String vspVersionId) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.DESIGN_VSP_ID.name(), vspId);
        parameters.put(OnapRequestParameters.DESIGN_VSP_VERSION_ID.name(), vspVersionId);
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.SDC_VSP_CSAR, parameters);
        log.info("[Design][Vsp][CSAR] " + parameters + " ,response:" + data);
        return ResponseEntity.ok(data.toString());
    }

    @PutMapping(path = "/design/vf/{vendorName}/{vspId}/{vspVersionName}/{vfName}/{vfDescription}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createVF(@PathVariable String vendorName, String vspId, String vspVersionName, String vfName, String vfDescription) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.DESIGN_VENDOR_NAME.name(), vendorName);
        parameters.put(OnapRequestParameters.DESIGN_VSP_ID.name(), vspId);
        parameters.put(OnapRequestParameters.DESIGN_VSP_VERSION_NAME.name(), vspVersionName);
        parameters.put(OnapRequestParameters.DESIGN_VF_NAME.name(), vfName);
        parameters.put(OnapRequestParameters.DESIGN_VF_DESCRIPTION.name(), vfDescription);
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.SDC_VF_CREATE, parameters);
        log.info("[Design][VF][Create] " + parameters + " ,response:" + data);
        return ResponseEntity.ok(data.toString());
    }
}
