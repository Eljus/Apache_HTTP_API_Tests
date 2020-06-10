package com.postcodes.tests;

import com.postcodes.api.ProjectConfig;
import com.postcodes.api.conditions.Conditions;
import com.postcodes.api.logic.BaseLogic;
import com.postcodes.api.services.PostcodeApiService;
import org.aeonbits.owner.ConfigFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class Get_Query_Postcodes {

    private final PostcodeApiService postcodeApiService = new PostcodeApiService();
    private ProjectConfig config = ConfigFactory.create(ProjectConfig.class);
    private String projectUrl;

    @BeforeClass
    public void setUp() {
        this.projectUrl = config.baseGetQueryUrl();
    }

    @Test
    public void validateTimeSpentOnFullResponseWithCv1() throws IOException {
        long startTime = System.currentTimeMillis();

        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1")
                .shouldHave(Conditions.statusCode(200));
        long elapsedTime = System.currentTimeMillis() - startTime;

        new BaseLogic().validateQueryTimeSpentIsInRange(elapsedTime, config.timeLimitTestHasToPass());
    }

    @Test
    public void validateAutoCompletedPostcodeWhichStartsFromThirdDigit0() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "0")
                .shouldHave(Conditions.bodyField("result[0]/postcode", "AB10 1AB"));
    }

    @Test
    public void resultNullWhenTakenLastCharacterFromOutcodeAndFirstFromIncodeWithSpace() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "1%203")
                .shouldHave(Conditions.bodyField("result", "null"));
    }

    @Test
    public void resultNullWhenTakenTwoLastCharacterFromOutcodeAndFirstFromIncodeWithSpace() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "V1%203")
                .shouldHave(Conditions.bodyField("result", "null"));
    }

    @Test
    public void resultNullWhenTakenLastCharacterFromOutcodeAndTwoFirstFromIncodeWithSpace() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "1%203G")
                .shouldHave(Conditions.bodyField("result", "null"));
    }

    @Test
    public void resultNullWhenTakenLastCharacterFromOutcodeAndFirstFromIncodeWithoutSpace() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "13")
                .shouldHave(Conditions.bodyField("result", "null"));
    }

    @Test
    public void resultNullWhenTakenTwoLastCharacterFromOutcodeAndFirstFromIncodeWithoutSpace() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "V13")
                .shouldHave(Conditions.bodyField("result", "null"));
    }

    @Test
    public void resultNullWhenTakenLastCharacterFromOutcodeAndTwoFirstFromIncodeWithoutSpace() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "13G")
                .shouldHave(Conditions.bodyField("result", "null"));
    }

    @Test
    public void validateAutoCompletedPostcodeWhichStartsFromSecondDigitB() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "B")
                .shouldHave(Conditions.bodyField("result[0]/postcode", "B10 0AB"));
    }

    @Test
    public void validateAutoCompletedPostcodeWhichStartsFromFourthDigitWhichIsNotSpaceB() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "B")
                .shouldHave(Conditions.bodyField("result[0]/postcode", "B10 0AB"));
    }

    @Test
    public void validateIncodeIsFoundFor1AH() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "1AH")
                .shouldHave(Conditions.bodyField("result[0]/postcode", "AB10 1AH"));
    }

    @Test
    public void validatePrefixAsSpaceDoesReturnsValidPostcodeForCV13GA() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "%20%20%20%20CV13GA")
                .shouldHave(Conditions.bodyField("result[0]/postcode", "CV1 3GA"));
    }

    @Test
    public void validatePrefixAsSpaceDoesReturnsValidPostcodeAfterCV13GA() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV13GA%20%20%20%20")
                .shouldHave(Conditions.bodyField("result[0]/postcode", "CV1 3GA"));
    }

    @Test
    public void validateresultNullWhenSpaceIsAfterFirstLetter() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "C%20V13GA")
                .shouldHave(Conditions.bodyField("result", "null"));
    }

    @Test
    public void validateErrorOnNoQueryParameters() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "")
                .shouldHave(Conditions.statusCode(400))
                .shouldHave(Conditions.bodyField("error", "No postcode query submitted. Remember to include query parameter"));
    }

    @Test
    public void validateEleventhElementIsNotInJson() {
        String errorMessage = "";
        try {
            postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1")
                    .shouldHave(Conditions.bodyField("result[10]/postcode", "CV1 3GA"));
        } catch (Exception e) {
            errorMessage = e.toString();
        }
        Assert.assertEquals(errorMessage, "org.json.JSONException: JSONArray[10] not found.");
    }

    @Test
    public void validateInwardCode0NY() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "0NY")
                .shouldHave(Conditions.bodyField("result", "null"));
    }

    @Test
    public void validateInwardCode7GZ() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "7GZ")
                .shouldHave(Conditions.bodyField("result", "null"));
    }

    @Test
    public void validateInwardCode7HF() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "7HF")
                .shouldHave(Conditions.bodyField("result", "null"));
    }

    @Test
    public void validateInwardCode8JQ() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "8JQ")
                .shouldHave(Conditions.bodyField("result", "null"));
    }

    @Test
    public void validateNullresultOnWrongUrl() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "//////Test")
                .shouldHave(Conditions.bodyField("result", "null"));
    }

    @Test
    public void validateMultipleQueuesAreNotSupported() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1?q=CV2")
                .shouldHave(Conditions.bodyField("result", "null"));
    }

    @Test
    public void validateStatusNullWithUnderspaceBetweenIncodeAndOutcode() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1_3ga")
                .shouldHave(Conditions.bodyField("result", "null"));
    }

    @Test
    public void validateHeaderServer() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1")
                .shouldHave(Conditions.header("Server", "[Server: nginx/1.14.0]"));
    }

    @Test
    public void validateHeaderContentType() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1")
                .shouldHave(Conditions.header("Content-Type", "[Content-Type: application/json; charset=utf-8]"));
    }

    @Test
    public void validateHeaderConnection() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1")
                .shouldHave(Conditions.header("Connection", "[Connection: keep-alive]"));
    }

    @Test
    public void validateGnu() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1")
                .shouldHave(Conditions.header("X-GNU", "[X-GNU: Michael J Blanchard]"));
    }

    @Test
    public void validateCogIdWithFirstresultWithOutcodeCv1() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1")
                .shouldHave(Conditions.bodyField("result[0]/codes/ccg_id", "05A"));
    }

    @Test
    public void validateStatusCodeWithOutcodeCv1() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1")
                .shouldHave(Conditions.statusCode(200));
    }

    @Test
    public void validateStatusCodeWithOutcodeCC() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CC")
                .shouldHave(Conditions.statusCode(200));
    }

    @Test
    public void validateresultWithOutcodeCC() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CC")
                .shouldHave(Conditions.bodyField("result", "null"));
    }

    @Test
    public void validatePostcodeForFirstElementForCV1() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1")
                .shouldHave(Conditions.bodyField("result[0]/postcode", "CV1 1AH"));
    }

    @Test
    public void validatePostcodeForSecondElementForCV1() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1")
                .shouldHave(Conditions.bodyField("result[1]/postcode", "CV1 1AJ"));
    }

    @Test
    public void validatePostcodeForThirdElementForCV1() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1")
                .shouldHave(Conditions.bodyField("result[2]/postcode", "CV1 1DD"));
    }

    @Test
    public void validatePostcodeForFourthElementForCV1() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1")
                .shouldHave(Conditions.bodyField("result[3]/postcode", "CV1 1DE"));
    }

    @Test
    public void validatePostcodeForFifthElementForCV1() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1")
                .shouldHave(Conditions.bodyField("result[4]/postcode", "CV1 1DF"));
    }

    @Test
    public void validatePostcodeForSixthElementForCV1() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1")
                .shouldHave(Conditions.bodyField("result[5]/postcode", "CV1 1DG"));
    }

    @Test
    public void validatePostcodeForSeventhElementForCV1() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1")
                .shouldHave(Conditions.bodyField("result[6]/postcode", "CV1 1DL"));
    }

    @Test
    public void validatePostcodeForEighthElementForCV1() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1")
                .shouldHave(Conditions.bodyField("result[7]/postcode", "CV1 1DN"));
    }

    @Test
    public void validatePostcodeForNinthElementForCV1() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1")
                .shouldHave(Conditions.bodyField("result[8]/postcode", "CV1 1DS"));
    }

    @Test
    public void validatePostcodeForTenthElementForCV1() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "CV1")
                .shouldHave(Conditions.bodyField("result[9]/postcode", "CV1 1DX"));
    }

    @Test
    public void validateWholeRequestAnswersAtOnceForCV13GA() throws IOException {
        postcodeApiService.getPostcodeResponse(this.projectUrl + "E17AF")
                .shouldHave(Conditions.statusCode(200))
                .shouldHave(Conditions.bodyField("result[0]/postcode", "E1 7AF"))
                .shouldHave(Conditions.bodyField("result[0]/quality", "1"))
                .shouldHave(Conditions.bodyField("result[0]/eastings", "533537"))
                .shouldHave(Conditions.bodyField("result[0]/northings", "181385"))
                .shouldHave(Conditions.bodyField("result[0]/country", "England"))
                .shouldHave(Conditions.bodyField("result[0]/nhs_ha", "London"))
                .shouldHave(Conditions.bodyField("result[0]/longitude", "-0.076899"))
                .shouldHave(Conditions.bodyField("result[0]/latitude", "51.515613"))
                .shouldHave(Conditions.bodyField("result[0]/european_electoral_region", "London"))
                .shouldHave(Conditions.bodyField("result[0]/primary_care_trust", "City and Hackney Teaching"))
                .shouldHave(Conditions.bodyField("result[0]/region", "London"))
                .shouldHave(Conditions.bodyField("result[0]/lsoa", "City of London 001E"))
                .shouldHave(Conditions.bodyField("result[0]/msoa", "City of London 001"))
                .shouldHave(Conditions.bodyField("result[0]/incode", "7AF"))
                .shouldHave(Conditions.bodyField("result[0]/outcode", "E1"))
                .shouldHave(Conditions.bodyField("result[0]/parliamentary_constituency", "Cities of London and Westminster"))
                .shouldHave(Conditions.bodyField("result[0]/admin_district", "City of London"))
                .shouldHave(Conditions.bodyField("result[0]/parish", "City of London, unparished area"))
                .shouldHave(Conditions.bodyField("result[0]/admin_county", "null"))
                .shouldHave(Conditions.bodyField("result[0]/admin_ward", "Portsoken"))
                .shouldHave(Conditions.bodyField("result[0]/ced", "null"))
                .shouldHave(Conditions.bodyField("result[0]/ccg", "NHS City and Hackney"))
                .shouldHave(Conditions.bodyField("result[0]/nuts", "Camden and City of London"))
                .shouldHave(Conditions.bodyField("result[0]/codes/admin_district", "E09000001"))
                .shouldHave(Conditions.bodyField("result[0]/codes/admin_county", "E99999999"))
                .shouldHave(Conditions.bodyField("result[0]/codes/admin_ward", "E05009308"))
                .shouldHave(Conditions.bodyField("result[0]/codes/parish", "E43000191"))
                .shouldHave(Conditions.bodyField("result[0]/codes/parliamentary_constituency", "E14000639"))
                .shouldHave(Conditions.bodyField("result[0]/codes/ccg", "E38000035"))
                .shouldHave(Conditions.bodyField("result[0]/codes/ccg_id", "07T"))
                .shouldHave(Conditions.bodyField("result[0]/codes/ced", "E99999999"))
                .shouldHave(Conditions.bodyField("result[0]/codes/nuts", "UKI31"));
    }
}
