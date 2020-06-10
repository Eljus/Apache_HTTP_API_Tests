package api.conditions;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONObject;

public interface Condition {

    void check(CloseableHttpResponse response, JSONObject responseJson);
}
