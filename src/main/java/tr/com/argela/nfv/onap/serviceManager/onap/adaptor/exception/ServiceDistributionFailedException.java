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
package tr.com.argela.nfv.onap.serviceManager.onap.adaptor.exception;

import tr.com.argela.nfv.onap.serviceManager.onap.rest.model.Service;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
public class ServiceDistributionFailedException extends Exception {

    Service service;
    String distributionId;
    String component;
    String status;

    public ServiceDistributionFailedException(Service service, String distributionId, String component, String status) {
        this.service = service;
        this.distributionId = distributionId;
        this.component = component;
        this.status = status;
    }

    public Service getService() {
        return service;
    }

    public String getDistributionId() {
        return distributionId;
    }

    public String getComponent() {
        return component;
    }

    public String getStatus() {
        return status;
    }

}
