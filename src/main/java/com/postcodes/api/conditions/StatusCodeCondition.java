package com.postcodes.api.conditions;

import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONObject;
import org.testng.Assert;

@RequiredArgsConstructor
public class StatusCodeCondition implements Condition {

    private final int statusCode;

    @Override
    public void check(CloseableHttpResponse response,  JSONObject responseJson) {
        Assert.assertEquals(response.getStatusLine().getStatusCode(), statusCode);
    }

    @Override
    public String toString() {
        return "Status code is " + statusCode;
    }
}
