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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tr.com.argela.nfv.onap.serviceManager.onap.rest.model.constant.DistributionStatus;
import tr.com.argela.nfv.onap.serviceManager.onap.rest.model.constant.EntityStatus;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
public class Service {

    String uuid;
    String uniqueId;
    String invariantUUID;
    String name, description, versionName;
    List<VF> vfs;
    EntityStatus versionStatus;
    DistributionStatus distributionStatus;

    @JsonIgnore
    Map<String, VF> vfMapByModelName = new HashMap<>();

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

    public List<VF> getVfs() {
        return vfs;
    }

    public void setVfs(List<VF> vfs) {
        this.vfs = vfs;
    }

    public EntityStatus getVersionStatus() {
        return versionStatus;
    }

    public void setVersionStatus(EntityStatus versionStatus) {
        this.versionStatus = versionStatus;
    }

    public DistributionStatus getDistributionStatus() {
        return distributionStatus;
    }

    public void setDistributionStatus(DistributionStatus distributionStatus) {
        this.distributionStatus = distributionStatus;
    }

    public void mapVfs() {
        vfs.forEach((vf) -> {
            vfMapByModelName.put(vf.getModelName(), vf);
        });
    }

    public VF getVFByModelName(String modelName) {
        return vfMapByModelName.get(modelName);
    }
}
