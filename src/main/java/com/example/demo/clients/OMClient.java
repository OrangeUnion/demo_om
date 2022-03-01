package com.example.demo.clients;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OMClient {
    public static void main(String[] args) {
        List<String> arrayList = getOmState("q82u2qr9");
        System.out.println(arrayList.get(0));
    }

    public static List<String> getOmName(String tag) {
        String omData = getOmApi(tag.replaceAll("#", ""));
        List<String> status = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(omData);
        if (!jsonObject.isNull("data")) {
            JSONObject data = jsonObject.getJSONObject("data");
            String clanTag = data.getString("id");
            String name = data.getString("name");
            String state = data.getString("state");
            status.add(0, clanTag);
            status.add(1, name);
            status.add(2, state);
        } else {
            status.add(0, "null");
            status.add(1, "null");
            status.add(2, "null");
        }
        return status;
    }

    public static List<String> getOmState(String tag) {
        String omData = getOmApi(tag.replaceAll("#", ""));
        List<String> status = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(omData);
        if (!jsonObject.isNull("data")) {
            JSONObject data = jsonObject.getJSONObject("data");
            String state = data.getString("state");
            String name = data.getString("name");
            if ("正常".equals(state)) {
                status.add(0, "正常O盟部落");
                status.add(1, "badge bg-primary");
            }
            if ("冻结".equals(state)) {
                status.add(0, "O盟冻结部落");
                status.add(1, "badge bg-info");
            }
            if ("寄生虫".equals(state)) {
                status.add(0, "O盟黑名单");
                status.add(1, "badge bg-danger");
            }
            status.add(2, name);
        } else {
            status.add(0, null);
            status.add(1, null);
        }
        return status;
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
