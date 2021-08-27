package runner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "classpath:features",
        glue = "stepdefs",
        tags = "",
        plugin = {"pretty","summary",
                "html:target/cucumber-reports.html",
                "json:target/json_result.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        dryRun = false

)
public class RunTest extends AbstractTestNGCucumberTests {

        @Override
        @DataProvider(parallel = true)
        public Object[][] scenarios(){
                return super.scenarios();
        }
}
