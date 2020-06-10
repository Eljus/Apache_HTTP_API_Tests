package api.conditions;

import lombok.experimental.UtilityClass;
import org.hamcrest.Matcher;
import org.json.JSONObject;


@UtilityClass
public class Conditions {

    public StatusCodeCondition statusCode(int code){
        return new StatusCodeCondition(code);
    }

    public BodyFieldCondition bodyField(String jsonPath, String matcher){
        return new BodyFieldCondition(jsonPath, matcher);
    }

    public HeaderCondition header(String header, String headerValue){
        return new HeaderCondition(header, headerValue);
    }
}
