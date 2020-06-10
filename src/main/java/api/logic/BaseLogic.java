package api.logic;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

@Slf4j
public class BaseLogic {

    public String getValueByJPath(JSONObject jsonResponse, String jPath) {
        Object object = jsonResponse;
        for (String s : jPath.split("/"))
            if (!s.isEmpty())
                if (!(s.contains("[") || s.contains("]")))
                    object = ((JSONObject) object).get(s);
                else if (s.contains("[") || s.contains("]"))
                    object = ((JSONArray) ((JSONObject) object).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
        return object.toString();
    }

    public BaseLogic validateQueryTimeSpentIsInRange(long actualTimeSpent, long timeLimitTestHasToPass){
        if(actualTimeSpent >= timeLimitTestHasToPass){
            log.debug("Response took too much time");
            Assert.fail();
        }
        return this;
    }
}