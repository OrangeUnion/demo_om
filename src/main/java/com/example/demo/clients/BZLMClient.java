package com.example.demo.clients;

import com.example.demo.utils.Utils;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        nameValuePairs.add(new BasicNameValuePair("myTag", clanTag));
        nameValuePairs.add(new BasicNameValuePair("isGlobal", "false"));
        return cocHttpPost(url, nameValuePairs);
    }

    public static String getJsonString(HttpResponse response) {
        String result = "";
        try {
            result = convertStreamToString(response.getEntity().getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String cocHttpGet(String url) {
        HttpGet request = new HttpGet(url);

//        request.addHeader("x-api-key", apiKeyBZLM);
        request.addHeader("content-type", contentType);

        HttpResponse response = null;
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            response = httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return getJsonString(response);
    }

    public static String cocHttpPost(String url, List<BasicNameValuePair> params) {
        HttpPost request = new HttpPost(url);

//        request.addHeader("x-api-key", apiKeyBZLM);
        request.addHeader("content-type", contentType);

        HttpResponse response = null;
        try {
            request.setEntity(new UrlEncodedFormEntity(params));
            HttpClient httpClient = HttpClientBuilder.create().build();
            response = httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return getJsonString(response);
    }
}
