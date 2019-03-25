package PageObjects;

import Abstracts.POAbstract;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PODefault extends POAbstract {
    private final String URL_MATCH = "https://www.bitpanda.com";

    /**
     * Log in
     */
    @FindBy(xpath = "//a[@class='btn btn--outline nav__primary__btn' and text()='Log in']")
    private WebElement buttonLogIn;
    /**
     * Sign up
     */
    @FindBy(xpath = "//a[@class='btn btn--solid nav__primary__btn' and text()='Sign up']")
    private WebElement buttonSignUp;

    public PODefault(WebDriver driver) throws Exception {
        super(driver);
        checkURL(URL_MATCH);
    }

    public void openLoginPage() {
        webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(buttonLogIn)).click();
    }

    public PORegistration openRegistrationPage() throws Exception {
        webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(buttonSignUp)).click();
        return new PORegistration(driver);
    }

    public String getURL_MATCH() {
        return URL_MATCH;
    }
}
