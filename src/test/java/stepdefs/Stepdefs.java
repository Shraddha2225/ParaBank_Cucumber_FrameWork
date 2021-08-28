package stepdefs;

import io.cucumber.java.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.log4testng.Logger;
import utils.DriverFactory;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Log4j2
public class Stepdefs {
    //private static final Logger log = (Logger) LogManager.getLogger(Stepdefs.class);

    WebDriver driver;
    String url = "https://parabank.parasoft.com/parabank/index.htm";
    String userName = "john";
    String password = "demo";
    String enterAmount = "3600";
    Scenario scenario;

    @Before
    public void setUp(Scenario scenario){

        this.scenario = scenario;
        scenario.log("executed before each scenario");
    }

    @After
    public  void cleanUp(){
        if (!(driver==null)){
            driver.quit();
        }
        scenario.log("executed after each scenario");
    }

    @BeforeStep
    public  void beforeEachStep(){
        scenario.log("executed before each line of test case");
    }

    @AfterStep
    public void afterEachStep(){
        scenario.log("executed after each line of test case");
        if(!(driver==null)){
            TakesScreenshot screenshot =(TakesScreenshot)driver;
            byte[] data = screenshot.getScreenshotAs(OutputType.BYTES);
            scenario.attach(data,"image/png/jpeg","Failed step name" +scenario.getName());
            //scenario.log("Test cases is passed,no screenshots captured");
        }
        log.debug("Each step hook is executed,screen shots taken");
    }



    ////TC1: Login Feature
    @Given("User opened the browser")
    public void User_opened_the_browser(){
        String browserName = System.getProperty("browser");//get value from command line
        driver = DriverFactory.createInstance(browserName);
        driver = new ChromeDriver();
        log.debug("chrome initialized");
        driver.manage().window().maximize(); // maximize browser window
        log.debug("Browser maximized");
        driver.manage().deleteAllCookies(); // delete all cookies
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        log.debug("Each step hook is executed,screen shots taken");
    }

    @Given("User navigated to the application url")
    public void User_navigated_to_the_application_url(){
        driver.get(url);
        log.debug("Navigate url" +url);
    }

    @When("User enter username as {string} and password as {string} and click on login button")
    public void User_enter_username_as_and_password_as_and_click_on_login_button(String userName,String password){
        driver.findElement(By.name("username")).sendKeys(userName);
        log.debug("login username" +userName);
        driver.findElement(By.name("password")).sendKeys(password);
        log.debug("login password" +password);
        driver.findElement(By.xpath("//input[@value='Log In']")).click();
        log.debug("Click on login");
     }

    //User credentials implementation using key and value pair
    @When("User Enter Username and Password as in the below table and click on login button")
    public void user_enter_username_and_password_as_in_the_below_table_and_click_on_login_button(Map<String,String> userCred) {
        driver.findElement(By.name("username")).sendKeys(userCred.get("Username"));
        driver.findElement(By.name("password")).sendKeys(userCred.get("Password"));
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
        log.debug("click on link and link name is :" +linkName);
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
        Thread.sleep(5000);
        driver.findElement(By.xpath("//input[@type='submit' and @value='Open New Account']")).click();
    }

    @Then("Message is displayed Congratulations, your account is now open.")
    public void message_is_displayed_congratulations_your_account_is_now_open() {
        WebElement element = driver.findElement(By.xpath("//h1[text()='Account Opened!']"));
        Assert.assertEquals(element.isDisplayed(),true,"Account open successfully");
    }

    @Then("A new account number is generated")
    public void a_new_account_number_is_generated() {
        WebElement element = driver.findElement(By.id("newAccountId"));
        String accountNumber = element.getText();
        Assert.assertEquals(element.isDisplayed(),true,"New account number link");
        scenario.log("New Account Number Generated As:" + accountNumber);
    }


    //TC3: Transfer funds from one account to another account
    @When("User enter amount {string} to transfer")
    public void user_enter_amount_to_transfer(String enterAmount) throws InterruptedException {
        Thread.sleep(5000);
        driver.findElement(By.id("amount")).sendKeys(enterAmount);
    }

    @When("User select any account number and want to transfer funds to any another account")
    public void user_select_any_account_number_and_want_to_transfer_funds_to_any_another_account(){
        WebElement dropDownFirstAccountNumber = driver.findElement(By.id("fromAccountId"));
        Select selectFirstAccountNumber = new Select(dropDownFirstAccountNumber);
        selectFirstAccountNumber.selectByIndex(0);

        WebElement dropDownSecondAccountNumber = driver.findElement(By.id("toAccountId"));
        Select selectSecondAccountNumber = new Select(dropDownSecondAccountNumber);
        selectSecondAccountNumber.selectByIndex(0);
    }

    @When("User click on TRANSFER")
    public void user_click_on_transfer() {
        driver.findElement(By.xpath("//input[@type='submit' and @value='Transfer']")).click();

    }

    @Then("Message is displayed amount is transfer from one account number to another account number")
    public void message_is_displayed_amount_is_transfer_from_one_account_number_to_another_account_number(){
        WebElement element = driver.findElement(By.xpath("//h1[text()='Transfer Complete!']"));
        Assert.assertEquals(element.isDisplayed(),true,"Amount is transfer successfully ");

    }


    //TC4: Bill payment
    @When("User enter payee name and address")
    public void user_enter_payee_name_and_address() {
        WebElement payeeName =driver.findElement(By.name("payee.name"));
        payeeName.sendKeys("shraddha");

        WebElement payeeAddress = driver.findElement(By.name("payee.address.street"));
        payeeAddress.sendKeys("Sector 15,Carena Galaxy,Near DMart,Kharghar");

    }

    @When("User enter city and state")
    public void user_enter_city_and_state() {
        WebElement payeeCity = driver.findElement(By.name("payee.address.city"));
        payeeCity.sendKeys("Navi Mumbai");

        WebElement payeeState = driver.findElement(By.name("payee.address.state"));
        payeeState.sendKeys("Maharashtra");
    }

    @When("User enter zipcode and phone number")
    public void user_enter_zipcode_and_phone_number() {
        WebElement payeeZipcode = driver.findElement(By.name("payee.address.zipCode"));
        payeeZipcode.sendKeys("410210");

        WebElement payeePhoneNumber = driver.findElement(By.name("payee.phoneNumber"));
        payeePhoneNumber.sendKeys("7886453790");
    }

    @When("User enter account number and verify account number")
    public void user_enter_account_number_and_verify_account_number() {
        WebElement payeeAccountNumber = driver.findElement(By.name("payee.accountNumber"));
        payeeAccountNumber.sendKeys("29876");

        WebElement payeeVerifyAccountNumber = driver.findElement(By.name("verifyAccount"));
        payeeVerifyAccountNumber.sendKeys("29876");

    }

    @When("User enter an amount")
    public void user_enter_an_amount() {
        WebElement enterAmount = driver.findElement(By.name("amount"));
        enterAmount.sendKeys("4000");
    }

    @When("User select any account number from which he want to pay a bill")
    public void user_select_any_account_number_from_which_he_want_to_pay_a_bill() {
        WebElement dropDownFromAccountNo = driver.findElement(By.name("fromAccountId"));
        Select selectFromAccountNo = new Select(dropDownFromAccountNo);
        selectFromAccountNo.selectByIndex(0);
    }

    @When("User click on SEND PAYMENT")
    public void user_click_on_send_payment() {
        driver.findElement(By.xpath("//input[@type='submit' and @value='Send Payment']")).click();
    }

    @Then("Message is displayed bill payment was successful")
    public void message_is_displayed_bill_payment_was_successful() throws InterruptedException {
        WebElement displayBillComplete = driver.findElement(By.xpath("//h1[text()='Bill Payment Service']"));
        Assert.assertEquals(displayBillComplete.isDisplayed(),true,"Bill Payment Is Completed Successfully");
        Thread.sleep(5000);
    }


    //Datatable temporary example
    @Given("I want to do something")
    public void i_want_to_do_something() {

    }

    @When("I have a argument to send as {string}")
    public void i_have_a_argument_to_send_as(String args) {
        System.out.println("Printing the Argument :" +args);
    }

    @When("I have a list of items to send")
    public void i_have_a_list_of_items_to_send(List<String> list) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.

        System.out.println(list.toString());
    }

    //datatable entry using collection map key and value pair
    @When("I have a Employees Name and their Employee Id")
    public void i_have_a_employees_name_and_their_employee_id(Map<String,String> map) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        System.out.println("Printing An Employee Details : " +map);
    }

    @Then("Something should be happen")
    public void something_should_be_happen() {

    }

    //Examples steps
    @Given("I am on a search page")
    public void i_am_on_a_search_page() {

    }
    @When("I want to search for a product as {string}")
    public void i_want_to_search_for_a_product_as(String product) {
        System.out.println("Printing searched product :" +product);
    }
    @Then("Result should be displayed related to {string}")
    public void result_should_be_displayed_related_to(String success) {
        System.out.println("Product search success :" +success);

    }















}
