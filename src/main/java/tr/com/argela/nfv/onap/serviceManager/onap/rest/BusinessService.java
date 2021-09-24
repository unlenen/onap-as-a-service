/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.com.argela.nfv.onap.serviceManager.onap.rest;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
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
public class BusinessService {

    @Autowired
    OnapAdaptor onapUtil;

    @GetMapping(path = "/business/customers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getBusinessCostumers() throws IOException {
        JSONObject data = (JSONObject) onapUtil.call(OnapRequest.BUSINESS_CUSTOMER);
        log.info("[Business][Customer][Get] size:" + onapUtil.getResponseSize(data, "customer"));
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/business/owning-entities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getBusinessOwningEntities() throws IOException {
        JSONObject data = (JSONObject) onapUtil.call(OnapRequest.BUSINESS_OWNING_ENTITY);
        log.info("[Business][OwningEntity][Get] size:" + onapUtil.getResponseSize(data, "owning-entity"));
        return ResponseEntity.ok(data.toString());
    }

}
