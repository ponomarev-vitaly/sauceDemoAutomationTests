import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class OrderTest {

        private static WebDriver driver;

        @BeforeAll
        public static void setUpDriver() {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            WebDriver driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.get("https://www.saucedemo.com/");
        }

        @Test
        public void addToCart(){
//        Login
            driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
            driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
            driver.findElement(By.xpath("(//input[@id='login-button'])[1]")).click();

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
            Assert.assertEquals(validateNumber, 3, "Cart not equal to 3");

//       Click on the cart icon.
            WebElement clickIcon = driver.findElement(By.xpath("//a[@class='shopping_cart_link']"));
            clickIcon.click();

//      verify the items on the page match the items you have added to the cart.
            WebElement actualBackpack = driver.findElement(By.xpath("//div[normalize-space()='Sauce" +
                    " Labs Backpack']"));
            String expectedBackpack = "Sauce Labs Backpack";
            Assert.assertEquals(actualBackpack.getText(), expectedBackpack, "Items do not match");

            WebElement actualOnesie = driver.findElement(By.xpath("//div[normalize-space()='Sauce Labs Onesie']"));
            String expectedOnesie = "Sauce Labs Onesie";
            Assert.assertEquals(actualOnesie.getText(), expectedOnesie, "Items do not match");

            WebElement actualBoltShirt = driver.findElement(By.xpath("//div[normalize-space()='Sauce Labs Bolt T-Shirt']"));
            String expectedBoltShirt = "Sauce Labs Bolt T-Shirt";
            Assert.assertEquals(actualBoltShirt.getText(), expectedBoltShirt, "Items do not match");

//        Click on checkout.
            WebElement checkOut = driver.findElement(By.xpath("//button[@id='checkout']"));
            checkOut.click();

//       Enter names and zip code and Verify that the names and zip codes are present on the page
            WebElement firstName = driver.findElement(By.xpath("//input[@id='first-name']"));
            firstName.sendKeys("Test");
            Assert.assertEquals(firstName.getAttribute("value"), "Test");

            WebElement lastName = driver.findElement(By.xpath("//input[@id='last-name']"));
            lastName.sendKeys("Ignore");
            Assert.assertEquals(lastName.getAttribute("value"), "Ignore");

            WebElement zipCode = driver.findElement(By.xpath("//input[@id='postal-code']"));
            zipCode.sendKeys("123");
            Assert.assertEquals(zipCode.getAttribute("value"), "123");

//        Click on continue
            WebElement contInue = driver.findElement(By.xpath("//input[@id='continue']"));
            contInue.click();

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
            Assert.assertEquals(expectedTotal, actualTotal, "Price Total and tax does not equal Total");

//        Click on Finish
            WebElement finish = driver.findElement(By.xpath("//button[@id='finish']"));
            finish.click();

//        Verify that the order has been completed.
            WebElement confirmation = driver.findElement(By.xpath("//h2[@class='complete-header']"));
            Assert.assertTrue(confirmation.isDisplayed(), "Order has not been completed");
        }

        @AfterAll
        public static void tearDown(){
            driver.quit();
        }
}


