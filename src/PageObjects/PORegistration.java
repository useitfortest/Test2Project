package PageObjects;

import Abstracts.POAbstract;
import ExtObjects.User;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Reporter;

import java.util.ArrayList;
import java.util.List;

public class PORegistration extends POAbstract {
    private String URL_MATCH = "https://web.bitpanda.com/user/register";
    private List<Boolean> toggles = new ArrayList<>();

    /**
     * First name
     */
    @FindBy(xpath = "//input[@id='forename']")
    private WebElement fieldFirstname;

    /**
     * Surname
     */
    @FindBy(xpath = "//input[@id='surname']")
    private WebElement fieldSurname;

    /**
     * Email
     */
    @FindBy(xpath = "//input[@id='email']")
    private WebElement fieldEmail;

    /**
     * Password
     */
    @FindBy(xpath = "//input[@id='password']")
    private WebElement fieldPassword;

    /**
     * Country
     */
    @FindBy(xpath = "//div[@class='styled-select']")
    private WebElement fieldCountry;

    /**
     * Toggle buttons (off)
     */
    @FindBy(xpath = "//button[@class='toggle-btn toggle-btn-off']")
    private List<WebElement> togglesOff;

    /**
     * Toggle buttons (on)
     */
    @FindBy(xpath = "//button[@class='toggle-btn toggle-btn-on']")
    private List<WebElement> togglesOn;

    /**
     * Create button
     */
    @FindBy(xpath = "//button[@class='btn btn-primary btn-block btn-lg mt-40']")
    private WebElement buttonCreate;

    /**
     * Terms and conditions
     */
    @FindBy(xpath = "//a[text()='I have read and agree to the Terms and Conditions.']")
    private WebElement linkTerms;

    /**
     * Privacy Policy
     */
    @FindBy(xpath = "//a[text()='I have read and agree to the Privacy Policy.']")
    private WebElement linkPolicy;

    /**
     * Privacy Policy
     */
    @FindBy(xpath = "//a[text()='Already have an account?']")
    private WebElement linkHaveAnAccount;

    /**
     * Link 'here'
     */
    @FindBy(xpath = "//a[text()='here']")
    private WebElement linkHere;

    /**
     * Alert popup
     */
    @FindBy(xpath = "//div[@aria-live='polite' and @role='alertdialog']")
    private WebElement alertPopup;

    /**
     * No country message
     */
    @FindBy(xpath = "//div[@class='ng-option ng-option-disabled ng-star-inserted']")
    private WebElement noCountry;

    /**
     * Clear country search result
     */
    @FindBy(xpath = "//span[@class='ng-clear-wrapper ng-star-inserted']")
    private WebElement clearAll;

    /**
     * Password eye
     */
    @FindBy(xpath = "//span[@class='input-group-addon']")
    private WebElement passwordEye;

    /**
     * Field validation
     */
    @FindBy(xpath = "//span[@class='form-error ng-star-inserted']")
    private List<WebElement> nameValidators;
    //0 - The forename must contain only alphabetical characters, dots, commas, apostrophes and spaces
    //1 - The surname must contain only alphabetical characters, dots, commas, apostrophes and spaces

    @FindBy(xpath = "//span[@class='form-error ng-star-inserted']")
    private WebElement emailValidator;
    //2 - Email is required

    public PORegistration(WebDriver driver) throws Exception {
        super(driver);
        checkURL(URL_MATCH);
        toggles.add(false); //Terms and Conditions
        toggles.add(false); //Privacy Policy
        toggles.add(false); //Bitpanda Newsletter
    }

    /**
     * Enter credentials
     *
     * @param user
     */
    public void enterUser(User user) throws Exception {
        String error = "";
        webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(fieldFirstname)).sendKeys(user.getFirstName(), Keys.TAB);
        String result = fieldFirstname.getAttribute("value");
        if(!result.equals(user.getFirstName())){
            error = error+"<br>WARNING: First name is not correct. Expected value: " + user.getFirstName() + ". Actual value: "+result+".";
        }
        webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(fieldSurname)).sendKeys(user.getLastName(), Keys.TAB);
        result = fieldSurname.getAttribute("value");
        if (!result.equals(user.getLastName())) {
            error = error+"<br>WARNING: Surname is not correct. Expected value: " + user.getLastName() + ". Actual value: "+result+".";
        }
        webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(fieldEmail)).sendKeys(user.getEmail(), Keys.TAB);
        result = fieldEmail.getAttribute("value");
        if (!result.equals(user.getEmail())) {
            error = error+"<br>WARNING: Email is not correct. Expected value: " + user.getEmail() + ". Actual value: "+result+".";
        }
        webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(fieldPassword)).sendKeys(user.getPassword(), Keys.TAB);
        result = fieldPassword.getAttribute("type");
        if (!result.equals("password")) {
            error = error+"<br>WARNING: Password is visible.";
        }
        webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(passwordEye)).click();
        result = fieldPassword.getAttribute("type");
        if (!result.equals("text")) {
            error = error+"<br>WARNING: Password is not visible.";
        }
        result = fieldPassword.getAttribute("value");
        if (!result.equals(user.getPassword())) {
            error = error+"<br>WARNING: Password is not correct. Expected value: " + user.getPassword() + ". Actual value: "+result+".";
        }
        webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(fieldCountry)).click();
        poAction.sendKeys(user.getCountry(), Keys.ENTER).perform();
        result = fieldCountry.getText().trim();
        if (!result.equals(user.getCountry())) {
            error = error+"<br>WARNING: Country is not correct. Expected value: " + user.getCountry() + ". Actual value: "+result+".";
        }
        if (!error.isEmpty()) {
            Reporter.log(error);
            Reporter.log(takeScreenshot());
            throw new IllegalStateException(error);
        }
    }

    /**
     * Click on Create Account & enter captcha
     * @param isAccountIncorrect
     * @param isCapture this flag is required to test toggles (should be false)
     * @return
     * @throws Exception
     */
    public PORegistered createAccount(boolean isAccountIncorrect, boolean isCapture) throws Exception {
        webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(buttonCreate)).click();
        if (isCapture) {
            //TODO get captcha for QA aims (with 1 element for example)
            poAction.pause(3000).perform();
            //Select first image
            poAction.sendKeys(Keys.ARROW_UP, Keys.ENTER).perform();
            //Verify
            poAction.sendKeys(Keys.ARROW_DOWN, Keys.ENTER).perform();
        }
        if (isAccountIncorrect) {
            return null;
        }
        return new PORegistered(driver);
    }

    /**
     * Go to terms & conditions
     */
    public void goToTermsAndConditions() {
        webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(linkTerms)).click();
    }

    /**
     * Go to privacy policy
     */
    public void goToPrivacyPolicy() {
        webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(linkPolicy)).click();
    }

    /**
     * Go to login page (already have an account)
     */
    public void goToLoginPage() {
        webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(linkHaveAnAccount)).click();
    }

    /**
     * Open Accepted Documents for verification
     */
    public void goToAcceptanceDocuments() {
        webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(linkHere)).click();
    }

    /**
     * Just ensure correct text is displayed in the search result (if country was not found)
     * @return
     */
    public boolean checkIncorrectCountryText() throws Exception {
        String expected_text = "No items found";
        String actual_text = webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(noCountry)).getText().trim();
        if (actual_text.equals(expected_text)) {
            return true;
        }
        Reporter.log("<br>Error text is not found. Expected value: "+expected_text+". Actual value: "+actual_text+".");
        Reporter.log(takeScreenshot());
        return false;
    }

    /**
     * Validate error for the firstname field
     * @return
     */
    public boolean checkIncorrectFirstnameText() throws Exception {
        String expected_text = "The forename must contain only alphabetical characters, dots, commas, apostrophes and spaces";
        String actual_text = webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(nameValidators.get(0))).getText().trim();
        if (actual_text.equals(expected_text)) {
            return true;
        }
        Reporter.log("<br>Error text is not found. Expected value: "+expected_text+". Actual value: "+actual_text+".");
        Reporter.log(takeScreenshot());
        return false;
    }

    /**
     * Validate error for the surname field
     * @return
     */
    public boolean checkIncorrectSurnameText() throws Exception {
        String expected_text = "The forename must contain only alphabetical characters, dots, commas, apostrophes and spaces";
        String actual_text = webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(nameValidators.get(1))).getText().trim();
        if (actual_text.equals(expected_text)) {
            return true;
        }
        Reporter.log("<br>Error text is not found. Expected value: "+expected_text+". Actual value: "+actual_text+".");
        Reporter.log(takeScreenshot());
        return false;
    }

    /**
     * Validate error for the email field
     * @return
     */
    public boolean checkIncorrectEmailText() throws Exception {
        String expected_text = "Email is required";
        String actual_text = webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(emailValidator)).getText().trim();
        if (actual_text.equals(expected_text)) {
            return true;
        }
        Reporter.log("<br>Error text is not found. Expected value: "+expected_text+". Actual value: "+actual_text+".");
        Reporter.log(takeScreenshot());
        return false;
    }

    /**
     * Validate error for the email field
     * @return
     */
    public boolean checkIncorrectTogglesText() throws Exception {
        String expected_text = "You have to accept our Terms and Conditions and Privacy Policy in order to use Bitpanda";
        String actual_text = webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(alertPopup)).getText().trim();
        if (actual_text.equals(expected_text)) {
            return true;
        }
        Reporter.log("<br>Popup error text is not found. Expected value: "+expected_text+". Actual value: "+actual_text+".");
        Reporter.log(takeScreenshot());
        return false;
    }

    /**
     * Clear search result
     */
    public void clearCountrySearchResult() throws Exception {
        webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(clearAll)).click();
        String result = fieldCountry.getText().trim();
        if (!result.isEmpty()) {
            String error = "<br>WARNING: Country is not cleared. Actual value: "+result+".";
            Reporter.log(error);
            Reporter.log(takeScreenshot());
            throw new IllegalStateException(error);
        }
    }

    /**
     * Toggle on appropriate values
     *
     * @param isTerms      Terms and Conditions
     * @param isPolicy     Privacy Policy
     * @param isNewsletter Bitpanda Newsletter
     */
    public void setToggles(boolean isTerms, boolean isPolicy, boolean isNewsletter) {
        if (!toggles.get(0) && !toggles.get(1) && !toggles.get(2)) { //Just for further tests
            int index = 0;
            if (isTerms) {
                webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(togglesOff.get(index))).click();
            } else {
                index++;
            }
            if (isPolicy) {
                webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(togglesOff.get(index))).click();
            } else {
                index++;
            }
            if (isNewsletter) {
                webDriverExplicitWait.until(ExpectedConditions.elementToBeClickable(togglesOff.get(index))).click();
            }
        }
    }
}
