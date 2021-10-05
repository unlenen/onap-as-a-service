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
import org.springframework.web.bind.annotation.DeleteMapping;
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
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.BUSINESS_CUSTOMERS);
        log.info("[Business][Customers][Get] size:" + adaptor.getResponseSize(data, "customer"));
        return ResponseEntity.ok(data.toString());
    }

    @GetMapping(path = "/business/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCostumer(@PathVariable String customerId) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.BUSINESS_CUSTOMER_ID.name(), customerId);
        JSONObject data = (JSONObject) adaptor.call(OnapRequest.BUSINESS_CUSTOMER, parameters);
        log.info("[Business][Customer][Get] customerId:" + customerId + ", data: " + data);
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

    @DeleteMapping(path = "/business/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteCostumer(@PathVariable String customerId) throws IOException {

        ResponseEntity responseEntity = getCostumer(customerId);
        JSONObject obj = new JSONObject(responseEntity.getBody() + "");
        if (!obj.has("resource-version")) {
            return ResponseEntity.ok("{\"error\":{\"msg\":\"Customer Not Found\"}}");
        }
        String resourceVersion = obj.getString("resource-version");
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.BUSINESS_CUSTOMER_ID.name(), customerId);
        parameters.put(OnapRequestParameters.RESOURCE_VERSION.name(), resourceVersion);
        String data = (String) adaptor.call(OnapRequest.BUSINESS_CUSTOMER_DELETE, parameters);
        log.info("[Business][Customer][Delete] customerId:" + customerId);
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

    @GetMapping(path = "/business/customer-subscribe/{customerId}/{serviceName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCustomerServiceSubscription(@PathVariable String customerId, @PathVariable String serviceName) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.BUSINESS_CUSTOMER_ID.name(), customerId);
        parameters.put(OnapRequestParameters.DESIGN_SERVICE_MODEL_NAME.name(), serviceName);

        JSONObject data = (JSONObject) adaptor.call(OnapRequest.BUSINESS_CUSTOMER_SERVICE_SUBSCRIPTION, parameters);
        log.info("[Business][Customer][Subscription][Service][Get] customerId:" + customerId + ",serviceName:" + serviceName + " , data:" + data);
        return ResponseEntity.ok(data.toString());
    }

    @PutMapping(path = "/business/customer-subscribe/{customerId}/{serviceName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createCustomerServiceSubscription(@PathVariable String customerId, @PathVariable String serviceName) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.BUSINESS_CUSTOMER_ID.name(), customerId);

        parameters.put(OnapRequestParameters.DESIGN_SERVICE_MODEL_NAME.name(), serviceName);

        String data = (String) adaptor.call(OnapRequest.BUSINESS_CUSTOMER_SUBSCRIPTION_CREATE, parameters);
        log.info("[Business][Customer][Subscription][Service][Create] customerId:" + customerId + ",serviceName:" + serviceName + ", data:" + data);
        return ResponseEntity.ok("{}");
    }

    @PutMapping(path = "/business/customer-subscribe-tenant/{customerId}/{cloudOwner}/{regionId}/{tenantId}/{tenantName}/{serviceName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createCustomerTenantSubscription(@PathVariable String customerId, @PathVariable String cloudOwner, @PathVariable String regionId, @PathVariable String tenantId, @PathVariable String tenantName, @PathVariable String serviceUniqueId) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.BUSINESS_CUSTOMER_ID.name(), customerId);
        parameters.put(OnapRequestParameters.CLOUD_OWNER.name(), cloudOwner);
        parameters.put(OnapRequestParameters.CLOUD_REGION.name(), regionId);
        parameters.put(OnapRequestParameters.CLOUD_TENANT_ID.name(), tenantId);
        parameters.put(OnapRequestParameters.CLOUD_TENANT_NAME.name(), tenantName);
        parameters.put(OnapRequestParameters.DESIGN_SERVICE_MODEL_NAME.name(), serviceUniqueId);

        String data = (String) adaptor.call(OnapRequest.BUSINESS_CUSTOMER_TENANT_SUBSCRIPTION_CREATE, parameters);
        log.info("[Business][Customer][Subscription][Tenant][Create] customerId:" + customerId + ",cloudOwner:" + cloudOwner + " ,regionId:" + regionId + ",tenantId:" + tenantId + ", serviceUniqueId:" + serviceUniqueId + ", data:" + data);
        return ResponseEntity.ok("{}");
    }

    @GetMapping(path = "/business/service-subscribe/{serviceInvariantUUID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getServiceSubscriptions(@PathVariable String serviceInvariantUUID) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.DESIGN_SERVICE_MODEL_InvariantUUID.name(), serviceInvariantUUID);

        JSONObject data = (JSONObject) adaptor.call(OnapRequest.AAI_SERVICE_MODEL_SUBSCRIPTIONS, parameters);
        log.info("[Business][Service][Subscriptions] serviceInvariantUUID: " + serviceInvariantUUID + " , data: " + data);
        return ResponseEntity.ok(data.toString());
    }

    @PutMapping(path = "/business/service-subscribe/{serviceInvariantUUID}/{serviceName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createServiceSubscription(@PathVariable String serviceInvariantUUID, @PathVariable String serviceName) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OnapRequestParameters.DESIGN_SERVICE_MODEL_InvariantUUID.name(), serviceInvariantUUID);
        parameters.put(OnapRequestParameters.DESIGN_SERVICE_MODEL_NAME.name(), serviceName);

        String data = (String) adaptor.call(OnapRequest.AAI_SERVICE_MODEL_SUBSCRIPTION_CREATE, parameters);
        log.info("[Business][Service][Subscriptions] serviceInvariantUUID: " + serviceInvariantUUID + " ,serviceName:" + serviceName + ", data: " + data);
        return ResponseEntity.ok("{]");
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
