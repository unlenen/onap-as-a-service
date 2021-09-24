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
package tr.com.argela.nfv.onap.serviceManager.onap.adaptor.http;

import org.json.JSONObject;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
public class URLResponse {

    int responseCode;
    String response;
    JSONObject responseJSON;

    public URLResponse() {
    }

    public URLResponse(int responseCode, String response, JSONObject responseJSON) {
        this.responseCode = responseCode;
        this.response = response;
        this.responseJSON = responseJSON;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public JSONObject getResponseJSON() {
        return responseJSON;
    }

    public void setResponseJSON(JSONObject responseJSON) {
        this.responseJSON = responseJSON;
    }

}
