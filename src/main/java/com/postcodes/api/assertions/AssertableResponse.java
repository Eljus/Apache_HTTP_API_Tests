package com.postcodes.api.assertions;

import com.postcodes.api.conditions.Condition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONObject;

@RequiredArgsConstructor
@Slf4j
public class AssertableResponse {
    private final CloseableHttpResponse response;
    private final JSONObject responseJson;

    public AssertableResponse shouldHave(Condition condition) {
        log.info("About to check condition {}", condition);
        condition.check(response, responseJson);
        return this;
    }
}
