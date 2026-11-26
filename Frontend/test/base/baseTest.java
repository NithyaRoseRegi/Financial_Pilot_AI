package frontend.base;

import com.financialpilot.automation.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    protected WebDriver driver;

    @BeforeSuite
    public void suiteSetup() {

        System.out.println("===== Financial Pilot AI Automation Suite Started =====");

    }

    @BeforeMethod
    public void setUp() {

        driver = DriverFactory.createDriver();

    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }

    }

    @AfterSuite
    public void suiteTeardown() {

        System.out.println("===== Financial Pilot AI Automation Suite Finished =====");

    }
}