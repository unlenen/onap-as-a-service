/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.model.OnapRequest;
import tr.com.argela.nfv.onap.serviceManager.onap.adaptor.exception.OnapRequestFailedException;

/**
 *
 * @author Nebi Volkan UNLENEN(unlenen@gmail.com)
 */
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

    public URLResponse call(String url, String type, String token, String log, int expectedResponseCode, boolean toJSON, String jsonArrayKey) throws IOException {
        OkHttpClient client = getHttpsBuilder().build();
        String logText = " url:" + url;
        System.out.println("[OpenstackService][" + log + "][Start] " + logText + " , token:" + token);
        Request request = new Request.Builder()
                .url(url)
                .method(type, null)
                .addHeader("X-Auth-Token", token)
                .build();
        Response response = client.newCall(request).execute();
        ResponseBody responseBody = response.body();
        if (expectedResponseCode > 0) {
            if (response.code() != expectedResponseCode) {
                throw new IOException(responseBody.string());
            }
        }

        String responseStr = responseBody.string();
        int responseArraySize = -1;
        JSONObject root = null;
        if (toJSON) {
            root = new JSONObject(responseStr);
            if (jsonArrayKey != null && root.has(jsonArrayKey)) {
                responseArraySize = root.getJSONArray(jsonArrayKey).length();
            }
        }

        URLResponse urlResponse = new URLResponse(response.code(), responseStr, root);
        System.out.println("[OpenstackService][" + log + "][Complete] " + logText + " respSize:" + responseStr.length() + ", size:" + responseArraySize);
        return urlResponse;
    }

    public Response post(String url, String data, String type) throws IOException {
        OkHttpClient client = getHttpsBuilder().build();
        RequestBody body = RequestBody.create(data, MediaType.parse(type));
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", type)
                .build();
        return client.newCall(request).execute();
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
        Response response = get(onapRequest.getEndpoint(parameters), onapRequest.getOnapModule().getHeaders());
        ResponseBody responseBody = response.body();
        if (response.code() != onapRequest.getValidReturnCode()) {
            throw new OnapRequestFailedException(onapRequest, responseBody.string());
        }
        String data = responseBody.string();

        switch (onapRequest.getResponseType()) {
            case JSON: {
                return new JSONObject(data);
            }
            case STRING: {
                return data;
            }
        }
        return null;
    }

}
