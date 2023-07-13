import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;

import java.util.List;

public class SeleniumRunner {


    private static WebDriver driver;
    private static WebDriverWait wait;
    @BeforeAll
    public static void setUpDriver(){
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

    }

    @BeforeAll
    public static void setUpWait(){
        wait = new WebDriverWait(driver, 15);

    }

    @Test
    public void addToCart() {
        driver.get("https://www.saucedemo.com/");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.performLogin();


//        Add the sauce labs Backpack, Sauce labs Onesie, and Sauce Labs Bolt T-shirt to
//        the cart

        WebElement backpack = driver.findElement(By.xpath("//button[@id='add-to-cart-sauce-labs" +
                    "-backpack']"));
        backpack.click();

        WebElement onesie = driver.findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-onesie']"));
        onesie.click();

        WebElement bolt = driver.findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-bolt-t-shirt']"));
        bolt.click();

//       Validate that three items have been added to the cart on the cart icon.
        WebElement cartNumber = driver.findElement(By.xpath("//span[@class='shopping_cart_badge']"));
        int validateNumber = Integer.parseInt(cartNumber.getText());
        Assertions.assertEquals(validateNumber, 3, "Cart not equal to 3");
//
//       Click on the cart icon.
        WebElement clickIcon = driver.findElement(By.xpath("//a[@class='shopping_cart_link']"));
        clickIcon.click();

//      verify the items on the page match the items you have added to the cart.
        WebElement actualBackpack = driver.findElement(By.xpath("//div[normalize-space()='Sauce" + " Labs Backpack']"));
        String expectedBackpack = "Sauce Labs Backpack";
        Assertions.assertEquals(actualBackpack.getText(), expectedBackpack, "Items do not match");

        WebElement actualOnesie = driver.findElement(By.xpath("//div[normalize-space()='Sauce Labs Onesie']"));
        String expectedOnesie = "Sauce Labs Onesie";
        Assertions.assertEquals(actualOnesie.getText(), expectedOnesie, "Items do not match");

        WebElement actualBoltShirt = driver.findElement(By.xpath("//div[normalize-space()='Sauce Labs Bolt T-Shirt']"));
        String expectedBoltShirt = "Sauce Labs Bolt T-Shirt";
        Assertions.assertEquals(actualBoltShirt.getText(), expectedBoltShirt, "Items do not match");

//        Click on checkout.
        WebElement checkOut = driver.findElement(By.xpath("//button[@id='checkout']"));
        checkOut.click();

//       Enter name, last name and zip code and verify that the names and zip codes are present on the page
        WebElement firstName = driver.findElement(By.xpath("//input[@id='first-name']"));
        firstName.sendKeys("Test");
        Assertions.assertEquals(firstName.getAttribute("value"), "Test");

        WebElement lastName = driver.findElement(By.xpath("//input[@id='last-name']"));
        lastName.sendKeys("Ignore");
        Assertions.assertEquals(lastName.getAttribute("value"), "Ignore");

        WebElement zipCode = driver.findElement(By.xpath("//input[@id='postal-code']"));
        zipCode.sendKeys("123");
        Assertions.assertEquals(zipCode.getAttribute("value"), "123");

//        Click on continue
        WebElement continueClick = driver.findElement(By.xpath("//input[@id='continue']"));
        continueClick.click();

//         Verify the total amount on the page including tax (write a function to do the
//         calculation and check that it matches the value on the page)
        List<WebElement> priceList = driver.findElements(By.xpath("//div[@class = 'inventory_item_price']"));
        float sum = 0.0f;
        float tax = 4.32f;
        for (WebElement priceElement : priceList) {
            String priceText = priceElement.getText();
            String priceValue = priceText.replaceAll("[^0-9.]", "");
            float price = Float.parseFloat(priceValue);
            sum += price;
        }

        WebElement total = driver.findElement(By.xpath("//div[@class='summary_info_label summary_total_label']"));
        String totalText = total.getText();
        String totalValue = totalText.replaceAll("[^0-9.]", "");

        float expectedTotal = Float.parseFloat(totalValue);
        float actualTotal = sum + tax;
        Assertions.assertEquals(expectedTotal, actualTotal, "Price Total and tax does not equal Total");

//        Click on Finish
        WebElement finish = driver.findElement(By.xpath("//button[@id='finish']"));
        finish.click();

//        Verify that the order has been completed.
        WebElement confirmation = driver.findElement(By.xpath("//h2[@class='complete-header']"));
        Assertions.assertTrue(confirmation.isDisplayed(), "Order has not been completed");

    }

//    @AfterAll
//    public static void tearDownDriver(){
//        driver.quit();
//    }
}
