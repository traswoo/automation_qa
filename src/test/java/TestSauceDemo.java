import io.github.bonigarcia.wdm.WebDriverManager;
import lv.acodemy.constants.Generic;
import lv.acodemy.page_object.InventoryPage;
import lv.acodemy.page_object.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static lv.acodemy.constants.Generic.SAUCE_URL;

public class TestSauceDemo {

    ChromeDriver driver;
    LoginPage loginPage;
    InventoryPage inventoryPage;

    @BeforeMethod
    public void initialize() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        driver.get(SAUCE_URL);
    }

    @Test
    public void authorizeTest() {
        loginPage.authorize("standard_user", "secret_sauce");
        Assert.assertEquals(inventoryPage.itemElementCount(), 6);
    }

    @Test
    public void loginErrorTest() {
        loginPage.authorize("standarduser", "secretsauce");
        Assert.assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username and password do not match any user in this service");
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
