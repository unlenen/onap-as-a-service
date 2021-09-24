/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
