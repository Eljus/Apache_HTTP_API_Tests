package com.postcodes.api.conditions;

import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONObject;
import org.testng.Assert;

import java.util.Arrays;

@RequiredArgsConstructor
public class HeaderCondition implements Condition {

    private final String header;
    private final String headerValue;

    @Override
    public void check(CloseableHttpResponse response, JSONObject responseJson) {
        Assert.assertEquals(Arrays.toString(response.getHeaders(header)), headerValue);
    }

    @Override
    public String toString() {
        return "Header is " + headerValue;
    }
}
