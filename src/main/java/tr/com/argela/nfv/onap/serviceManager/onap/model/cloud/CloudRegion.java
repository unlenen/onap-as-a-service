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
public class CloudRegion {

    String cloudOwner;
    String cloudRegionId;
    String cloudType;
    String ownerDefinedType;
    String cloudRegionVersion;
    String cloudZone;
    String cloudExtraInfo;
    boolean orchestrationDisabled;
    boolean inMaint;
    String resourceVersion;

    public String getCloudOwner() {
        return cloudOwner;
    }

    public void setCloudOwner(String cloudOwner) {
        this.cloudOwner = cloudOwner;
    }

    public String getCloudRegionId() {
        return cloudRegionId;
    }

    public void setCloudRegionId(String cloudRegionId) {
        this.cloudRegionId = cloudRegionId;
    }

    public String getCloudType() {
        return cloudType;
    }

    public void setCloudType(String cloudType) {
        this.cloudType = cloudType;
    }

    public String getOwnerDefinedType() {
        return ownerDefinedType;
    }

    public void setOwnerDefinedType(String ownerDefinedType) {
        this.ownerDefinedType = ownerDefinedType;
    }

    public String getCloudRegionVersion() {
        return cloudRegionVersion;
    }

    public void setCloudRegionVersion(String cloudRegionVersion) {
        this.cloudRegionVersion = cloudRegionVersion;
    }

    public String getCloudZone() {
        return cloudZone;
    }

    public void setCloudZone(String cloudZone) {
        this.cloudZone = cloudZone;
    }

    public String getCloudExtraInfo() {
        return cloudExtraInfo;
    }

    public void setCloudExtraInfo(String cloudExtraInfo) {
        this.cloudExtraInfo = cloudExtraInfo;
    }

    public boolean isOrchestrationDisabled() {
        return orchestrationDisabled;
    }

    public void setOrchestrationDisabled(boolean orchestrationDisabled) {
        this.orchestrationDisabled = orchestrationDisabled;
    }

    public boolean isInMaint() {
        return inMaint;
    }

    public void setInMaint(boolean inMaint) {
        this.inMaint = inMaint;
    }

    public String getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    public static CloudRegion parse(JSONObject obj) {
        CloudRegion cloudRegion = new CloudRegion();
        cloudRegion.setCloudOwner(obj.getString("cloud-owner"));
        cloudRegion.setCloudRegionId(obj.getString("cloud-region-id"));
        cloudRegion.setCloudType(obj.getString("cloud-type"));
        cloudRegion.setOwnerDefinedType(obj.getString("owner-defined-type"));
        cloudRegion.setCloudRegionVersion(obj.getString("cloud-region-version"));
        cloudRegion.setCloudZone(obj.getString("cloud-zone"));
        cloudRegion.setCloudExtraInfo(obj.getString("cloud-extra-info"));
        cloudRegion.setOrchestrationDisabled(obj.getBoolean("orchestration-disabled"));
        cloudRegion.setInMaint(obj.getBoolean("in-maint"));
        cloudRegion.setResourceVersion(obj.getString("resource-version"));
        return cloudRegion;
    }

    public static List<CloudRegion> parseTotal(JSONObject obj) {
        List<CloudRegion> cloudRegions = new ArrayList();
        JSONArray array = obj.getJSONArray("cloud-region");
        for (int i = 0; i < array.length(); i++) {
            cloudRegions.add(parse(array.getJSONObject(i)));
        }
        return cloudRegions;
    }

    @Override
    public String toString() {
        return "CloudRegion{" + "cloudOwner=" + cloudOwner + ", cloudRegionId=" + cloudRegionId + '}';
    }

}
