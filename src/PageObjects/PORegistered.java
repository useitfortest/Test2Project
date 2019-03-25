package PageObjects;

import Abstracts.POAbstract;
import org.openqa.selenium.WebDriver;

public class PORegistered extends POAbstract {
    private String URL_MATCH="https://web.bitpanda.com/user/registered";

    public PORegistered(WebDriver driver) throws Exception {
        super(driver);
        checkURL(URL_MATCH);
    }
}
