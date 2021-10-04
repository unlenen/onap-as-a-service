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

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
public class VFModule {

    String id;
    String name;
    String type;
    String reqId;
    String reqUrl;
    String availabilityZone;

    VFModuleProfile profile;

    @JsonIgnore
    VNF vnf;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public String getAvailabilityZone() {
        return availabilityZone;
    }

    public void setAvailabilityZone(String availabilityZone) {
        this.availabilityZone = availabilityZone;
    }

    public VFModuleProfile getProfile() {
        return profile;
    }

    public void setProfile(VFModuleProfile profile) {
        this.profile = profile;
    }

    public VNF getVnf() {
        return vnf;
    }

    public void setVnf(VNF vnf) {
        this.vnf = vnf;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("VFModule{id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", availabilityZone=").append(availabilityZone);
        sb.append('}');
        return sb.toString();
    }

}
