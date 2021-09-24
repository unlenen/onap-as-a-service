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

import java.io.IOException;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.model.OnapRequest;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
public class OnapRequestFailedException extends IOException {

    OnapRequest onapRequest;

    public OnapRequestFailedException(OnapRequest onapRequest) {
        this.onapRequest = onapRequest;
    }

    public OnapRequestFailedException(OnapRequest onapRequest, String url, int responseCode, String string) {
        super("request:" + onapRequest + ",url:" + url + ",responseCode:" + responseCode + " , msg:" + string);
        this.onapRequest = onapRequest;
    }

    public OnapRequestFailedException(OnapRequest onapRequest, String string, Throwable thrwbl) {
        super(string, thrwbl);
        this.onapRequest = onapRequest;
    }

    public OnapRequest getOnapRequest() {
        return onapRequest;
    }

}
