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

import java.util.List;
import tr.com.argela.nfv.onap.serviceManager.onap.rest.model.constant.CloudType;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
public class CloudRegion {

    String name;
    String cloudOwner;
    String complexName;
    String regionName;

    CloudType cloudType;
    String domain, defaultProject, authServiceURL, authUser, authPassword, configParameters;
    List<Tenant> tenants;
    List<AvailabilityZone> availabilityZones;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCloudOwner() {
        return cloudOwner;
    }

    public void setCloudOwner(String cloudOwner) {
        this.cloudOwner = cloudOwner;
    }

    public String getComplexName() {
        return complexName;
    }

    public void setComplexName(String complexName) {
        this.complexName = complexName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public CloudType getCloudType() {
        return cloudType;
    }

    public void setCloudType(CloudType cloudType) {
        this.cloudType = cloudType;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDefaultProject() {
        return defaultProject;
    }

    public void setDefaultProject(String defaultProject) {
        this.defaultProject = defaultProject;
    }

    public String getAuthServiceURL() {
        return authServiceURL;
    }

    public void setAuthServiceURL(String authServiceURL) {
        this.authServiceURL = authServiceURL;
    }

    public String getAuthUser() {
        return authUser;
    }

    public void setAuthUser(String authUser) {
        this.authUser = authUser;
    }

    public String getAuthPassword() {
        return authPassword;
    }

    public void setAuthPassword(String authPassword) {
        this.authPassword = authPassword;
    }

    public String getConfigParameters() {
        return configParameters;
    }

    public void setConfigParameters(String configParameters) {
        this.configParameters = configParameters;
    }

    public List<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants) {
        this.tenants = tenants;
    }

    public List<AvailabilityZone> getAvailabilityZones() {
        return availabilityZones;
    }

    public void setAvailabilityZones(List<AvailabilityZone> availabilityZones) {
        this.availabilityZones = availabilityZones;
    }

    @Override
    public String toString() {
        return "CloudRegion{" + "cloudOwner=" + cloudOwner + ", name=" + name + ", cloudType=" + cloudType + '}';
    }

}
