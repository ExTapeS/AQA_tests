import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

public class MainCapabilities {
    WebDriver driver;

    @BeforeMethod
    public void setUp(){

        System.setProperty("webdriver.chrome.driver", "FilePath");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
    }

    @AfterMethod
    public void closure(){
        driver.quit();
    }

    @Test (priority = 1)
    public void alertAfter5Seconds(){

        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.id("timerAlertButton")).click();
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(6));
        w.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    @Test (priority = 2)
    public void newTab() {
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.xpath("//button[@id='tabButton']")).click();
        Set<String> listOfWindows = driver.getWindowHandles();
        Iterator<String> iterator = listOfWindows.iterator();
        String mainWindow = iterator.next();
        String childWindow = iterator.next();
        driver.switchTo().window(childWindow);
        Assert.assertEquals(driver.findElement(By.xpath("//h1[@id='sampleHeading']")).getText(),"This is a sample page");
    }

    @Test (priority = 3)
    public void childFrame() {
        driver.get("https://demoqa.com/nestedframes");
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='frame1']")));
        driver.switchTo().frame(driver.findElement(By.xpath("//body/iframe[1]")));
        Assert.assertEquals(driver.findElement(By.xpath("//body")).getText(), "Child Iframe");
        driver.switchTo().defaultContent();
    }

    @Test (priority = 4, dependsOnMethods = {"childFrame"})
    public void dragAndDropWithOffset() {
        driver.get("https://demoqa.com/droppable");
        driver.findElement(By.xpath("//a[contains(@id,'Propogation')]")).click();
        Actions a = new Actions(driver);
        WebElement draggedElement = driver.findElement(By.xpath("//div[@id='dragBox']"));
        WebElement landingElement = driver.findElement(By.xpath("//div[@id='notGreedyInnerDropBox']"));
        a.clickAndHold(draggedElement)
                .pause(Duration.ofSeconds(1))
                .moveToElement(landingElement, 0, -92)
                .pause(Duration.ofSeconds(1))
                .release()
                .perform();
        WebElement outFrame = driver.findElement(By.xpath("//div[@id='notGreedyDropBox']"));
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(2));
        w.until(ExpectedConditions.textToBe(By.xpath("//div[@id='notGreedyDropBox']"), "Dropped!\n" +
                "Inner droppable (not greedy)"));
        Assert.assertEquals(outFrame.getText(), "Dropped!\n" + "Inner droppable (not greedy)");
    }
}
