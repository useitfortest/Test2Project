package PageObjects;

import Abstracts.POAbstract;
import org.openqa.selenium.WebDriver;

public class POHome extends POAbstract {
    private final String URL_MATCH = "https://web.bitpanda.com/home";

    public POHome(WebDriver driver) throws Exception {
        super(driver);
        checkURL(URL_MATCH);
    }
}
