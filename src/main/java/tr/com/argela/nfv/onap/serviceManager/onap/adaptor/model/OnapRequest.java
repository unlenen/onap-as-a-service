/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.com.argela.nfv.onap.serviceManager.onap.adaptor.model;

import java.util.Map;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.http.HttpCallType;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.http.HttpResponseType;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
public enum OnapRequest {

    CLOUD_REGION(HttpCallType.GET, OnapModule.AAI, "/cloud-infrastructure/cloud-regions", 200, HttpResponseType.JSON),
    CLOUD_COMPLEX(HttpCallType.GET, OnapModule.AAI, "/cloud-infrastructure/complexes", 200, HttpResponseType.JSON),
    CLOUD_TENANT(HttpCallType.GET, OnapModule.AAI, "/cloud-infrastructure/cloud-regions/cloud-region/${" + OnapRequestParameters.CLOUD_OWNER + "}/${" + OnapRequestParameters.CLOUD_REGION + "}/tenants", 200, HttpResponseType.JSON),
    CLOUD_AVAILABILITY_ZONE(HttpCallType.GET, OnapModule.AAI, "/cloud-infrastructure/cloud-regions/cloud-region/${" + OnapRequestParameters.CLOUD_OWNER + "}/${" + OnapRequestParameters.CLOUD_REGION + "}/availability-zones", 200, HttpResponseType.JSON),
    BUSINESS_CUSTOMER(HttpCallType.GET, OnapModule.AAI, "/business/customers", 200, HttpResponseType.JSON),
    BUSINESS_OWNING_ENTITY(HttpCallType.GET, OnapModule.AAI, "/business/owning-entities", 200, HttpResponseType.JSON),
    ;

    private OnapRequest(HttpCallType callType, OnapModule onapModule, String url, int validReturnCode, HttpResponseType responseType) {
        this.callType = callType;
        this.onapModule = onapModule;
        this.url = url;
        this.validReturnCode = validReturnCode;
        this.responseType = responseType;
    }

    HttpCallType callType;
    OnapModule onapModule;
    String url;
    int validReturnCode;
    HttpResponseType responseType;

    public HttpCallType getCallType() {
        return callType;
    }

    public int getValidReturnCode() {
        return validReturnCode;
    }

    public HttpResponseType getResponseType() {
        return responseType;
    }

    public OnapModule getOnapModule() {
        return onapModule;
    }

    public String getUrl() {
        return url;
    }

    public String getEndpoint(Map<String, String> parameters) {
        String mainURL = getOnapModule().getApiURL() + getUrl();
        for (String key : parameters.keySet()) {
            mainURL = mainURL.replaceAll("\\$\\{" + key + "\\}", parameters.get(key));
        }
        return mainURL;
    }
}
