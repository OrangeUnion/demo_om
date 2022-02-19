package com.example.demo.clients;


import com.example.demo.utils.PrettyPrint;
import com.example.demo.utils.Utils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BZLMClient {

    private static final String HOST = "http://www.cocbzlm.com";
    private static final String PORT = ":8422";
    private static final String contentType = "application/json";

    public static String getBZLMAccountInfo(String clanTag) {
        final String PATH = "/api/accinfo";
        clanTag = Utils.getFormalizedCLanTag(clanTag);
        String url = HOST + PORT + PATH;

        List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
        JSONObject payload = new JSONObject();
        payload.put("myTag", clanTag);
        payload.put("isGlobal", false);
        String result = "";
        try {
            result = cocHttpPost(url, payload).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String cocHttpGet(String url) {
        return "";
    }

    public static HttpResponse<String> cocHttpPost(String url, JSONObject payload) throws UnirestException {
        HttpResponse<String> response = Unirest.post(url)
                .header("Content-Type", "application/json")
                .body(PrettyPrint.printFromJsonObject(payload))
                .asString();
        return response;
    }
}
