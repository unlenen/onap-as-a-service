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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.OnapAdaptor;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.model.OnapRequest;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.exception.OnapRequestFailedException;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
@Component

public class URLConnectionService {

    Logger log = LoggerFactory.getLogger(URLConnectionService.class);

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

        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    }

    public ResponseEntity get(String url, Map<String, String> headerMap) {
        HttpHeaders headers = new HttpHeaders();

        for (String header : headerMap.keySet()) {
            headers.add(header, headerMap.get(header));
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        return response;
    }

    public ResponseEntity delete(String url, Map<String, String> headerMap) {
        HttpHeaders headers = new HttpHeaders();

        for (String header : headerMap.keySet()) {
            headers.add(header, headerMap.get(header));
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
        return response;
    }

    public ResponseEntity push(String methodType, String url, Map<String, String> headerMap, String data, String type) {

        HttpHeaders headers = new HttpHeaders();

        for (String header : headerMap.keySet()) {
            headers.add(header, headerMap.get(header));
        }
        headers.setContentType(org.springframework.http.MediaType.parseMediaType(type));
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<>(data, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.valueOf(methodType), requestEntity, String.class);
        return response;
    }

    public ResponseEntity postFile(OnapRequest onapRequest, String url, Map<String, Object> fileMapByParamName) {
        HttpHeaders headers = new HttpHeaders();

        for (String header : onapRequest.getOnapModule().getHeaders().keySet()) {
            headers.add(header, onapRequest.getOnapModule().getHeaders().get(header));
        }
        headers.setContentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        for (String paramName : fileMapByParamName.keySet()) {
            Object val = fileMapByParamName.get(paramName);
            if (val instanceof File) {
                body.add(paramName, new FileSystemResource((File) (val)));
            } else if (val instanceof String) {
                body.add(paramName, (String) val);
            }
        }

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        return response;
    }

    public Object call(OnapRequest onapRequest, String url, Map<String, String> parameters, Map<String, Object> files) throws IOException {

        String data = "";
        switch (onapRequest.getCallType()) {
            case PUT:
            case POST: {
                if (onapRequest.getPayloadFilePath() != null) {
                    data = enrichPayloadData(readResourceFileToString(onapRequest.getPayloadFilePath()), parameters);
                }
            }
        }

        log.info("[ONAP][APICALL][" + onapRequest.getCallType() + "] url : " + url + " , data: " + data.replaceAll("\n", " "));
        ResponseEntity<String> response = null;
        switch (onapRequest.getCallType()) {
            default:
            case GET: {
                response = get(url, onapRequest.getOnapModule().getHeaders());
                break;
            }
            case DELETE: {
                response = delete(url, onapRequest.getOnapModule().getHeaders());
                break;
            }
            case PUT:
            case POST: {
                response = push(onapRequest.getCallType().name(), url, onapRequest.getOnapModule().getHeaders(), data, onapRequest.getPayloadFileType());
                break;
            }
            case POST_FILE: {
                response = postFile(onapRequest, url, files);
            }
        }

        int responseCode = response.getStatusCodeValue();
        if (responseCode != onapRequest.getValidReturnCode()) {
            throw new OnapRequestFailedException(onapRequest, url, responseCode, response.getBody());
        }
        String responseBody = response.getBody();

        switch (onapRequest.getResponseType()) {
            case JSONObject: {
                return new JSONObject(responseBody);
            }
            case JSONArray: {
                return new JSONArray(responseBody);
            }
            case STRING: {
                return responseBody;
            }
        }
        return null;
    }

    private String enrichPayloadData(String payloadData, Map<String, String> parameters) {
        for (String key : parameters.keySet()) {
            payloadData = payloadData.replaceAll("\\$\\{" + key + "\\}", parameters.get(key));
        }
        return payloadData;
    }

    private String readResourceFileToString(String path) throws IOException {
        if (path == null) {
            return null;
        }
        return copyStreamToString(getResourceStream(path));
    }

    private InputStream getResourceStream(String payloadFilePath) {
        InputStream is = OnapAdaptor.class.getClassLoader().getResourceAsStream(payloadFilePath);
        return is;
    }

    private String copyStreamToString(InputStream stream) throws IOException {
        StringBuilder str = new StringBuilder();
        while (true) {
            byte[] data = new byte[4096];

            int count = stream.read(data);
            if (count < 0) {
                break;
            }
            str.append(new String(data, 0, count));
        }
        return str.toString();
    }

}
