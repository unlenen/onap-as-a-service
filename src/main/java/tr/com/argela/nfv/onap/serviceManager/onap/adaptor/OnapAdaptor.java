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
package tr.com.argela.nfv.onap.serviceManager.onap.adaptor;

import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.model.OnapRequest;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.http.URLConnectionService;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@Service
public class OnapAdaptor {

    @Autowired
    OnapConfig onapConfig;

    @Autowired
    URLConnectionService urlConnectionService;

    public Object call(OnapRequest request) throws IOException {
        return call(request, new HashMap<>());
    }

    public Object call(OnapRequest request, Map<String, String> parameters) throws IOException {
        parameters.put("ONAPIP", onapConfig.onapIPAddress);
        return urlConnectionService.call(request, parameters);
    }

    public Object getResponseItem(JSONObject data, String item) {
        if (data == null || !data.has(item)) {
            return null;
        }
        return data.get(item);
    }

    public int getResponseSize(JSONObject data, String item) {
        if (data == null || !data.has(item)) {
            return 0;
        }
        return data.getJSONArray(item).length();
    }

}
