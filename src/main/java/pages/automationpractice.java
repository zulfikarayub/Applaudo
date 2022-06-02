package pages;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utilities.BaseClass;

import java.util.List;

public class automationpractice extends BaseClass {

    public static Logger log = LogManager.getLogger(automationpractice.class);

    @FindBy(xpath = "//ul[@id='homefeatured']//li//a[@class='product_img_link']")
    public List<WebElement> dashProductImg;

    @FindBy(xpath = "//iframe[@class='fancybox-iframe']")
    public WebElement productIframe;

    @FindBy(xpath = "//h1[@itemprop='name']")
    public WebElement popUpProductName;

    @FindBy(xpath = "//form[@id='buy_block']//span[@itemprop='price']")
    public WebElement popUpProductPrice;

    @FindBy(xpath = " //form[@id='buy_block']//span[text()='Add to cart']")
    public WebElement popUpAddToCart;

    @FindBy(xpath = "//div[@id='layer_cart']//div[contains(@class,'layer_cart_product')]//h2")
    public WebElement popUpSuccessMessage;

    @FindBy(xpath = "//a[@title='Proceed to checkout']//span[normalize-space(text()='Proceed to checkout')]")
    public WebElement popUpProceedToCheckOut;

    @FindBy(xpath = "//div[@id='order-detail-content']//p[@class='product-name']")
    public WebElement cartPageProductName;

    @FindBy(xpath = "//div[@id='order-detail-content']//td[@data-title='Unit price']/span[@class='price']")
    public WebElement cartPageProductPrice;

    @FindBy(xpath = "  //a[@title='Delete']")
    public WebElement cartPageDeleteIcon;

    @FindBy(xpath = "  //p[text()='Your shopping cart is empty.']")
    public WebElement cartPageEmptyMessage;

    @FindBy(xpath = "//section[@id='block_contact_infos']//h4")
    public WebElement footerStoreInfoHeader;

    @FindBy(xpath = "//section[@id='block_contact_infos']//li")
    public List<WebElement> footerStoreInfoDetails;

    @FindBy(id = "search_query_top")
    public WebElement searchBoxDash;

    @FindBy(xpath = "//div[@class='ac_results']//li")
    public List<WebElement> searchResultList;


    public automationpractice(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    @Step("add product to cart and validating successfully message is displayed")
    public void addProductFromDashBoard() {

        try {
            count = dashProductImg.size();
            log.info("no. of product displayed in dashboard .." + count);
            if (count > 1) {
                dashProductImg.get(0).click();
            } else {
                log.error("no product displayed in the page");
                Assert.fail();
            }
            driver.switchTo().frame(productIframe);
            waitForVisibility(popUpProductName, 10);
            UiProductName = popUpProductName.getText().trim();
            log.info("Product name is .." + UiProductName);

            UiProductPrice = popUpProductPrice.getText().trim();
            log.info("Product prioe is .." + UiProductPrice);

            popUpAddToCart.click();
            log.info("Add to button clicked");

            driver.switchTo().defaultContent();

            waitForVisibility(popUpSuccessMessage, 15);
            Assert.assertEquals(popUpSuccessMessage.getText().trim(), "Product successfully added to your shopping cart");
            log.info("successfully added the product to cart..");

            waitForVisibility(popUpProceedToCheckOut, 10);
            popUpProceedToCheckOut.click();
            log.info("proceed to checkout button button clicked");


        } catch (Exception e) {
            e.printStackTrace();
            log.error("failed due to :::" + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @Step("validate product price and product name in cart page")
    public void cartPageDetails() {

        try {
            waitForVisibility(cartPageProductName, 20);
            log.info("product name from cart page is .." + cartPageProductName.getText().trim());
            Assert.assertEquals(cartPageProductName.getText().trim(), UiProductName);

            log.info("product price from cart page is .." + cartPageProductPrice.getText().trim());
            Assert.assertEquals(cartPageProductPrice.getText().trim(), UiProductPrice);


        } catch (Exception e) {
            e.printStackTrace();
            log.error("failed due to :::" + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @Step("Delete the item from cart ")
    public void deleteItemCart() {

        try {
            waitForVisibility(cartPageDeleteIcon, 20);
            cartPageDeleteIcon.click();
            log.info("delete icon is clicked ...");

            waitForVisibility(cartPageEmptyMessage, 20);
            Assert.assertEquals(cartPageEmptyMessage.getText().trim(), "Your shopping cart is empty.");
            log.info("Empty cart message is displayed ..");


        } catch (Exception e) {
            e.printStackTrace();
            log.error("failed due to :::" + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @Step("validate Store info footer")
    public void FooterStoreInfo() {
        String[] ExpList = new String[]
                {"Selenium Framework, Research Triangle Park, North Carolina, USA", "Call us now: (347) 466-7432", "Email: support@seleniumframework.com"};

        try {
            waitForVisibility(footerStoreInfoHeader, 20);
            Assert.assertEquals(footerStoreInfoHeader.getText().trim(), "Store information");
            log.info("Footer header is validated ...");

            FooterDetailListValidation(footerStoreInfoDetails, ExpList);


        } catch (Exception e) {
            e.printStackTrace();
            log.error("failed due to :::" + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @Step("validate search product with both positive and negative value")
    public void searchProduct(String whatToSearch) {


        try {
            waitForVisibility(searchBoxDash, 20);
            searchBoxDash.sendKeys(whatToSearch);
            Thread.sleep(2000);
            count = searchResultList.size();
            log.info("no. of product displayed in dashboard .." + count);
            if (count > 1) {
                searchResultList.get(0).click();
            } else {
                log.error("no product displayed in the page");
                Assert.fail();
            }
            waitForVisibility(popUpProductName, 20);
            log.info("product name from cart page is .." + popUpProductName.getText().trim());
            Assert.assertTrue(!popUpProductName.getText().trim().isEmpty());

            log.info("product price from cart page is .." + popUpProductPrice.getText().trim());
            Assert.assertTrue(!popUpProductPrice.getText().trim().isEmpty());

        } catch (Exception e) {
            e.printStackTrace();
            log.error("failed due to :::" + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }


}

