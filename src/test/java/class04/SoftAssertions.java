package class04;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.CommonMethods;

public class SoftAssertions extends CommonMethods {
    //login into the syntax HRMS APP
    //verify that the Welcome message is displayed
    //Verify that the message is "Welcome Admin"
    //pre condition


    @BeforeMethod(alwaysRun = true)
    public void OpenBrowserAndNavigate() {
        openBrowserAndNavigateToURL("http://hrm.syntaxtechs.net/humanresources/symfony/web/index.php/auth/login","chrome");
    }

    //post condition
    @AfterMethod(alwaysRun = true)
    public void quitBrowser() {
        //  driver.quit();
    }

    @Test(groups = "regression")
    public void verifyTheWelcomeErrorMessage() {
        //send username
        WebElement userName = driver.findElement(By.xpath("//input[@name='txtUsername']"));
        userName.sendKeys("admin");
        //send password
        WebElement password = driver.findElement(By.xpath("//input[@id='txtPassword']"));
        password.sendKeys("Hum@nhrm123");
        //click login
        WebElement loginBtn = driver.findElement(By.xpath("//input[@id='btnLogin']"));
        loginBtn.click();

        //get the message
      WebElement welcomeMsg=driver.findElement(By.id("welcome"));
      String welcomeText=welcomeMsg.getText();

      //verify welcomeMsg is displayed
        boolean isDisplayed=welcomeMsg.isDisplayed();
        SoftAssert soft=new SoftAssert();
        soft.assertTrue(isDisplayed);

        //verify the message is welcome admin
        String actualWelcomeText=welcomeMsg.getText();
        String expectedWelcomeText="Welcome Admin";
        soft.assertEquals(actualWelcomeText,expectedWelcomeText);
        //assert all the assertions that have been made
        soft.assertAll();
    }
}