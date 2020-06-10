package com.postcodes.api.services;

import com.postcodes.api.ProjectConfig;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

@Slf4j
public class ApiService {
    private JSONObject responseJson;
    private CloseableHttpClient client = HttpClients.createDefault();

    protected CloseableHttpResponse setup(String url, String json) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPost);

        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
        this.responseJson = new JSONObject(responseString);
        getFilters(response);
        return response;
    }

    protected CloseableHttpResponse setup(String url) throws IOException {
        HttpGet httpget = new HttpGet(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();

        CloseableHttpResponse response = httpClient.execute(httpget);

        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
        this.responseJson = new JSONObject(responseString);
        getFilters(response);
        return response;
    }

    protected CloseableHttpResponse setup(CloseableHttpResponse response, HttpPost httpPost, StringEntity entity) throws IOException {
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");
        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
        this.responseJson = new JSONObject(responseString);
        getFilters(response);
        return response;
    }

    public JSONObject getResponseJson() {
        return responseJson;
    }

    public CloseableHttpClient getClient() {
        return client;
    }

    private void getFilters(CloseableHttpResponse response) {
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class);
        if (config.logging()) {
            for (Header header : response.getAllHeaders()) {
                log.info(header.toString());
            }

        }
    }
}
