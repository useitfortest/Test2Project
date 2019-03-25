package Tests;

import Abstracts.TestAbstract;
import DataProviders.DataProviderClass;
import ExtObjects.User;
import PageObjects.PODefault;
import PageObjects.POHome;
import PageObjects.POLogin;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

public class TestLoginPage extends TestAbstract {
    private PODefault poDefault;
    private POLogin poLogin;
    private WebDriver driver;

    @BeforeClass
    @Parameters("browser")
    public void openHomePage(String browser) throws Exception {
        if (browser.equals("firefox")) {
            driver = getFirefoxDriver();
        } else {
            driver = getChromeDriver();
        }
        driver.get(dafault_url);
        driver.manage().window().maximize();
        poDefault = new PODefault(driver);
    }

    @BeforeMethod
    public void openDefaultPage() {
        driver.get(dafault_url);
    }

    @Test(priority = 0,
            dataProvider = "getTestUsers",
            dataProviderClass = DataProviderClass.class,
            enabled = true
    )
    public void logIntoWithCorrectCredentials(User user) throws Exception {
        poDefault.openLoginPage();
        poLogin = new POLogin(driver);
        poLogin.logIntoBitPanda(user);
        //Exception will be here if the page is not correct (it will be not correct because of captcha)
        POHome poHome = new POHome(driver);
        Reporter.log("<br>The user is logged into resource with correct credentials.");
    }

    @Test(priority = 1,
            dataProvider = "getTestUsers",
            dataProviderClass = DataProviderClass.class,
            enabled = true
    )
    public void logIntoWithIncorrectCredentials(User user) throws Exception {
        poDefault.openLoginPage();
        poLogin = new POLogin(driver);
        poLogin.logIntoBitPanda(user);
        try {
            Assert.assertTrue(poLogin.isCredentialsWarning());
        } catch (AssertionError ae) {
            Reporter.log("<br>WARNING: Credentials error is not displayed for incorrect credentials.");
            Reporter.log(poLogin.takeScreenshot());
            throw ae;
        }
    }

    @Test(priority = 2,
            dataProvider = "getTestUsers",
            dataProviderClass = DataProviderClass.class,
            enabled = true
    )
    public void logIntoWithIncorrectPassword(User user) throws Exception {
        poDefault.openLoginPage();
        poLogin = new POLogin(driver);
        poLogin.logIntoBitPanda(user);
        try {
            Assert.assertTrue(poLogin.isCredentialsWarning());
        } catch (AssertionError ae) {
            Reporter.log("<br>WARNING: Credentials error is not displayed for an incorrect password.");
            Reporter.log(poLogin.takeScreenshot());
            throw ae;
        }
    }

    @Test(priority = 3,
            dataProvider = "getTestUsers",
            dataProviderClass = DataProviderClass.class,
            enabled = true
    )
    public void logIntoWithIncorrectEmail(User user) throws Exception {
        poDefault.openLoginPage();
        poLogin = new POLogin(driver);
        poLogin.logIntoBitPanda(user);
        try {
            Assert.assertTrue(poLogin.isCredentialsWarning());
        } catch (AssertionError ae) {
            Reporter.log("<br>WARNING: Credentials error is not displayed for an incorrect email.");
            Reporter.log(poLogin.takeScreenshot());
            throw ae;
        }
    }

    @Test(priority = 4,
            enabled = true
    )
    public void returnFromLoginPage() throws Exception {
        poDefault.openLoginPage();
        poLogin = new POLogin(driver);
        poLogin.goToDefaultPage();
        String url = driver.getCurrentUrl();
        try {
            Assert.assertTrue(url.equals(poDefault.getURL_MATCH()+"/en"));
        } catch (AssertionError ae) {
            Reporter.log("<br>WARNING: The user is not redirected to the default page.");
            Reporter.log(poLogin.takeScreenshot());
            throw ae;
        }
    }

    @AfterClass
    public void returnToHome() {
        driver.close();
    }

}
