package com.postcodes.tests;

import api.ProjectConfig;
import api.conditions.Conditions;
import api.logic.BaseLogic;
import api.services.PostcodeApiService;
import org.aeonbits.owner.ConfigFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class Get_Postcodes {

    private final PostcodeApiService postcodeApiService = new PostcodeApiService();
    private ProjectConfig config = ConfigFactory.create(ProjectConfig.class);
    private String projectUrl;

    @BeforeClass
    public void setUp() {
        this.projectUrl = config.baseGetUrl();
    }


    @Test
    public void validateStatusCodeForStandardPostcode() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV13GA")
                .shouldHave(Conditions.statusCode(200));
    }

    @Test
    public void validateResultNullWhenIncodeIsMissing() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "3GA")
                .shouldHave(Conditions.bodyField("error", "Invalid postcode"));
    }

    @Test
    public void validateResultNullWhenOutcodeIsMissing() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1")
                .shouldHave(Conditions.bodyField("error", "Invalid postcode"));
    }

    @Test
    public void validateResultNullWhenIncodeFirstCharacterIsMissing() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "V13GA")
                .shouldHave(Conditions.bodyField("error", "Postcode not found"));
    }

    @Test
    public void validateResultNullWhenIncodeSecondCharacterIsMissing() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "C13GA")
                .shouldHave(Conditions.bodyField("error", "Postcode not found"));
    }

    @Test
    public void validateResultNullWhenIncodeThirdCharacterIsMissing() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV3GA")
                .shouldHave(Conditions.bodyField("error", "Invalid postcode"));
    }

    @Test
    public void validateResultNullWhenOutcodeFirstCharacterIsMissing() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1GA")
                .shouldHave(Conditions.bodyField("error", "Invalid postcode"));
    }

    @Test
    public void validateResultNullWhenOutcodeSecondCharacterIsMissing() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV13A")
                .shouldHave(Conditions.bodyField("error", "Invalid postcode"));
    }

    @Test
    public void validateResultNullWhenOutcodeThirdCharacterIsMissing() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV13G")
                .shouldHave(Conditions.bodyField("error", "Invalid postcode"));
    }

    @Test
    public void validateBodyFieldCogIdCodeForStandardPostcode() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV13GA")
                .shouldHave(Conditions.bodyField("result/codes/ccg_id", "05A"));
    }

    @Test
    public void validateStatusCode404WithUnderspaceBetweenIncodeAndOutcode() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1_3ga")
                .shouldHave(Conditions.statusCode(404));
    }

    @Test
    public void validateStatusCode404WhenSpaceIsAfterFirstLetter() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "C%20V13GA")
                .shouldHave(Conditions.statusCode(404));
    }

    @Test
    public void validateStatusCode200WHenSpacesBeforePostcode() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "%20%20%20%20CV13GA")
                .shouldHave(Conditions.statusCode(200));
    }

    @Test
    public void validateStatusCode200WhenSpacesAfterPostCode() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV13GA%20%20%20%20")
                .shouldHave(Conditions.statusCode(200));
    }

    @Test
    public void validateStatusCode200WhenSpacesBetweenIncodeAndOutcode() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1%20%20%20%203GA")
                .shouldHave(Conditions.statusCode(200));
    }

    @Test
    public void validateSecondElementIsNotInJson() {
        String errorMessage = "";
        try {
            postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1")
                    .shouldHave(Conditions.bodyField("result[1]", "CV1 3GA"));
        } catch (Exception e) {
            errorMessage = e.toString();
        }
        Assert.assertEquals(errorMessage, "org.json.JSONException: JSONObject[\"result\"] not found.");
    }

    @Test
    public void validateTimeSpentOnFullResponseNotExceedingBasicTimeWithCv13GA() throws IOException {
        long startTime = System.currentTimeMillis();
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV13GA")
                .shouldHave(Conditions.statusCode(200));
        long elapsedTime = System.currentTimeMillis() - startTime;

        new BaseLogic().validateQueryTimeSpentIsInRange(elapsedTime, config.timeLimitTestHasToPass());
    }

    @Test
    public void validateWholeRequestAnswersAtOnceForCV13GA() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "E17AF")
                .shouldHave(Conditions.statusCode(200))
                .shouldHave(Conditions.bodyField("result/postcode", "E1 7AF"))
                .shouldHave(Conditions.bodyField("result/quality", "1"))
                .shouldHave(Conditions.bodyField("result/eastings", "533537"))
                .shouldHave(Conditions.bodyField("result/northings", "181385"))
                .shouldHave(Conditions.bodyField("result/country", "England"))
                .shouldHave(Conditions.bodyField("result/nhs_ha", "London"))
                .shouldHave(Conditions.bodyField("result/longitude", "-0.076899"))
                .shouldHave(Conditions.bodyField("result/latitude", "51.515613"))
                .shouldHave(Conditions.bodyField("result/european_electoral_region", "London"))
                .shouldHave(Conditions.bodyField("result/primary_care_trust", "City and Hackney Teaching"))
                .shouldHave(Conditions.bodyField("result/region", "London"))
                .shouldHave(Conditions.bodyField("result/lsoa", "City of London 001E"))
                .shouldHave(Conditions.bodyField("result/msoa", "City of London 001"))
                .shouldHave(Conditions.bodyField("result/incode", "7AF"))
                .shouldHave(Conditions.bodyField("result/outcode", "E1"))
                .shouldHave(Conditions.bodyField("result/parliamentary_constituency", "Cities of London and Westminster"))
                .shouldHave(Conditions.bodyField("result/admin_district", "City of London"))
                .shouldHave(Conditions.bodyField("result/parish", "City of London, unparished area"))
                .shouldHave(Conditions.bodyField("result/admin_county", "null"))
                .shouldHave(Conditions.bodyField("result/admin_ward", "Portsoken"))
                .shouldHave(Conditions.bodyField("result/ced", "null"))
                .shouldHave(Conditions.bodyField("result/ccg", "NHS City and Hackney"))
                .shouldHave(Conditions.bodyField("result/nuts", "Camden and City of London"))
                .shouldHave(Conditions.bodyField("result/codes/admin_district", "E09000001"))
                .shouldHave(Conditions.bodyField("result/codes/admin_county", "E99999999"))
                .shouldHave(Conditions.bodyField("result/codes/admin_ward", "E05009308"))
                .shouldHave(Conditions.bodyField("result/codes/parish", "E43000191"))
                .shouldHave(Conditions.bodyField("result/codes/parliamentary_constituency", "E14000639"))
                .shouldHave(Conditions.bodyField("result/codes/ccg", "E38000035"))
                .shouldHave(Conditions.bodyField("result/codes/ccg_id", "07T"))
                .shouldHave(Conditions.bodyField("result/codes/ced", "E99999999"))
                .shouldHave(Conditions.bodyField("result/codes/nuts", "UKI31"));
    }
}
