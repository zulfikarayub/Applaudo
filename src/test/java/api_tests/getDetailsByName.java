package api_tests;

import apibase.utilities.ApiBase;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static utilities.ConfigReader.getProperty;

public class getDetailsByName extends ApiBase {
    public static Logger log = LogManager.getLogger(getDetailsByName.class);

    @DataProvider(name = "newData")
    public Object[][] nameData() {
        return new Object[][]{
                {"Walter", "White"},
                {"Jesse", "Pinkman"}
        };
    }

    @Test(description = "Get the value of Date of Birth from name", dataProvider = "newData")

    public void getDOB(String fName, String lName) {
        domain = getProperty("baseURI_dev");

        // final end point
        String URI = domain + "characters";

        //hitting the api to get the response
        Response response = given().urlEncodingEnabled(false).queryParam("name", fName + "+" + lName).when().get(URI).then().extract().response();
        Assert.assertEquals(response.statusCode(), 200);

        String StrDOB = response.jsonPath().getString("birthday"), StrCharacterName = response.jsonPath().getString("name"), StrPortrayed = response.jsonPath().getString("portrayed");

        log.info("The Birthday is .." + StrDOB);
        log.info("The Character name  is .." + StrCharacterName);
        log.info("The portrayed is .." + StrPortrayed);

    }

}
