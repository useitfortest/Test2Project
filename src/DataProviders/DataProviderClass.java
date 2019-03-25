package DataProviders;

import ExtObjects.User;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.UUID;

public class DataProviderClass {
    @DataProvider
    public static Object[][] getTestUsers(Method m) {
        User tmpUser = new User();
        //Use correct credentials by default
        tmpUser.setPassword("correct_password");
        tmpUser.setEmail("correct_user@mailinator.com");
        if (m.getName().equalsIgnoreCase("logIntoWithCorrectCredentials")) {
            return new Object[][]{
                    {tmpUser}
            };
        } else if (m.getName().equalsIgnoreCase("logIntoWithIncorrectPassword")) {
            tmpUser.setPassword(UUID.randomUUID().toString().replace("-", ""));
            return new Object[][]{
                    {tmpUser}
            };
        } else if (m.getName().equalsIgnoreCase("logIntoWithIncorrectEmail")) {
            tmpUser.setEmail(UUID.randomUUID().toString().replace("-", "")+"@mailinator.com");
            return new Object[][]{
                    {tmpUser}
            };
        } else if (m.getName().equalsIgnoreCase("logIntoWithIncorrectCredentials")) {
            tmpUser.setPassword(UUID.randomUUID().toString().replace("-", ""));
            tmpUser.setEmail(UUID.randomUUID().toString().replace("-", "")+"@mailinator.com");
            return new Object[][]{
                    {tmpUser}
            };
        } else if (m.getName().equalsIgnoreCase("logIntoWithExpiredCredentials")) {
            tmpUser.setPassword("exp_password");
            tmpUser.setEmail("exp_email@mailinator.com");
            return new Object[][]{
                    {tmpUser}
            };
        } else if (m.getName().equalsIgnoreCase("createExistingAccount")) {
            tmpUser.setPassword("existing_password");
            tmpUser.setEmail("existing_email@mailinator.com");
            tmpUser.setFirstName("Existingname");
            tmpUser.setLastName("Existingsurname");
            tmpUser.setCountry("Russian Federation");
            return new Object[][]{
                    {tmpUser}
            };
        } else if (m.getName().equalsIgnoreCase("createNewAccount")) {
            tmpUser.setPassword(UUID.randomUUID().toString().replace("-", ""));
            tmpUser.setEmail(UUID.randomUUID().toString().replace("-", "")+"@mailinator.com");
            tmpUser.setFirstName(UUID.randomUUID().toString().replace("-", "").replaceAll("[0-9]",""));
            tmpUser.setLastName(UUID.randomUUID().toString().replace("-", "").replaceAll("[0-9]",""));
            tmpUser.setCountry("Russian Federation");
            return new Object[][]{
                    {tmpUser}
            };
        } else if (m.getName().equalsIgnoreCase("createNewAccountIncorrectCountry")) {
            tmpUser.setPassword(UUID.randomUUID().toString().replace("-", ""));
            tmpUser.setEmail(UUID.randomUUID().toString().replace("-", "")+"@mailinator.com");
            tmpUser.setFirstName(UUID.randomUUID().toString().replace("-", "").replaceAll("[0-9]",""));
            tmpUser.setLastName(UUID.randomUUID().toString().replace("-", "").replaceAll("[0-9]",""));
            tmpUser.setCountry(UUID.randomUUID().toString().replace("-", ""));
            return new Object[][]{
                    {tmpUser}
            };
        } else if (m.getName().equalsIgnoreCase("createNewAccountIncorrectName")) {
            tmpUser.setPassword(UUID.randomUUID().toString().replace("-", ""));
            tmpUser.setEmail(UUID.randomUUID().toString().replace("-", "")+"@mailinator.com");
            tmpUser.setFirstName(UUID.randomUUID().toString().replace("-", ""));
            tmpUser.setLastName(UUID.randomUUID().toString().replace("-", ""));
            tmpUser.setCountry("Russian Federation");
            return new Object[][]{
                    {tmpUser}
            };
        } else if (m.getName().equalsIgnoreCase("createNewAccountIncorrectEmail")) {
            tmpUser.setPassword(UUID.randomUUID().toString().replace("-", ""));
            tmpUser.setEmail(UUID.randomUUID().toString().replace("-", ""));
            tmpUser.setFirstName(UUID.randomUUID().toString().replace("-", "").replaceAll("[0-9]",""));
            tmpUser.setLastName(UUID.randomUUID().toString().replace("-", "").replaceAll("[0-9]",""));
            tmpUser.setCountry("Russian Federation");
            return new Object[][]{
                    {tmpUser}
            };
        } else if (m.getName().equalsIgnoreCase("createNewAccountCheckToggles")) {
            tmpUser.setPassword(UUID.randomUUID().toString().replace("-", ""));
            tmpUser.setEmail(UUID.randomUUID().toString().replace("-", "")+"@mailinator.com");
            tmpUser.setFirstName(UUID.randomUUID().toString().replace("-", "").replaceAll("[0-9]",""));
            tmpUser.setLastName(UUID.randomUUID().toString().replace("-", "").replaceAll("[0-9]",""));
            tmpUser.setCountry("Russian Federation");
            return new Object[][]{
                    {tmpUser}
            };
        }
        return null;
    }
}
