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

public class BusinessService {

    @Autowired
    OnapAdaptor adaptor;

    Logger log = LoggerFactory.getLogger(BusinessService.class);

    @GetMapping(path = "/business/customers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCostumers() throws IOException {
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.BUSINESS_CUSTOMER);
        log.info("[Business][Customer][Get] size:" + adaptor.getResponseSize(data, "customer"));
        return ResponseEntity.ok(data.toString());
    }

    @PutMapping(path = "/business/customer/{customerId}/{customerName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createCostumer(@PathVariable String customerId, @PathVariable String customerName) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.BUSINESS_CUSTOMER_ID.name(), customerId);
        parameters.put(OnapRequestParameters.BUSINESS_CUSTOMER_NAME.name(), customerName);
        String data = (String) adaptor.call(OnapRequest.BUSINESS_CUSTOMER_CREATE, parameters);
        log.info("[Business][Customer][Create] customerId:" + customerId + " , customerName:" + customerName);
        return ResponseEntity.ok(data);
    }

    @GetMapping(path = "/business/customer-subscribe/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCustomerSubscriptions(@PathVariable String customerId) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.BUSINESS_CUSTOMER_ID.name(), customerId);

        JSONObject data = (JSONObject) adaptor.call(OnapRequest.BUSINESS_CUSTOMER_SUBSCRIPTIONS, parameters);
        log.info("[Business][Customer][Subscriptions] customerId:" + customerId + " , response:  size : " + adaptor.getResponseSize(data, "service-subscription"));
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/business/customer-subscribe/{customerId}/{serviceUniqueId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCustomerServiceSubscription(@PathVariable String customerId, @PathVariable String serviceUniqueId) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.BUSINESS_CUSTOMER_ID.name(), customerId);
        parameters.put(OnapRequestParameters.DESIGN_SERVICE_MODEL_UNIQUE_ID.name(), serviceUniqueId);

        JSONObject data = (JSONObject) adaptor.call(OnapRequest.BUSINESS_CUSTOMER_SERVICE_SUBSCRIPTION, parameters);
        log.info("[Business][Customer][Subscription][Service][Get] customerId:" + customerId + ",serviceUniqueId:" + serviceUniqueId + " , data:" + data);
        return ResponseEntity.ok(data.toString());
    }

    @PutMapping(path = "/business/customer-subscribe/{customerId}/{serviceUniqueId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createCustomerServiceSubscription(@PathVariable String customerId, @PathVariable String serviceUniqueId) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.BUSINESS_CUSTOMER_ID.name(), customerId);

        parameters.put(OnapRequestParameters.DESIGN_SERVICE_MODEL_UNIQUE_ID.name(), serviceUniqueId);

        JSONObject data = (JSONObject) adaptor.call(OnapRequest.BUSINESS_CUSTOMER_SUBSCRIPTION_CREATE, parameters);
        log.info("[Business][Customer][Subscription][Service][Create] customerId:" + customerId + ",serviceUniqueId:" + serviceUniqueId + ", data:" + data);
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/business/service-subscribe/{serviceUniqueId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getServiceSubscriptions(@PathVariable String serviceUniqueId) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.DESIGN_SERVICE_MODEL_UNIQUE_ID.name(), serviceUniqueId);

        JSONObject data = (JSONObject) adaptor.call(OnapRequest.AAI_SERVICE_MODEL_SUBSCRIPTIONS, parameters);
        log.info("[Business][Service][Subscriptions] serviceUniqueId: " + serviceUniqueId + " , data: " + data);
        return ResponseEntity.ok(data.toString());
    }

    @PutMapping(path = "/business/service-subscribe/{serviceUniqueId}/{serviceName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createServiceSubscription(@PathVariable String serviceUniqueId, @PathVariable String serviceName) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.DESIGN_SERVICE_MODEL_UNIQUE_ID.name(), serviceUniqueId);
        parameters.put(OnapRequestParameters.DESIGN_SERVICE_MODEL_NAME.name(), serviceName);

        JSONObject data = (JSONObject) adaptor.call(OnapRequest.AAI_SERVICE_MODEL_SUBSCRIPTION_CREATE, parameters);
        log.info("[Business][Service][Subscriptions] serviceUniqueId: " + serviceUniqueId + " ,serviceName:" + serviceName + ", data: " + data);
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/business/owning-entities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getOwningEntities() throws IOException {
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.BUSINESS_OWNING_ENTITY);
        log.info("[Business][OwningEntity][Get] size:" + adaptor.getResponseSize(data, "owning-entity"));
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/business/platforms", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPlatforms() throws IOException {
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.BUSINESS_PLATFORM);
        log.info("[Business][Platforms][Get] size:" + adaptor.getResponseSize(data, "platform"));
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/business/projects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getProjects() throws IOException {
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.BUSINESS_PROJECT);
        log.info("[Business][Projects][Get] size:" + adaptor.getResponseSize(data, "project"));
        return ResponseEntity.ok(data.toString());
    }

}
