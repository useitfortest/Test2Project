package Abstracts;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class TestAbstract {
    public final String dafault_url = "https://www.bitpanda.com/";
    private final int implicitWaiterInSeconds = 5;

    public TestAbstract() {
        //Set system properties (fix paths as required)
        System.setProperty("webdriver.gecko.driver", "C:\\drivers\\geckodriver-v0.24.0-win32\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver_win32\\chromedriver.exe");
    }

    public ChromeDriver getChromeDriver() {
        ChromeDriver chromeDriver = new ChromeDriver();
        chromeDriver.manage().timeouts().implicitlyWait(implicitWaiterInSeconds, TimeUnit.SECONDS);
        return chromeDriver;
    }

    public FirefoxDriver getFirefoxDriver() {
        FirefoxDriver firefoxDriver = new FirefoxDriver();
        firefoxDriver.manage().timeouts().implicitlyWait(implicitWaiterInSeconds, TimeUnit.SECONDS);
        return firefoxDriver;
    }
}
