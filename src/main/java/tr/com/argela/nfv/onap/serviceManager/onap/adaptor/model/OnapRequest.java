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
package tr.com.argela.nfv.onap.serviceManager.onap.adaptor.model;

import java.util.Map;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.http.HttpCallType;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.http.HttpResponseType;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
public enum OnapRequest {

    CLOUD_REGION(HttpCallType.GET, OnapModule.AAI, "/cloud-infrastructure/cloud-regions", 200, HttpResponseType.JSONObject),
    CLOUD_COMPLEX(HttpCallType.GET, OnapModule.AAI, "/cloud-infrastructure/complexes", 200, HttpResponseType.JSONObject),
    CLOUD_TENANT(HttpCallType.GET, OnapModule.AAI, "/cloud-infrastructure/cloud-regions/cloud-region/${" + OnapRequestParameters.CLOUD_OWNER + "}/${" + OnapRequestParameters.CLOUD_REGION + "}/tenants", 200, HttpResponseType.JSONObject),
    CLOUD_AVAILABILITY_ZONE(HttpCallType.GET, OnapModule.AAI, "/cloud-infrastructure/cloud-regions/cloud-region/${" + OnapRequestParameters.CLOUD_OWNER + "}/${" + OnapRequestParameters.CLOUD_REGION + "}/availability-zones", 200, HttpResponseType.JSONObject),
    BUSINESS_CUSTOMER(HttpCallType.GET, OnapModule.AAI, "/business/customers", 200, HttpResponseType.JSONObject),
    BUSINESS_OWNING_ENTITY(HttpCallType.GET, OnapModule.AAI, "/business/owning-entities", 200, HttpResponseType.JSONObject),
    BUSINESS_PLATFORM(HttpCallType.GET, OnapModule.AAI, "/business/platforms", 200, HttpResponseType.JSONObject),
    BUSINESS_PROJECT(HttpCallType.GET, OnapModule.AAI, "/business/projects", 200, HttpResponseType.JSONObject),
    SDC_SERVICE_MODELS(HttpCallType.GET, OnapModule.SDC_CATALOG, "/services", 200, HttpResponseType.JSONArray),
    SDC_VFS(HttpCallType.GET, OnapModule.SDC_CATALOG, "/resources?resourceType=VF", 200, HttpResponseType.JSONArray),
    SDC_VENDORS(HttpCallType.GET, OnapModule.SDC_FeProxy, "/onboarding-api/v1.0/vendor-license-models", 200, HttpResponseType.JSONObject),
    SDC_VSPS(HttpCallType.GET, OnapModule.SDC_FeProxy, "/onboarding-api/v1.0/vendor-software-products", 200, HttpResponseType.JSONObject),
    RUNTIME_SERVICE_INSTANCES(HttpCallType.GET, OnapModule.NBI, "/service?relatedParty.id=${" + OnapRequestParameters.BUSINESS_CUSTOMER_NAME + "}", 200, HttpResponseType.JSONArray),
    RUNTIME_SERVICE_INSTANCE_DETAIL(HttpCallType.GET, OnapModule.NBI, "/service/${" + OnapRequestParameters.RUNTIME_SERVICE_INSTANCE_ID + "}", 200, HttpResponseType.JSONObject),
    RUNTIME_VNFS(HttpCallType.GET, OnapModule.AAI, "/network/generic-vnfs", 200, HttpResponseType.JSONObject),
    RUNTIME_VNF_DETAIL(HttpCallType.GET, OnapModule.AAI, "/network/generic-vnfs/generic-vnf/${" + OnapRequestParameters.RUNTIME_VNF_ID + "}", 200, HttpResponseType.JSONObject),
    RUNTIME_VFMODULES(HttpCallType.GET, OnapModule.AAI, "/network/generic-vnfs/generic-vnf/${" + OnapRequestParameters.RUNTIME_VNF_ID + "}/vf-modules", 200, HttpResponseType.JSONObject),
    RUNTIME_VFMODULE_DETAIL(HttpCallType.GET, OnapModule.AAI, "/network/generic-vnfs/generic-vnf/${" + OnapRequestParameters.RUNTIME_VNF_ID + "}/vf-modules/vf-module/${" + OnapRequestParameters.RUNTIME_VF_MODULE_ID + "}", 200, HttpResponseType.JSONObject),
    RUNTIME_VFMODULE_INSTANTIATE_DETAIL(HttpCallType.GET, OnapModule.SO, "/infra/orchestrationRequests/v7?filter=vfModuleInstanceId:EQUALS:${" + OnapRequestParameters.RUNTIME_VF_MODULE_ID + "}", 200, HttpResponseType.JSONObject),;

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
