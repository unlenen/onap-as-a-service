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
public class Customer {

    String id;
    String name;
    String versionId = "";
    String type = "";

    @JsonIgnore
    Service service;

    public Customer() {
    }

    public Customer(String id, String name, String versionId, String type) {
        this.id = id;
        this.name = name;
        this.versionId = versionId;
        this.type = type;
    }

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

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name=" + name + ", type=" + type + '}';
    }

    public void copy(Customer customerOnap) {
        this.id = customerOnap.getId();
        this.name = customerOnap.getName();
        this.versionId = customerOnap.getVersionId();
        this.type = customerOnap.getType();
    }

}
