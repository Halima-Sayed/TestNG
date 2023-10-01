package class03;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class dataDrivenTesting {
      //Test Scenario:
    //navigate to syntax HRMS
    //login into the webiste using the following credentials and check for correct errors
    //a.username ="Admin"  , password="12345"  ---> Error Message ="Invalid credentials"
    //b.username= "ABCD"   , password ="Hum@nhrm123" -->Error Message ="Invalid credentials"
    //c.username= ""   ,   password ="Hum@nhrm123"   -->Error Message ="Username cannot be empty"
    //d.username= "Admin" ,password= ""  -->Error Message= "Password cannot be empty"
      @DataProvider(name = "invalidCredentials")
      public Object[][] data() {
          Object[][] loginData = {
                  {"Admin", "12345", "Invalid credentials"},
                  {"ABCD", "Hum@nhrm123", "Invalid credentials"},
                  {"Admin", "", "Password cannot be empty"},
                  {"", "Hum@nhrm123", "Username cannot be empty"}
          };
          return loginData;
      }
      public static WebDriver driver;

      @BeforeMethod
    public void OpenBrowser(){
          driver=new ChromeDriver();
          driver.get("http://hrm.syntaxtechs.net/humanresources/symfony/web/index.php/auth/login");
          driver.manage().window().maximize();
          driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
      }
      @AfterMethod
    public void closeBrowser(){
          driver.quit();
      }
      @Test(dataProvider ="invalidCredentials" )
    public void loginWithInvalidCredentials(String user,String pass,String expectedMsg){
          //send username
          WebElement userName = driver.findElement(By.xpath("//input[@name='txtUsername']"));
          userName.sendKeys(user);
          //send password
          WebElement password=driver.findElement(By.xpath("//input[@id='txtPassword']"));
          password.sendKeys(pass);
          //click login
          WebElement loginBtn=driver.findElement(By.xpath("//input[@id='btnLogin']"));
          loginBtn.click();

          //get the element  msg invalid credentials
          WebElement errorMsg = driver.findElement(By.id("spanMessage"));
          //extract the error msh
          String actualErrorMessage = errorMsg.getText();
          Assert.assertEquals(actualErrorMessage,expectedMsg);
      }
}
