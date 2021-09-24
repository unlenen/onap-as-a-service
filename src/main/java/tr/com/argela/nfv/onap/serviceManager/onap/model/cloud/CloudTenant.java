/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.com.argela.nfv.ran_network_tool.model.cloud;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
public class CloudTenant {

    String tenantId;
    String tenantName;
    String resourceVersion;
    CloudRegion cloudRegion;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    public CloudRegion getCloudRegion() {
        return cloudRegion;
    }

    public void setCloudRegion(CloudRegion cloudRegion) {
        this.cloudRegion = cloudRegion;
    }

    public static CloudTenant parse(JSONObject obj) {
        CloudTenant cloudRegion = new CloudTenant();
        cloudRegion.setTenantId(obj.getString("tenant-id"));
        cloudRegion.setTenantName(obj.getString("tenant-name"));
        cloudRegion.setResourceVersion(obj.getString("resource-version"));

        return cloudRegion;
    }

    public static List<CloudTenant> parseTotal(JSONObject obj) {
        List<CloudTenant> data = new ArrayList();
        JSONArray array = obj.getJSONArray("tenant");
        for (int i = 0; i < array.length(); i++) {
            data.add(parse(array.getJSONObject(i)));
        }
        return data;
    }

    @Override
    public String toString() {
        return "CloudTenant{" + "tenantId=" + tenantId + ", tenantName=" + tenantName + ", cloudRegion=" + cloudRegion + '}';
    }

}
