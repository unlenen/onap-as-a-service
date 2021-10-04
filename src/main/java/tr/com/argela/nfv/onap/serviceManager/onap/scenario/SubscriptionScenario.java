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
package tr.com.argela.nfv.onap.serviceManager.onap.scenario;

import com.jayway.jsonpath.Criteria;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tr.com.argela.nfv.onap.serviceManager.onap.rest.BusinessService;
import tr.com.argela.nfv.onap.serviceManager.onap.rest.DesignService;
import tr.com.argela.nfv.onap.serviceManager.onap.rest.model.CloudRegion;
import tr.com.argela.nfv.onap.serviceManager.onap.rest.model.Customer;
import tr.com.argela.nfv.onap.serviceManager.onap.rest.model.Scenario;
import tr.com.argela.nfv.onap.serviceManager.onap.rest.model.Service;
import tr.com.argela.nfv.onap.serviceManager.onap.rest.model.Tenant;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@org.springframework.stereotype.Service
public class SubscriptionScenario extends CommonScenario {

    Logger log = LoggerFactory.getLogger(SubscriptionScenario.class);

    @Autowired
    DesignService designService;

    @Autowired
    BusinessService businessService;

    public void processSubscription(Service service) throws Exception {

        service.getScenario().mapTenants(service.getScenario());

        subscribeService(service);
        saveCustomers(service);
        subscribeConsumers(service);
    }

    private void saveCustomers(Service service) throws Exception {
        Map<String, Customer> customersAtOnap = getCustomers();

        for (Customer customer : service.getCustomers()) {
            customer.setService(service);
            Customer customerOnap = customersAtOnap.get(customer.getId());
            if (customerOnap == null) {
                createCustomer(customer);
            } else {
                customer.copy(customerOnap);
                log.info("[Scenario][Subscription][Customer][Exists] " + customerOnap);
            }
        }
    }

    private Map<String, Customer> getCustomers() throws Exception {
        Map<String, Customer> customerIds = new HashMap<>();
        JSONObject root = new JSONObject(readResponse(businessService.getCostumers()));
        JSONArray array = root.getJSONArray("customer");
        for (int i = 0; i < array.length(); i++) {
            JSONObject customer = array.getJSONObject(i);
            String id = customer.getString("global-customer-id");
            String name = customer.getString("subscriber-name");
            String versionId = customer.getString("resource-version");
            String type = customer.getString("subscriber-type");
            customerIds.put(id, new Customer(id, name, versionId, type));
        }
        return customerIds;
    }

    private void createCustomer(Customer customer) throws Exception {
        readResponseValidateOption(businessService.createCostumer(customer.getId(), customer.getName()), false);
        log.info("[Scenario][Subscription][Customer][New] " + customer);
    }

    private void subscribeService(Service service) throws Exception {

        JSONObject subscription = new JSONObject(readResponseValidateOption(businessService.getServiceSubscriptions(service.getInvariantUUID()), false));
        if (subscription.has("service-id")) {
            log.info("[Scenario][Subscription][Service][Exists] " + service);
        } else {
            readResponse(businessService.createServiceSubscription(service.getUniqueId(), service.getName()));
            log.info("[Scenario][Subscription][Service][Create] " + service);
        }
    }

    private void subscribeConsumers(Service service) throws Exception {
        for (Customer customer : service.getCustomers()) {
            subscribeConsumerToService(customer);
        }
    }

    private void subscribeConsumerToService(Customer customer) throws Exception {
        JSONObject subscription = new JSONObject(readResponseValidateOption(businessService.getCustomerServiceSubscription(customer.getId(), customer.getService().getName()), false));
        if (subscription.has("service-type")) {
            log.info("[Scenario][Subscription][Service][Exists] " + customer + " to " + customer.getService());
        } else {
            businessService.createCustomerServiceSubscription(customer.getId(), customer.getService().getName());
            log.info("[Scenario][Subscription][Service][Create] " + customer + " to " + customer.getService());
        }

        subscribeTenants(customer, subscription);
    }

    private void subscribeTenants(Customer customer, JSONObject subscription) throws Exception {
        DocumentContext rootContext = JsonPath.parse(subscription.toString());

        for (Tenant tenant : customer.getService().getTenants()) {
            boolean subscriptionFound = false;
            if (subscription.has("relationship-list")) {

                Filter tenantId = Filter.filter(Criteria.where("related-link").contains(tenant.getId()));
                net.minidev.json.JSONArray tenantFound = rootContext.read("$['relationship-list']['relationship'][?]", tenantId);
                if (tenantFound.size() > 0) {
                    log.info("[Scenario][Subscription][Service][Tenant][Exists] " + customer + " to " + customer.getService() + " on " + tenant);
                    subscriptionFound = true;
                }

            }
            if (!subscriptionFound) {
                createTenantSubscription(tenant, customer);
                log.info("[Scenario][Subscription][Service][Tenant][New] " + customer + " to " + customer.getService() + " on " + tenant);
            }
        }

    }

    private void createTenantSubscription(Tenant tenant, Customer customer) throws Exception {
        Tenant tenantFull = customer.getService().getScenario().getTenantMapById().get(tenant.getId());
        String result = readResponse(businessService.createCustomerTenantSubscription(customer.getId(), tenantFull.getCloudRegion().getCloudOwner(), tenantFull.getCloudRegion().getRegionName(), tenantFull.getId(), tenantFull.getName(), customer.getService().getName()));
    }

}
