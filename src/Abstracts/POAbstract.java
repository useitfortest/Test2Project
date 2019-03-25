package Abstracts;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.NoSuchElementException;

public class POAbstract {
    public WebDriver driver;
    public WebDriverWait webDriverExplicitWait;
    public final int timeOutInSeconds = 5;
    public final int sleepInMillis = 150;
    public Actions poAction;

    public POAbstract(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.driver = driver;
        webDriverExplicitWait = new WebDriverWait(this.driver, timeOutInSeconds, sleepInMillis);
        poAction = new Actions(driver);
        poAction.build();
    }

    public void checkURL(String URL_MATCH) throws IllegalStateException, Exception {
        //Check if page is correct
        if (!driver.getCurrentUrl().contains(URL_MATCH)) {
            String error = "WARNING: Page is not correct. URL should contain '" + URL_MATCH + "'.";
            Reporter.log(error);
            Reporter.log(takeScreenshot());
            throw new IllegalStateException(error);
        }
    }

    /**
     * Check if element exists
     *
     * @param by
     * @return
     */
    public boolean isElementPresented(By by) {
        try {
            if (driver.findElements(by).size() != 0) {
                return true;
            } else {
                return false;
            }
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * @return
     * @throws Exception
     */
    public String takeScreenshot() throws Exception {
        String timeStamp;
        File screenShotName;
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        //The below method will save the screen shot in d drive with name "screenshot.png"
        timeStamp = new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(Calendar.getInstance().getTime());
        String projectDir = System.getProperty("user.dir");
        String locImageDir = "Images" + File.separator;
        String imageDir = File.separator + "out" + File.separator + "Reports" + File.separator + locImageDir;
        screenShotName = new File(projectDir + imageDir + timeStamp + ".png");
        FileUtils.copyFile(scrFile, screenShotName);
        String path = "<br><a href=\"" + locImageDir + screenShotName.getName() + "\"><img src=\""
                + locImageDir + screenShotName.getName() + "\" alt=\"\" width=\"25%\" height=\"25%\"/></a>";
        return path;
    }
}
