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

import tr.com.argela.nfv.onap.serviceManager.onap.rest.model.serverInstance.Server;
import tr.com.argela.nfv.onap.serviceManager.onap.rest.model.serverInstance.ServerType;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
public class OpenstackServer extends Server {

    String novaLink;
    String stackName;

    OpenstackComputeNode node;
    OpenstackFlavor flavor;
    OpenstackImage image;

    public OpenstackServer() {
        super(ServerType.OPENSTACK);
    }

    public String getStackName() {
        return stackName;
    }

    public void setStackName(String stackName) {
        this.stackName = stackName;
    }

    public OpenstackImage getImage() {
        return image;
    }

    public void setImage(OpenstackImage image) {
        this.image = image;
    }

    public OpenstackComputeNode getNode() {
        return node;
    }

    public void setNode(OpenstackComputeNode node) {
        this.node = node;
    }

    public OpenstackFlavor getFlavor() {
        return flavor;
    }

    public void setFlavor(OpenstackFlavor flavor) {
        this.flavor = flavor;
    }

    public String getNovaLink() {
        return novaLink;
    }

    public void setNovaLink(String novaLink) {
        this.novaLink = novaLink;
    }

}
