/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.com.argela.nfv.onap.serviceManager.onap.adaptor.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
public enum OnapModule {

    AAI("https://${ONAPIP}:30233/aai/v16", new String[][]{
        {"Authorization", "Basic QUFJOkFBSQ=="},
        {"X-FromAppId", "AAI"},
        {"Accept", "application/json"},
        {"X-TransactionId", "get_aai_subscr"},
        {"Content-Type", "application/json"}
    }),
    SDC_CATALOG("https://${ONAPIP}:30204/sdc/v1/catalog", new String[][]{
        {"Content-Type", "application/json"},
        {"Accept", "application/json"},
        {"X-TransactionId", "ONAP-Test"},
        {"USER_ID", "cs0008"},
        {"X-FromAppId", "ONAP-Test"},
        {"Authorization", "Basic YWFpOktwOGJKNFNYc3pNMFdYbGhhazNlSGxjc2UyZ0F3ODR2YW9HR21KdlV5MlU="},
        {"x-ecomp-instanceid", "ONAP-Test"},}),
    SDC_FeProxy("https://${ONAPIP}:30207/sdc1/feProxy", new String[][]{
        {"Content-Type", "application/json"},
        {"Accept", "application/json"},
        {"X-TransactionId", "ONAP-Test"},
        {"USER_ID", "cs0008"},
        {"X-FromAppId", "ONAP-Test"}});

    private OnapModule(String api, String[][] data) {
        this.apiURL = api;
        this.headers = new HashMap<>();
        for (String[] object : data) {
            headers.put(object[0], object[1]);
        }
    }

    String apiURL;
    Map<String, String> headers;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getApiURL() {
        return apiURL;
    }

}
