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

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
public class VFModuleProfile {

    String name;
    List<VFModuleParameter> parameters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<VFModuleParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<VFModuleParameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "VFModuleProfile{" + "name=" + name + '}';
    }

}
