/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    OnapAdaptor onapUtil;

    @GetMapping(path = "/design/service-models", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getServiceModels() throws IOException {
        JSONArray data = (JSONArray) onapUtil.call(OnapRequest.SDC_SERVICE_MODELS);
        log.info("[Design][ServiceModels][Get] size:" + data.length());
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/design/vfs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getVFs() throws IOException {
        JSONArray data = (JSONArray) onapUtil.call(OnapRequest.SDC_SERVICE_MODELS);
        log.info("[Design][VFs][Get] size:" + data.length());
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/design/vendors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getVendors() throws IOException {
        JSONObject data = (JSONObject) onapUtil.call(OnapRequest.SDC_VENDORS);
        log.info("[Design][Vendors][Get] size:" + onapUtil.getResponseSize(data, "results"));
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/design/vsps", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getVSPs() throws IOException {
        JSONObject data = (JSONObject) onapUtil.call(OnapRequest.SDC_VSPS);
        log.info("[Design][VSPs][Get] size:" + onapUtil.getResponseSize(data, "results"));
        return ResponseEntity.ok(data.toString());
    }
}
