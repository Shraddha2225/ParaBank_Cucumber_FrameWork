package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

public class DriverFactory {

    public static WebDriver createInstance(String browserName) {
        //DriverManager driverManager=null;

        WebDriver driver = null;
        switch (browserName.trim().toLowerCase()){
            case "chrome":
                return new ChromeDriver();


            case "firefox":
                return new FirefoxDriver();

            default:
                Assert.fail("No Such Method Defined");
        }
        return driver;
    }
}