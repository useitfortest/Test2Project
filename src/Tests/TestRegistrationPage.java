package Tests;

import Abstracts.TestAbstract;
import DataProviders.DataProviderClass;
import ExtObjects.User;
import PageObjects.PODefault;
import PageObjects.PORegistration;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

public class TestRegistrationPage extends TestAbstract {
    private PODefault poDefault;
    private PORegistration poRegistration;
    private WebDriver driver;

    @BeforeClass
    @Parameters("browser")
    public void openDefaultPage(String browser) throws Exception {
        if (browser.equals("firefox")) {
            driver = getFirefoxDriver();
        } else {
            driver = getChromeDriver();
        }
        driver.get(dafault_url);
        driver.manage().window().maximize();
        poDefault = new PODefault(driver);
    }

    @AfterMethod
    public void openDefaultPage() {
        driver.get(dafault_url);
    }

    @Test(priority = 0,
            dataProvider = "getTestUsers",
            dataProviderClass = DataProviderClass.class,
            enabled = true
    )
    public void createNewAccount(User user) throws Exception {
        poRegistration = poDefault.openRegistrationPage();
        poRegistration.enterUser(user);
        poRegistration.setToggles(true,true,false);
        //Exception will be here if the page is not correct (it will be not correct because of captcha)
        poRegistration.createAccount(false,true);
        Reporter.log("<br>Account is created. Email was sent to: "+user.getEmail());
    }

    @Test(priority = 1,
            dataProvider = "getTestUsers",
            dataProviderClass = DataProviderClass.class,
            enabled = true
    )
    public void createNewAccountIncorrectCountry(User user) throws Exception {
        poRegistration = poDefault.openRegistrationPage();
        poRegistration.enterUser(user);
        Assert.assertTrue(poRegistration.checkIncorrectCountryText());
        //Exception will be here if country is not removed
        poRegistration.clearCountrySearchResult();
        Reporter.log("<br>Incorrect country is removed");
    }

    @Test(priority = 2,
            dataProvider = "getTestUsers",
            dataProviderClass = DataProviderClass.class,
            enabled = true
    )
    public void createNewAccountIncorrectName(User user) throws Exception {
        poRegistration = poDefault.openRegistrationPage();
        poRegistration.enterUser(user);
        poRegistration.setToggles(true,true,false);
        poRegistration.createAccount(true, true);
        //IndexOutOfBoundsException will be here because of captcha
        Assert.assertTrue(poRegistration.checkIncorrectFirstnameText());
        Assert.assertTrue(poRegistration.checkIncorrectSurnameText());
        Reporter.log("<br>Error text for name fields is displayed correctly");
    }

    @Test(priority = 3,
            dataProvider = "getTestUsers",
            dataProviderClass = DataProviderClass.class,
            enabled = true
    )
    public void createNewAccountIncorrectEmail(User user) throws Exception {
        poRegistration = poDefault.openRegistrationPage();
        poRegistration.enterUser(user);
        poRegistration.setToggles(true,true,false);
        poRegistration.createAccount(true, true);
        //TimeoutException will be here because of captcha
        Assert.assertTrue(poRegistration.checkIncorrectEmailText());
        Reporter.log("<br>Error text for the email field is displayed correctly");
    }

    @Test(priority = 4,
            dataProvider = "getTestUsers",
            dataProviderClass = DataProviderClass.class,
            enabled = true
    )
    public void createExistingAccount(User user) throws Exception {
        poRegistration = poDefault.openRegistrationPage();
        poRegistration.enterUser(user);
        poRegistration.setToggles(true,true,false);
        //Exception will be here if the page is not correct (it will be not correct because of captcha)
        poRegistration.createAccount(false,true);
        Reporter.log("<br>Account is created. Email was sent to: "+user.getEmail());
    }

    @Test(priority = 5,
            dataProvider = "getTestUsers",
            dataProviderClass = DataProviderClass.class,
            enabled = true
    )
    public void createNewAccountCheckToggles(User user) throws Exception {
        poRegistration = poDefault.openRegistrationPage();
        poRegistration.enterUser(user);
        poRegistration.setToggles(false,false,false);
        poRegistration.createAccount(true,false);
        Assert.assertTrue(poRegistration.checkIncorrectTogglesText());
        driver.get(driver.getCurrentUrl());
//
        poRegistration.enterUser(user);
        poRegistration.setToggles(false,false,true);
        poRegistration.createAccount(true,false);
        Assert.assertTrue(poRegistration.checkIncorrectTogglesText());
        driver.get(driver.getCurrentUrl());

        poRegistration.enterUser(user);
        poRegistration.setToggles(false,true,false);
        poRegistration.createAccount(true,false);
        Assert.assertTrue(poRegistration.checkIncorrectTogglesText());
        driver.get(driver.getCurrentUrl());
//
        poRegistration.enterUser(user);
        poRegistration.setToggles(true,false,false);
        poRegistration.createAccount(true,false);
        Assert.assertTrue(poRegistration.checkIncorrectTogglesText());
        driver.get(driver.getCurrentUrl());
//
        poRegistration.enterUser(user);
        poRegistration.setToggles(true,false,true);
        poRegistration.createAccount(true,false);
        Assert.assertTrue(poRegistration.checkIncorrectTogglesText());
        driver.get(driver.getCurrentUrl());
//
        poRegistration.enterUser(user);
        poRegistration.setToggles(false,true,true);
        poRegistration.createAccount(true,false);
        Assert.assertTrue(poRegistration.checkIncorrectTogglesText());

        Reporter.log("<br>Popup error appears correctly.");
    }

    @AfterClass
    public void returnToHome() {
        driver.close();
    }
}
