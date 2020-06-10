package api.conditions;

import api.logic.BaseLogic;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONObject;
import org.testng.Assert;

@RequiredArgsConstructor
public class BodyFieldCondition implements Condition {

    private final String jsonPath;
    private final String matcher;

    @Override
    public void check(CloseableHttpResponse response, JSONObject responseJson) {
        Assert.assertEquals(new BaseLogic().getValueByJPath(responseJson, jsonPath), matcher);
    }

    @Override
    public String toString() {
        return "body field [" + jsonPath + "] " + matcher;
    }
}
