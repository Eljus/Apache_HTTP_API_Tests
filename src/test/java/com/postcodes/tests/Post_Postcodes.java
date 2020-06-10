package com.postcodes.tests;

import api.ProjectConfig;
import api.conditions.Conditions;
import api.logic.BaseLogic;
import api.services.PostcodeApiService;
import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class Post_Postcodes {
    private final PostcodeApiService postcodeApiService = new PostcodeApiService();
    private ProjectConfig config = ConfigFactory.create(ProjectConfig.class);
    private String projectUrl;

    @BeforeClass
    public void setUp() {
        this.projectUrl = config.basePostUrl();
    }

    @Test
    public void validateResultErrorIfFirstPostcodesInvalid() throws IOException {
        String json = "{\"postcodes\":[\"5NU\",\"M32 0JG\",\"NE30 1DP\"]}";

        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.bodyField("result[0]/result", "null"));
    }

    @Test
    public void validateResultQueryIfFirstPostcodesInvalid() throws IOException {
        String json = "{\"postcodes\":[\"5NU\",\"M32 0JG\",\"NE30 1DP\"]}";

        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.bodyField("result[0]/query", "5NU"));
    }

    @Test
    public void validateResultErrorIfLastPostcodesInvalid() throws IOException {
        String json = "{\"postcodes\":[\"OX49 5NU\",\"M32 0JG\",\"NE30\"]}";

        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.bodyField("result[2]/result", "null"));
    }

    @Test
    public void validateResultQueryIfLastPostcodesInvalid() throws IOException {
        String json = "{\"postcodes\":[\"OX49 5NU\",\"M32 0JG\",\"NE30\"]}";

        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.bodyField("result[2]/query", "NE30"));
    }

    @Test
    public void validateResultErrorIfMiddlePostcodesInvalid() throws IOException {
        String json = "{\"postcodes\":[\"OX49 5NU\",\"\",\"NE30 1DP\"]}";

        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.bodyField("result[1]/result", "null"));
    }

    @Test
    public void validateResultQueryIfMiddlePostcodesMissing() throws IOException {
        String json = "{\"postcodes\":[\"OX49 5NU\",\"\",\"NE30 1DP\"]}";

        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.bodyField("result[1]/query", ""));
    }

    @Test
    public void validatePostcodesThatAreNotCorruptedWhereFirstIsInvalid() throws IOException {
        String json = "{\"postcodes\":[\"OX49\",\"M32 0JG\",\"NE30 1DP\"]}";

        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.bodyField("result[1]/result/postcode", "M32 0JG"))
                .shouldHave(Conditions.bodyField("result[2]/result/postcode", "NE30 1DP"));
    }

    @Test
    public void validatePostcodesThatAreNotCorruptedWhereSecondIsInvalid() throws IOException {
        String json = "{\"postcodes\":[\"OX49 5NU\",\"M32\",\"NE30 1DP\"]}";

        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.bodyField("result[0]/result/postcode", "OX49 5NU"))
                .shouldHave(Conditions.bodyField("result[2]/result/postcode", "NE30 1DP"));

    }

    @Test
    public void validatePostcodesThatAreNotCorruptedWhereLastIsInvalid() throws IOException {
        String json = "{\"postcodes\":[\"OX49 5NU\",\"M32 0JG\",\"NE30\"]}";

        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.bodyField("result[0]/result/postcode", "OX49 5NU"))
                .shouldHave(Conditions.bodyField("result[1]/result/postcode", "M32 0JG"));
    }

    @Test
    public void validateHeaderGnu() throws IOException {
        String json = "{\"postcodes\":[\"OX49 5NU\",\"M32 0JG\",\"NE30 1DP\"]}";

        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.header("X-GNU", "[X-GNU: Michael J Blanchard]"));
    }

    @Test
    public void validateHeaderContentType() throws IOException {
        String json = "{\"postcodes\":[\"OX49 5NU\",\"M32 0JG\",\"NE30 1DP\"]}";

        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.header("Content-Type", "[Content-Type: application/json; charset=utf-8]"));
    }

    @Test
    public void validateHeaderConnection() throws IOException {
        String json = "{\"postcodes\":[\"OX49 5NU\",\"M32 0JG\",\"NE30 1DP\"]}";

        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.header("Connection", "[Connection: keep-alive]"));
    }

    @Test
    public void validateAllSubmittedPostcodePostcodesCCG() throws IOException {
        String json = "{\"postcodes\":[\"OX49 5NU\",\"M32 0JG\",\"NE30 1DP\"]}";

        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.statusCode(200))
                .shouldHave(Conditions.bodyField("result[0]/result/postcode", "OX49 5NU"))
                .shouldHave(Conditions.bodyField("result[1]/result/postcode", "M32 0JG"))
                .shouldHave(Conditions.bodyField("result[2]/result/postcode", "NE30 1DP"));
    }


    @Test
    public void validateFirstSubmittedPostcodeCCG() throws IOException {
        String json = "{\"postcodes\":[\"OX49 5NU\",\"M32 0JG\",\"NE30 1DP\"]}";

        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.statusCode(200))
                .shouldHave(Conditions.bodyField("result[0]/result/codes/ccg_id", "10Q"));
    }


    @Test
    public void validateStatusCodeErrorOnExceedingAllowedRange101Postcodes() throws IOException {
        String json = "{\"postcodes\":[\"PR3 0SG\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\"]}";
        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.statusCode(400));
    }

    @Test
    public void validateErrorMessageOnExceedingAllowedRange101Postcodes() throws IOException {
        String json = "{\"postcodes\":[\"PR3 0SG\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\"]}";
        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.bodyField("error", "Too many postcodes submitted. Up to 100 postcodes can be bulk requested at a time"));
    }

    @Test
    public void validateStatusCode200On100Postcodes() throws IOException {
        String json = "{\"postcodes\":[\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\"]}";
        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.statusCode(200));
    }

    @Test
    public void validateTimeSpentOnFullResponseNotExceedingBasicTimeWithMaximumAmountOfPostcodes() throws IOException {
        long startTime = System.currentTimeMillis();
        String json = "{\"postcodes\":[\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\",\"PR3 0SG\",\"M45 6GN\",\"EX165BL\",\"CV1 1AH\",\"CV1 1AJ\",\"CV1 1DD\",\"CV1 1DE\",\"CV1 1DF\",\"CV1 1DG\",\"CV1 1DL\"]}";
        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.statusCode(200));
        long elapsedTime = System.currentTimeMillis() - startTime;
        new BaseLogic().validateQueryTimeSpentIsInRange(elapsedTime, config.timeLimitTestHasToPass());
    }

    @Test
    public void validateTimeSpentOnFullResponseNotExceedingBasicTimeWithOnePostcodes() throws IOException {
        long startTime = System.currentTimeMillis();
        String json = "{\"postcodes\":[\"PR3 0SG\"]}";
        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.statusCode(200));
        long elapsedTime = System.currentTimeMillis() - startTime;
        new BaseLogic().validateQueryTimeSpentIsInRange(elapsedTime, config.timeLimitTestHasToPass());
    }

    @Test
    public void validateEmptyPostcodeResultOnNoPostcodes() throws IOException {
        String json = "{\"postcodes\":[]}";
        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.bodyField("result", "[]"));

    }

    @Test
    public void validateStatusCode200OnEmptyJson() throws IOException {
        String json = "{\"postcodes\":[]}";
        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.statusCode(200));

    }

    @Test
    public void validateCodeStatusCode400WhenJsonEmptyString() throws IOException {
        String json = "";
        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.statusCode(400));

    }

    @Test
    public void validateErrorMessageWhenJsonEmptyString() throws IOException {
        String json = "";
        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.bodyField("error", "Invalid JSON query submitted. \nYou need to submit a JSON object with an array of postcodes or geolocation objects.\nAlso ensure that Content-Type is set to application/json\n"));
    }

    @Test
    public void validateCodeStatusCode400WhenJsonInvalidCharacter() throws IOException {
        String json = "-";
        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.statusCode(400));

    }

    @Test
    public void validateCodeStatusCode200WhenPostcodeHasInvalidCharacterBetweenOutcodeAndIncode() throws IOException {
        String json = "{\"postcodes\":[\"OX49_5NU\"]}";
        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.statusCode(200));

    }

    @Test
    public void validateResultNullWhenPostcodeHasInvalidCharacterBetweenOutcodeAndIncode() throws IOException {
        String json = "{\"postcodes\":[\"OX49_5NU\"]}";
        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.bodyField("result[0]/result", "null"));
    }

    @Test
    public void validateQueryHasInsertedInvalidPostcodeWhenPostcodeHasInvalidCharacterBetweenOutcodeAndIncode() throws IOException {
        String json = "{\"postcodes\":[\"OX49_5NU\"]}";
        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.bodyField("result[0]/query", "OX49_5NU"));
    }

    @Test
    public void validateResultAgainstCustomJson() throws IOException {
        String json = "{\"postcodes\":[\"OX49_5NU\"]}";
        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.bodyField("result", "[{\"result\":null,\"query\":\"OX49_5NU\"}]"));
    }

    @Test
    public void validatePostcodeOfOneSubmittedPostcode() throws IOException {
        String json = "{\"postcodes\":[\"OX49 5NU\"]}";
        postcodeApiService
                .registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.bodyField("result[0]/result/postcode", "OX49 5NU"));
    }

    @Test
    public void validateWholeRequestAnswersAtOnceForCV13GA() throws IOException {
        String json = "{\"postcodes\":[\"OX49 5NU\"]}";
        postcodeApiService.registerPostcode(this.projectUrl, json)
                .shouldHave(Conditions.statusCode(200))
                .shouldHave(Conditions.bodyField("result[0]/result/country", "England"))
                .shouldHave(Conditions.bodyField("result[0]/result/codes/ccg_id", "10Q"))
                .shouldHave(Conditions.bodyField("result[0]/result/codes/ced", "E58001238"))
                .shouldHave(Conditions.bodyField("result[0]/result/codes/nuts", "UKJ14"))
                .shouldHave(Conditions.bodyField("result[0]/result/codes/parish", "E04008109"))
                .shouldHave(Conditions.bodyField("result[0]/result/codes/ccg", "E38000136"))
                .shouldHave(Conditions.bodyField("result[0]/result/codes/admin_ward", "E05009735"))
                .shouldHave(Conditions.bodyField("result[0]/result/codes/parliamentary_constituency", "E14000742"))
                .shouldHave(Conditions.bodyField("result[0]/result/codes/admin_county", "E10000025"))
                .shouldHave(Conditions.bodyField("result[0]/result/codes/admin_district", "E07000179"))
                .shouldHave(Conditions.bodyField("result[0]/result/ced", "Chalgrove and Watlington"))
                .shouldHave(Conditions.bodyField("result[0]/result/ccg", "NHS Oxfordshire"))
                .shouldHave(Conditions.bodyField("result[0]/result/latitude", "51.6562"))
                .shouldHave(Conditions.bodyField("result[0]/result/postcode", "OX49 5NU"))
                .shouldHave(Conditions.bodyField("result[0]/result/european_electoral_region", "South East"))
                .shouldHave(Conditions.bodyField("result[0]/result/parliamentary_constituency", "Henley"))
                .shouldHave(Conditions.bodyField("result[0]/result/admin_district", "South Oxfordshire"))
                .shouldHave(Conditions.bodyField("result[0]/result/eastings", "464438"))
                .shouldHave(Conditions.bodyField("result[0]/result/admin_county", "Oxfordshire"))
                .shouldHave(Conditions.bodyField("result[0]/result/lsoa", "South Oxfordshire 011B"))
                .shouldHave(Conditions.bodyField("result[0]/result/msoa", "South Oxfordshire 011"))
                .shouldHave(Conditions.bodyField("result[0]/result/admin_district", "South Oxfordshire"))
                .shouldHave(Conditions.bodyField("result[0]/result/quality", "1"))
                .shouldHave(Conditions.bodyField("result[0]/result/primary_care_trust", "Oxfordshire"))
                .shouldHave(Conditions.bodyField("result[0]/result/nuts", "Oxfordshire"))
                .shouldHave(Conditions.bodyField("result[0]/result/parish", "Brightwell Baldwin"))
                .shouldHave(Conditions.bodyField("result[0]/result/outcode", "OX49"))
                .shouldHave(Conditions.bodyField("result[0]/result/northings", "195677"))
                .shouldHave(Conditions.bodyField("result[0]/result/nhs_ha", "South Central"))
                .shouldHave(Conditions.bodyField("result[0]/result/incode", "5NU"))
                .shouldHave(Conditions.bodyField("result[0]/result/region", "South East"))
                .shouldHave(Conditions.bodyField("result[0]/result/longitude", "-1.069876"))
                .shouldHave(Conditions.bodyField("result[0]/query", "OX49 5NU"));
    }


/*    @Test //working one
    public void demo3() throws IOException {
        HttpPost httpPost = new HttpPost("http://api.postcodes.io/postcodes/");

        String json = "{\"postcodes\":[\"OX49 5NU\",\"M32 0JG\",\"NE30 1DP\"]}";
        CloseableHttpClient client = HttpClients.createDefault();
        StringEntity entity = new StringEntity(json);

        httpPost.setEntity(entity);
        //httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPost);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
        JSONObject responseJson = new JSONObject(responseString);


        System.out.println(responseJson);

        String lastName = BaseLogic.getValueByJPath(responseJson, "result[0]/result/codes/ccg_id");

        System.out.println(lastName);
        client.close();
    }*/

}
