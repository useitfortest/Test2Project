package PageObjects;

import Abstracts.POAbstract;
import ExtObjects.User;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.security.Key;

public class POLogin extends POAbstract {
    private final String URL_MATCH = "https://web.bitpanda.com/user/login";
    private final String INCORRECT_CREDENTIALS=" Login credentials incorrect ";

    /**
     * Email
     */
    @FindBy(xpath = "//input[@id='email' and @name='email']")
    private WebElement fieldEmail;

    /**
     * Password
     */
    @FindBy(xpath = "//input[@id='password' and @name='password']")
    private WebElement fieldPassword;

    /**
     * Log in
     */
    @FindBy(xpath = "//button[text()='Log in']")
    private WebElement buttonLogIn;

    /**
     * Logo
     */
    @FindBy(xpath = "//a[@class='bp-navbar-public-logo']")
    private WebElement logo;

    /**
     * Warning
     */
    @FindBy(xpath = "//div[@class='alert alert-warning ng-star-inserted']")
    private WebElement warning;


    public POLogin(WebDriver driver) throws Exception {
        super(driver);
        checkURL(URL_MATCH);
    }

    /**
     * Log into resource with given user
     *
     * @param user
     */
    public void logIntoBitPanda(User user) {
        webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(fieldEmail)).sendKeys(user.getEmail(), Keys.TAB);
        webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(fieldPassword)).sendKeys(user.getPassword(), Keys.TAB);
        webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(buttonLogIn)).click();
        //TODO get captcha for QA aims (with 1 element for example)
        poAction.pause(3000).perform();
        //Select first image
        poAction.sendKeys(Keys.ARROW_UP, Keys.ENTER).perform();
        //Verify
        poAction.sendKeys(Keys.ARROW_DOWN, Keys.ENTER).perform();
    }

    /**
     * Go to default page
     */
    public void goToDefaultPage() {
        webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(logo)).click();
    }

    /**
     * Check if correct warning is displayed
     * @return
     */
    public boolean isCredentialsWarning() {
        String warning_text = webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(buttonLogIn)).getText().trim();
        return warning_text.equalsIgnoreCase(INCORRECT_CREDENTIALS.trim());
    }
}
