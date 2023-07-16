import com.beust.ah.A;
import com.google.common.base.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Driver;
import java.time.Duration;

public class testingASignUpForm {
    WebDriver driver;

    @BeforeClass
    public void setUp(){

        System.setProperty("webdriver.chrome.driver", "FilePath");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
    }

    @AfterClass (enabled = false)
    public void closure(){
        driver.quit();
    }

    @Test(priority = 1)
    public void nameValFailEmpField(){

        driver.get("https://demoqa.com/automation-practice-form");
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);
        Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>()
        {
            public Boolean apply(WebDriver driver) {
                WebElement element = driver.findElement(By.xpath("//input[@id='firstName']"));
                String color = element.getCssValue("border-bottom-color");
                if(color.equals("rgba(220, 53, 69, 1)"))
                {
                    return true;
                }
                return false;
            }
        };
        wait.until(function);

        WebElement firstName = driver.findElement(By.xpath("//input[@id='firstName']"));
        Assert.assertEquals(firstName.getCssValue("border-bottom-color"), "rgba(220, 53, 69, 1)");
    }

    @Test(priority = 2)
    public void surnameValFailEmpField(){
        WebElement lastName = driver.findElement(By.xpath("//input[@id='lastName']"));
        Assert.assertEquals(lastName.getCssValue("border-bottom-color"), "rgba(220, 53, 69, 1)");
    }

    @Test(priority = 3)
    public void mobNumValFailEmpField(){
        WebElement userNumber = driver.findElement(By.xpath("//input[@id='userNumber']"));
        Assert.assertEquals(userNumber.getCssValue("border-bottom-color"), "rgba(220, 53, 69, 1)");
    }

    @Test(priority = 4)
    public void maleRadButValFailEmpField() {
        WebElement maleButton = driver.findElement(By.xpath("//label[contains(text(),'Male')]"));
        Assert.assertEquals(maleButton.getCssValue("color"), "rgba(220, 53, 69, 1)");
    }

    @Test(priority = 5)
    public void fmaleRadButValFailEmpField(){
        WebElement userNumber = driver.findElement(By.xpath("//label[contains(text(),'Female')]"));
        Assert.assertEquals(userNumber.getCssValue("border-bottom-color"), "rgba(220, 53, 69, 1)");
    }

    @Test(priority = 6)
    public void otherRadButValFailEmpField(){
        WebElement userNumber = driver.findElement(By.xpath("//input[@id='userNumber']"));
        Assert.assertEquals(userNumber.getCssValue("border-bottom-color"), "rgba(220, 53, 69, 1)");
    }

    @Test(priority = 7)
    public void dateOfBirthAvlbl(){

        WebElement dateOfBirthField = driver.findElement(By.xpath("//input[@id='dateOfBirthInput']"));
        dateOfBirthField.click();

        Select monDateOfBirth = new Select
                (driver.findElement(By.xpath("//select[contains(@class,'month-select')]")));
        monDateOfBirth.selectByValue("5");

        Select yDateOfBirth = new Select
                (driver.findElement(By.xpath("//select[contains(@class,'year-select')]")));
        yDateOfBirth.selectByValue("1994");

        driver.findElement(By.xpath("//div[contains(@aria-label,'June 29th')]")).click();

        Assert.assertEquals(dateOfBirthField.getAttribute("value"), "29 Jun 1994");
    }

    @Test(priority = 8)
    public void dateOfBirthTypbl(){

        //That was the only way to type a date. Это был единственный способ напечатать дату.
        WebElement dateOfBirthField  = driver.findElement(By.xpath("//input[@id='dateOfBirthInput']"));

        Actions a = new Actions(driver);
        dateOfBirthField.click();
        dateOfBirthField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        a.sendKeys("29 Jun 1994").sendKeys(Keys.RETURN).perform();

        Assert.assertEquals(dateOfBirthField.getAttribute("value"), "29 Jun 1994");
    }

    @Test(priority = 9)
    public void usrnmbrIsAvlblAndTpbl(){

        WebElement usrNmbr = driver.findElement(By.xpath("//input[@id='userNumber']"));
        usrNmbr.sendKeys("9999999999");
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        Function<WebDriver, Boolean> function2 = new Function<WebDriver, Boolean>()
        {
            public Boolean apply(WebDriver driver) {
                WebElement element = driver.findElement(By.xpath("//input[@id='userNumber']"));
                String color = element.getCssValue("border-bottom-color");
                if(color.equals("rgba(40, 167, 69, 1)"))
                {
                    return true;
                }
                return false;
            }
        };
        wait.until(function2);

        Assert.assertEquals(usrNmbr.getCssValue("border-bottom-color"), "rgba(40, 167, 69, 1)");

    }

    @Test(priority = 10)
    public void nameIsValdtd(){

        WebElement usrName = driver.findElement(By.xpath("//input[@id='firstName']"));
        usrName.sendKeys("Name");
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        Function<WebDriver, Boolean> function3 = new Function<WebDriver, Boolean>()
        {
            public Boolean apply(WebDriver driver) {
                WebElement element = driver.findElement(By.xpath("//input[@id='firstName']"));
                String color = element.getCssValue("border-bottom-color");
                if(color.equals("rgba(40, 167, 69, 1)"))
                {
                    return true;
                }
                return false;
            }
        };
        wait.until(function3);

        Assert.assertEquals(usrName.getCssValue("border-bottom-color"), "rgba(40, 167, 69, 1)");

    }

    @Test(priority = 11)
    public void lastnameIsValdtd() {

        WebElement lastName = driver.findElement(By.xpath("//input[@id='lastName']"));
        lastName.sendKeys("Surname");
        driver.findElement(By.xpath("//button[@id='submit']")).click();

        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        Function<WebDriver, Boolean> function4 = new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                WebElement element = driver.findElement(By.xpath("//input[@id='lastName']"));
                String color = element.getCssValue("border-bottom-color");
                if (color.equals("rgba(40, 167, 69, 1)")) {
                    return true;
                }
                return false;
            }
        };
        wait.until(function4);

        Assert.assertEquals(lastName.getCssValue("border-bottom-color"), "rgba(40, 167, 69, 1)");

    }

        @Test(priority = 12)
        public void mailIsValdtd(){

            WebElement mail = driver.findElement(By.xpath("//input[@id='userEmail']"));
            mail.sendKeys("mail@mail.com");

            FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class);
            Function<WebDriver, Boolean> function5 = new Function<WebDriver, Boolean>()
            {
                public Boolean apply(WebDriver driver) {
                    WebElement element = driver.findElement(By.xpath("//input[@id='userEmail']"));
                    String color = element.getCssValue("border-bottom-color");
                    if(color.equals("rgba(40, 167, 69, 1)"))
                    {
                        return true;
                    }
                    return false;
                }
            };
            wait.until(function5);

            Assert.assertEquals(mail.getCssValue("border-bottom-color"), "rgba(40, 167, 69, 1)");

    }

    @Test(priority = 13)
    public void maleRadButValdtd() {

        WebElement maleButton = driver.findElement(By.xpath("//label[contains(text(),'Male')]"));
        maleButton.click();

        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        Function<WebDriver, Boolean> function6 = new Function<WebDriver, Boolean>()
        {
            public Boolean apply(WebDriver driver) {
                WebElement element = driver.findElement(By.xpath("//label[contains(text(),'Male')]"));
                String color = element.getCssValue("border-bottom-color");
                if(color.equals("rgba(40, 167, 69, 1)"))
                {
                    return true;
                }
                return false;
            }
        };
        wait.until(function6);

        Assert.assertEquals(maleButton.getCssValue("color"), "rgba(40, 167, 69, 1)");
    }

    @Test(priority = 14)
    public void hobRadButnIsValdtd() {

        WebElement hobButn = driver.findElement(By.xpath("//label[contains(text(),'Sports')]"));
        hobButn.click();

        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        Function<WebDriver, Boolean> function7 = new Function<WebDriver, Boolean>()
        {
            public Boolean apply(WebDriver driver) {
                WebElement element = driver.findElement(By.xpath("//label[contains(text(),'Sports')]"));
                String color = element.getCssValue("color");
                if(color.equals("rgba(40, 167, 69, 1)"))
                {
                    return true;
                }
                return false;
            }
        };
        wait.until(function7);

        Assert.assertEquals(hobButn.getCssValue("color"), "rgba(40, 167, 69, 1)");
    }

    @Test(priority = 15)
    public void AdressIsValdtd() {

        WebElement adress = driver.findElement(By.xpath("//textarea[@id='currentAddress']"));
        adress.sendKeys("adress");

        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        Function<WebDriver, Boolean> function8 = new Function<WebDriver, Boolean>()
        {
            public Boolean apply(WebDriver driver) {
                WebElement element = driver.findElement(By.xpath("//textarea[@id='currentAddress']"));
                String color = element.getCssValue("border-bottom-color");
                if(color.equals("rgba(40, 167, 69, 1)"))
                {
                    return true;
                }
                return false;
            }
        };
        wait.until(function8);

        Assert.assertEquals(adress.getCssValue("border-bottom-color"), "rgba(40, 167, 69, 1)");
    }

    @Test(priority = 16, dependsOnMethods = {"nameValFailEmpField", "surnameValFailEmpField", "mobNumValFailEmpField",
    "maleRadButValFailEmpField", "fmaleRadButValFailEmpField", "otherRadButValFailEmpField", "dateOfBirthAvlbl",
    "dateOfBirthTypbl", "usrnmbrIsAvlblAndTpbl", "nameIsValdtd", "lastnameIsValdtd", "mailIsValdtd", "maleRadButValdtd",
    "hobRadButnIsValdtd", "AdressIsValdtd"})

    //Dependencies are added for the sake of dependencies. Зависимости добавлены ради самих зависимостей.

    public void dateOfBrthIsDispld() {

        WebElement dateOfBrth = driver.findElement(By.xpath("//tbody/tr[5]/td[2]"));

        Assert.assertEquals(dateOfBrth.getText(), "29 June,1994");
    }

    @Test(priority = 17)
    public void mobNumIsDispld() {

        driver.findElement(By.xpath("//button[@id='submit']")).click();

        WebElement mobNum = driver.findElement(By.xpath("//tbody/tr[4]/td[2]"));
        Assert.assertEquals(mobNum.getText(), "9999999999");
    }

    @Test(priority = 18)
    public void mailIsDispld() {

        WebElement mail = driver.findElement(By.xpath("//tbody/tr[2]/td[2]"));
        Assert.assertEquals(mail.getText(), "mail@mail.com");
    }

    @Test(priority = 19)
    public void nameAndSrnmIsDispld() {

        WebElement mail = driver.findElement(By.xpath("//tbody/tr[1]/td[2]"));
        Assert.assertEquals(mail.getText(), "Name Surname");
    }

    @Test(priority = 20)
    public void hobbiesAreDispld() {

        WebElement hobby = driver.findElement(By.xpath("//tbody/tr[7]/td[2]"));
        Assert.assertEquals(hobby.getText(), "Sports");
    }

    @Test(priority = 21)
    public void addressIsDispld() {

        WebElement hobby = driver.findElement(By.xpath("//tbody/tr[9]/td[2]"));
        Assert.assertEquals(hobby.getText(), "adress");
    }

}
