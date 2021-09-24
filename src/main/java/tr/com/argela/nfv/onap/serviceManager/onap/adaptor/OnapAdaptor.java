/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.com.argela.nfv.onap.serviceManager.onap.adaptor;

import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.model.OnapRequest;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.http.URLConnectionService;
import java.io.IOException;
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

    URLConnectionService urlConnectionService = new URLConnectionService();

    public Object call(OnapRequest request) throws IOException {
        return call(request, new HashMap<>());
    }

    public Object call(OnapRequest request, Map<String, String> parameters) throws IOException {
        parameters.put("ONAPIP", onapConfig.onapIPAddress);
        return urlConnectionService.call(request, parameters);
    }

    public int getResponseSize(JSONObject data, String item) {
        if (data == null || !data.has(item)) {
            return 0;
        }
        return data.getJSONArray(item).length();
    }
}
