package ui_tests;


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
