package test.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = {"src/test/features"},
        glue = {"step.definitions"},
        tags = ""
)
public class TestRunner extends AbstractTestNGCucumberTests {

        @Override
        @DataProvider(parallel = false)
        // @DataProvider(parallel = true) // Multithreading
        public Object[][] scenarios() {
                return super.scenarios();
        }
}
