package utils;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitManager {
    private WebDriverWait wait;

    public WaitManager(WebDriver driver, int waitSeconds) {
        this.wait = new WebDriverWait(driver, waitSeconds);
    }

    public void dummyWait() {
        try {
            wait.until(ExpectedConditions.urlToBe("dummy-url-right-here"));
        } catch (TimeoutException e) {}
    }
}
