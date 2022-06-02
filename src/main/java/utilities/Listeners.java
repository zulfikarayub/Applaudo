package utilities;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class Listeners extends BaseClass implements ITestListener {


    public void onTestStart(ITestResult result) {
        // TODO Auto-generated method stub


    }

    public void onTestSuccess(ITestResult result) {
        // TODO Auto-generated method stub
//            closeDriver();
    }

    public void onTestFailure(ITestResult result) {
        // TODO Auto-generated method stub

        WebDriver driver = null;
        String testMethodName = result.getMethod().getMethodName();

        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
        } catch (Exception e) {

        }
        try {
            log.info("failure capture screenshot" + testMethodName);
            getScreenShotPath(testMethodName, driver);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void onTestSkipped(ITestResult result) {
        // TODO Auto-generated method stub
//            closeDriver();

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub

    }

    public void onStart(ITestContext context) {
        // TODO Auto-generated method stub

    }

    public void onFinish(ITestContext context) {
        // TODO Auto-generated method stub

    }

}
