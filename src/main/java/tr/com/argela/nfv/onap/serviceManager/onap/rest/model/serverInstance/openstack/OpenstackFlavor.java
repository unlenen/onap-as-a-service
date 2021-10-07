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
package tr.com.argela.nfv.onap.serviceManager.onap.rest.model.serverInstance.openstack;

import tr.com.argela.nfv.onap.serviceManager.onap.rest.model.serverInstance.ServerEntity;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
public class OpenstackFlavor extends ServerEntity {

    int vcpu;
    int ram;
    int disk;
    String swap;
    int ephemeral;

    public int getVcpu() {
        return vcpu;
    }

    public void setVcpu(int vcpu) {
        this.vcpu = vcpu;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getDisk() {
        return disk;
    }

    public void setDisk(int disk) {
        this.disk = disk;
    }

    public String getSwap() {
        return swap;
    }

    public void setSwap(String swap) {
        this.swap = swap;
    }

    public int getEphemeral() {
        return ephemeral;
    }

    public void setEphemeral(int ephemeral) {
        this.ephemeral = ephemeral;
    }

}
