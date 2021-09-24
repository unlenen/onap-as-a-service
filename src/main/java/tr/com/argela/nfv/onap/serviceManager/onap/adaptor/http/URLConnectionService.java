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

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.model.OnapRequest;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.exception.OnapRequestFailedException;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@Component
@Slf4j
public class URLConnectionService {

    SSLSocketFactory socketFactory;
    X509TrustManager x509TrustManager;

    public URLConnectionService() {
        x509TrustManager = new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }

            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }
        };

        TrustManager[] trustAllCerts = new TrustManager[]{
            x509TrustManager
        };

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            socketFactory = sc.getSocketFactory();
            HttpsURLConnection.setDefaultSSLSocketFactory(socketFactory);
        } catch (GeneralSecurityException e) {
        }
    }

    public OkHttpClient.Builder getHttpsBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.sslSocketFactory(socketFactory, x509TrustManager);
        builder.hostnameVerifier((String hostname, SSLSession session) -> true);
        return builder;
    }

    public Response get(String url, Map<String, String> headers) throws IOException {
        OkHttpClient client = getHttpsBuilder().build();

        Builder builder = new Request.Builder().url(url);
        for (String header : headers.keySet()) {
            builder.addHeader(header, headers.get(header));
        }

        Request request = builder.build();
        return client.newCall(request).execute();
    }

    public Object call(OnapRequest onapRequest, Map<String, String> parameters) throws IOException {
        String url = onapRequest.getEndpoint(parameters);
        log.info("[ONAP][APICALL] url : " + url);
        Response response = get(url, onapRequest.getOnapModule().getHeaders());
        ResponseBody responseBody = response.body();
        int responseCode = response.code();
        if (responseCode != onapRequest.getValidReturnCode()) {
            throw new OnapRequestFailedException(onapRequest, url, responseCode, responseBody.string());
        }
        String data = responseBody.string();

        switch (onapRequest.getResponseType()) {
            case JSONObject: {
                return new JSONObject(data);
            }
            case JSONArray: {
                return new JSONArray(data);
            }
            case STRING: {
                return data;
            }
        }
        return null;
    }

}
