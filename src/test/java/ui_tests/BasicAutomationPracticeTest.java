package ui_tests;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.BaseClass;

import static utilities.ConfigReader.getProperty;

public class BasicAutomationPracticeTest extends BaseClass {
    public static Logger log = LogManager.getLogger(BasicAutomationPracticeTest.class);


    @Test(description = "Add and delete item successfully ")
    @Description("Add and delete item successfully ")
    @Story("AP-1")
    @TmsLink("AP-1")
    public void AddRemoveItem() {

        try {
            visit(getProperty("baseURL"));
            automationpractice.addProductFromDashBoard();
            automationpractice.cartPageDetails();
            automationpractice.deleteItemCart();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("failed due to :::" + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @Test(description = "Footer store info validation ")
    @Description("Footer store info validation ")
    @Story("AP-2")
    @TmsLink("AP-2")
    public void FooterStoreValidation() {

        try {
            visit(getProperty("baseURL"));
            automationpractice.FooterStoreInfo();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("failed due to :::" + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }


    @DataProvider(name = "searchData")
    public Object[][] nameData() {
        return new Object[][]{
                {"Dress"},
                {"Automation"}
        };
    }


    @Test(description = "Search Validation with positive and negative", dataProvider = "searchData")
    @Description("Search Validation with positive and negative")
    @Story("AP-3")
    @TmsLink("AP-3")
    public void searchProducts(String dateSearch) {

        try {
            visit(getProperty("baseURL"));
            automationpractice.searchProduct(dateSearch);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("failed due to :::" + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }


}
