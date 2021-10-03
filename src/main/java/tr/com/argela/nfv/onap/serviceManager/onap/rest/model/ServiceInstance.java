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
public class ServiceInstance {

    String id;
    String name;
    String project;
    String reqId;
    String reqUrl;

    Customer customer;
    OwningEntity owningEntity;

    @JsonIgnore
    Service service;

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

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
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

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public OwningEntity getOwningEntity() {
        return owningEntity;
    }

    public void setOwningEntity(OwningEntity owningEntity) {
        this.owningEntity = owningEntity;
    }

    @Override
    public String toString() {
        return "ServiceInstance{" + "id=" + id + ", name=" + name + '}';
    }

}
