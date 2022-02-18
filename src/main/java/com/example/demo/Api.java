package com.example.demo;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Api {
    public static void main(String[] args) {
        String s = getOmApi("20RQL0Q8P");
        String om = getOmApi("20RQL0Q8P");
        if (om.contains("\"status\":0")) {
            System.out.println("111");
        } else {
            System.out.println("222");
        }
        System.out.println(getOmApi("20RQL0Q8P"));

    }
    public static String getOmApi(String tag) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url;
        url = "https://omcoc.club/user/datas?tag=%23" + tag;
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = null;
        try {
            assert response != null;
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
