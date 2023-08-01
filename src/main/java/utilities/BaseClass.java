package utilities;

import com.aventstack.extentreports.ExtentTest;
import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.automationpractice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class BaseClass extends TestBench {
    public static int count;
    public static String UiProductName, UiProductPrice;
    public static WebDriver driver;
    public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<>();
    public static ExtentTest test;
    public static String runtype;
    public static Logger log = LogManager.getLogger(BaseClass.class);
    public static Faker faker = new Faker();
    public static automationpractice automationpractice;
    public static JavascriptExecutor js;


    public static synchronized WebDriver getWebDriver() {
        return tdriver.get();
    }


    @AfterMethod
    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }


    public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = null;
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWaitInSec));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("failed due to :::" + e.getMessage());
            org.testng.Assert.fail(e.getMessage());

        }

        return wait.until(ExpectedConditions.visibilityOf(element));
    }


    @BeforeMethod(alwaysRun = true)
    public void initTestMethod() {
        driver = geTDriver();
        automationpractice = new automationpractice(driver);
        js = (JavascriptExecutor) driver;
    }

    public WebDriver geTDriver() {
        try {
            runtype = ConfigReader.getProperty("runtype");
            if (runtype.equalsIgnoreCase("local")) {
                if (driver == null) {
                    switch (ConfigReader.getProperty("browser")) {
                        case "chrome":
                            WebDriverManager.chromedriver().setup();
                            setDriver(new ChromeDriver());
                            break;
                        case "firefox":
                            WebDriverManager.firefoxdriver().setup();
                            setDriver(new FirefoxDriver());
                            break;
                        case "ie":
                            WebDriverManager.iedriver().setup();
                            setDriver(new InternetExplorerDriver());
                            break;
                        case "safari":
                            WebDriverManager.getInstance(SafariDriver.class).setup();
                            setDriver(new SafariDriver());
                            break;
                        case "headless-chrome":
                            WebDriverManager.chromedriver().setup();
                            setDriver(new ChromeDriver(new ChromeOptions().setHeadless(true)));
                            break;
                    }

                }

            } else if (runtype.equalsIgnoreCase("remote")) {
                DesiredCapabilities caps = new DesiredCapabilities();

                caps.setCapability("os", "Windows");
                caps.setCapability("os_version", "10");
                caps.setCapability("browser", "Chrome");
                caps.setCapability("browser_version", "latest");
                caps.setCapability("browserstack.local", "false");
                caps.setCapability("browserstack.selenium_version", "4.0.0");
                URL browserStackUrl = new URL("");
                setDriver(new RemoteWebDriver(browserStackUrl, caps));
                //Implicit wait for 30 seconds
                //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver = getDriver();
        tdriver.set(driver);
        return getWebDriver();
    }


    public void visit(String url) {
        getDriver().get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

//    @Attachment(value = "Page screenshot", type = "image/png")
//    public String getScreenShotPath(String testCaseName, WebDriver driver1) throws IOException {
//        log.info("screen shot code is called ....");
//        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//    }

    public String getScreenShot(String testCaseName, WebDriver driverScr) throws IOException {

        driverScr = driver;

        String screenshotPath = null;

        try {

            //take screenshot and save it in a file

            File sourceFile = ((TakesScreenshot) driverScr).getScreenshotAs(OutputType.FILE);

            //copy the file to the required path

            File destinationFile = new File(System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png");

            FileHandler.copy(sourceFile, destinationFile);

            String[] relativePath = destinationFile.toString().split("reports");

            screenshotPath = ".\\" + relativePath[1];

        } catch (Exception e) {

            System.out.println("Failure to take screenshot " + e);

        }

        return screenshotPath;

    }

    public void FooterDetailListValidation(List<WebElement> elementname, String[] SubMenuName) {

        ArrayList<String> ActualMenu = new ArrayList<String>();
        ArrayList<String> ExpectedMenu = new ArrayList<String>();
        ExpectedMenu.addAll(Arrays.asList(SubMenuName));
        int count;
        String actualText;

        try {
            count = elementname.size();
            log.info("total count of list is.." + count);

            for (WebElement headerText : elementname) {
                actualText = headerText.getText().trim();

                ActualMenu.add(actualText);

            }
            log.info("Actual value for  UI " + ActualMenu);
            Assert.assertEquals(ActualMenu, ExpectedMenu);


        } catch (Exception e) {
            e.printStackTrace();
            log.error("The actual value is " + ActualMenu);
            log.error("The Expected value is " + ExpectedMenu);
            log.error("failed due to :::" + e.getMessage());
            Assert.fail(e.getMessage());
        }


    }


}
