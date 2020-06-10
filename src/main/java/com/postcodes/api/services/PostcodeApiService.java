package com.postcodes.api.services;


import com.postcodes.api.assertions.AssertableResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.IOException;

public class PostcodeApiService extends ApiService {


    public AssertableResponse registerPostcode(String url, String json) throws IOException {
        return new AssertableResponse(setup(url, json), getResponseJson());
    }

    public AssertableResponse getPostcodeResponse(String url) throws IOException {
        return new AssertableResponse(setup(url), getResponseJson());
    }

    public AssertableResponse registerPostcodeWithAllCustomFields(CloseableHttpResponse response, HttpPost httpPost, StringEntity entity) throws IOException {
        return new AssertableResponse(setup(response, httpPost, entity), getResponseJson());
    }
}
