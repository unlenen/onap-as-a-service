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
    });

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
