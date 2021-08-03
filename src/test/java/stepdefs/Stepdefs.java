package stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;


public class Stepdefs {

    WebDriver driver;
    String url = "https://parabank.parasoft.com/parabank/index.htm";
    String userName = "john";
    String password = "demo";
    Scenario scenario;

    @Before
    public void setUp(Scenario scenario){
        this.scenario = scenario;
    }

    @After
    public  void cleanUp(){
        driver.quit();
    }


    ////TC1: Login Feature
    @Given("User opened the browser")
    public void User_opened_the_browser(){
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // maximize browser window
        driver.manage().deleteAllCookies(); // delete all cookies
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Given("User navigated to the application url")
    public void User_navigated_to_the_application_url(){
        driver.get(url);
    }

    @When("User enter username as {string} and password as {string} and click on login button")
    public void User_enter_username_as_and_password_as_and_click_on_login_button(String userName,String password){
        driver.findElement(By.name("username")).sendKeys(userName);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.xpath("//input[@value='Log In']")).click();

    }

    @Then("user is able to login in the application")
    public void user_is_able_to_login_in_the_application(){
        Assert.assertEquals("ParaBank | Accounts Overview",driver.getTitle());
        boolean actualTableDisplayed = driver.findElement(By.id("accountTable")).isDisplayed();
        Assert.assertEquals(true,actualTableDisplayed);


    }


    //TC2: open new account
    @Given("User is logged in")
    public void user_is_logged_in() {
        User_opened_the_browser();
        User_navigated_to_the_application_url();
        User_enter_username_as_and_password_as_and_click_on_login_button(userName,password);
    }

    @Given("User is able to click on link name as {string}")
    public void user_is_able_to_click_on_link_name_as(String linkName) {
        driver.findElement(By.linkText(linkName)).click();

    }

    @When("User select Account Type as {string} and any account number")
    public void user_select_account_type_as_and_any_account_number(String type) {
        WebElement dropDownAccountType = driver.findElement(By.id("type"));
        Select selectAccountType = new Select(dropDownAccountType);
        selectAccountType.selectByVisibleText(type);

        WebElement dropDownAccountNumber = driver.findElement(By.id("fromAccountId"));
        Select selectAccountNumber = new Select(dropDownAccountNumber);
        selectAccountNumber.selectByIndex(0);
    }

    @When("User click on OPEN NEW ACCOUNT button")
    public void user_click_on_open_new_account_button() throws InterruptedException {
        //thread added
        Thread.sleep(5000);
        driver.findElement(By.xpath("//input[@type='submit' and @value='Open New Account']")).click();
    }

    @Then("Message is displayed Congratulations, your account is now open.")
    public void message_is_displayed_congratulations_your_account_is_now_open() {
        WebElement element =driver.findElement(By.xpath("//h1[text()='Account Opened!']"));
        Assert.assertEquals(element.isDisplayed(),true,"Account open successfully");
    }

    @Then("A new account number is generated")
    public void a_new_account_number_is_generated() {
        WebElement element = driver.findElement(By.id("newAccountId"));
        String accountNumber = element.getText();
        Assert.assertEquals(element.isDisplayed(),true,"New account number link");
        scenario.log("New Account Number Generated As:" + accountNumber);
    }


}
