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

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
public class Scenario {

    Vendor vendor;
    Service service;
    List<CloudRegion> cloudRegions;
    List<VFModuleProfile> profiles;

    @JsonIgnore
    Map<String, Tenant> tenantMapById = new HashMap();
    @JsonIgnore
    Map<String, VFModuleProfile> profileMapByName = new HashMap();

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<CloudRegion> getCloudRegions() {
        return cloudRegions;
    }

    public void setCloudRegions(List<CloudRegion> cloudRegions) {
        this.cloudRegions = cloudRegions;
    }

    public List<VFModuleProfile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<VFModuleProfile> profiles) {
        this.profiles = profiles;
    }

    public void mapTenants(Scenario scenario) {
        if (scenario.getCloudRegions() != null) {
            for (CloudRegion cloudRegion : scenario.getCloudRegions()) {
                for (Tenant tenant : cloudRegion.getTenants()) {
                    tenant.setCloudRegion(cloudRegion);
                    tenantMapById.put(tenant.getId(), tenant);
                }
            }
        }
    }

    public void mapProfiles(Scenario scenario) {
        if (scenario.getProfileMapByName() != null) {
            for (VFModuleProfile vfmp : scenario.getProfiles()) {
                profileMapByName.put(vfmp.getName(), vfmp);
            }
        }
    }

    public Map<String, Tenant> getTenantMapById() {
        return tenantMapById;
    }

    public Map<String, VFModuleProfile> getProfileMapByName() {
        return profileMapByName;
    }

}
