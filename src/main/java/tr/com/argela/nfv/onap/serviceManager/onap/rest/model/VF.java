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
package tr.com.argela.nfv.onap.serviceManager.onap.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tr.com.argela.nfv.onap.serviceManager.onap.rest.model.constant.EntityStatus;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
public class VF {

    String uuid;
    String uniqueId;
    String invariantUUID;
    String name, description, versionName, customizationUUID, modelName, modelType, customizationName, modelUUID, modelInvariantUUID, modelCustomizationUUID;
    VSP vsp;
    @JsonIgnore
    Service service;
    EntityStatus versionStatus;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getInvariantUUID() {
        return invariantUUID;
    }

    public void setInvariantUUID(String invariantUUID) {
        this.invariantUUID = invariantUUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getCustomizationUUID() {
        return customizationUUID;
    }

    public void setCustomizationUUID(String customizationUUID) {
        this.customizationUUID = customizationUUID;
    }

    public String getCustomizationName() {
        return customizationName;
    }

    public void setCustomizationName(String customizationName) {
        this.customizationName = customizationName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelUUID() {
        return modelUUID;
    }

    public void setModelUUID(String modelUUID) {
        this.modelUUID = modelUUID;
    }

    public String getModelCustomizationUUID() {
        return modelCustomizationUUID;
    }

    public void setModelCustomizationUUID(String modelCustomizationUUID) {
        this.modelCustomizationUUID = modelCustomizationUUID;
    }

    public String getModelInvariantUUID() {
        return modelInvariantUUID;
    }

    public void setModelInvariantUUID(String modelInvariantUUID) {
        this.modelInvariantUUID = modelInvariantUUID;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public VSP getVsp() {
        return vsp;
    }

    public void setVsp(VSP vsp) {
        this.vsp = vsp;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public EntityStatus getVersionStatus() {
        return versionStatus;
    }

    public void setVersionStatus(EntityStatus versionStatus) {
        this.versionStatus = versionStatus;
    }

    @Override
    public String toString() {
        return "VF{" + "uuid=" + uuid + ", uniqueId=" + uniqueId + ", invariantUUID=" + invariantUUID + ", name=" + name + ", versionName=" + versionName + ", customizationUUID=" + customizationUUID + ", modelName=" + modelName + ", modelType=" + modelType + ", versionStatus=" + versionStatus + '}';
    }

    
}
