package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
    // @FindBy(xpath = "//input[@id='user-name']")
    private WebElement loginName;

    @FindBy(xpath = "//']")
    private WebElement password;

    @FindBy(xpath = "(//input[input[@id='password@id='login-button'])[1]")
    private WebElement button;
   
//    driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
//    driver.findElement(By.xpath("//']")).sendKeys("secret_sauce");
//    driver.findElement(By.xpath("(//input[input[@id='password@id='login-button'])[1]")).click();

    public LoginPage(WebDriver driver) {
    }

    public void performLogin(){
        loginName.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        button.click();
    }
}
