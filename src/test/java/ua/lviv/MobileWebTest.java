package ua.lviv;
import io.selendroid.client.SelendroidDriver;
import io.selendroid.common.SelendroidCapabilities;
import io.selendroid.standalone.SelendroidConfiguration;
import io.selendroid.standalone.SelendroidLauncher;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;


public class MobileWebTest {
    private SelendroidLauncher selendroidServer = null;
    private WebDriver driver = null;

    @Test
    public void testLoginGmail() {

        driver.get("https://mail.google.com/");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        WebElement emailInput = driver.findElement(By.id("identifierId"));
        emailInput.sendKeys("andrianlarson@gmail.com");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement buttonNext = driver.findElement(By.id("identifierNext"));

        buttonNext.click();

        WebElement passwordInput = driver.findElement(By.xpath("//div[@id=\"password\"]/div/div/div/input"));
        passwordInput.sendKeys("qwertyuiop[]");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement buttonSignIn = driver.findElement(By.id("passwordNext"));

        buttonSignIn.click();

        // Check the title of the page
        if(!driver.getTitle().startsWith("https://")) {
            assertEquals("Gmail", driver.getTitle());
        }
        driver.quit();
    }

    @Before
    public void startSelendroidServer() throws Exception {
        if (selendroidServer != null) {
            selendroidServer.stopSelendroid();
        }
        SelendroidConfiguration config = new SelendroidConfiguration();

        selendroidServer = new SelendroidLauncher(config);
        selendroidServer.launchSelendroid();

        DesiredCapabilities caps = SelendroidCapabilities.android();

        driver = new SelendroidDriver(new URL("http://localhost:4444/wd/hub/"), caps);
    }

    @After
    public void stopSelendroidServer() {
        if (driver != null) {
            driver.quit();
        }
        if (selendroidServer != null) {
            selendroidServer.stopSelendroid();
        }
    }
}

