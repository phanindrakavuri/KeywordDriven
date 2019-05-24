package keypack;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MethodsClass {
public static WebDriver driver;
public WebDriverWait wait;

public String applaunch(String elementLocator, String data, String criteria) {
	if (elementLocator.equalsIgnoreCase("chrome"))
	{
		ChromeOptions ops = new ChromeOptions();
        ops.addArguments("--disable-notifications");
		System.setProperty("webdriver.chrome.driver","D:\\shiva\\drivers\\chromedriver.exe");
		driver = new ChromeDriver(ops);
	}
	else if (elementLocator.equalsIgnoreCase("firefox"))
	{
		System.setProperty("webdriver.gecko.driver","");
		driver = new FirefoxDriver();
	}
	else{
		System.out.println("unknown browser");
	}
	
	driver.get(data);
	wait = new WebDriverWait(driver,20);
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[@id='mobileNo'])[1]")));
	driver.manage().window().maximize();
	
	return("Browser Opened");
}

public String fill(String elementLocator, String data, String criteria) {
	driver.findElement(By.xpath(elementLocator)).sendKeys(data);
	return ("Input credentials entered");
}

public String click(String elementLocator, String data, String criteria) {
	driver.findElement(By.xpath(elementLocator)).click();
	return ("click on login button");
}


public String validateLogin(String elementLocator, String data, String criteria) throws Exception {
if (criteria.equalsIgnoreCase("all_valid") && driver.findElement(By.xpath("//*[text()='SendSMS']")).isDisplayed())
{
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='SendSMS']")));
	String temp = this.screenShot();
	return ("successfully login with valid input "+temp+".png");
}
else if(criteria.equalsIgnoreCase("mbno_blank") && driver.findElement(By.xpath("//*[text()='Enter your mobile number']")).isDisplayed())
{
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Enter your mobile number']")));
	String temp = this.screenShot();
	return ("mobile number blank test passed "+temp+".png");
}
else if (criteria.equalsIgnoreCase("mbno_invalid") && driver.findElement(By.xpath("//*[contains(text(),'Enter valid mobile number')][1]")).isDisplayed()) {
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Enter valid mobile number')][1]")));
	String temp = this.screenShot();
	return ("mobile Number invalid test passed "+temp+".png");
}
else if (criteria.equalsIgnoreCase("pwd_invalid") &&driver.findElement(By.xpath("//*[contains(text(),'Try Again')][1]")).isDisplayed())
{
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Try Again')][1]")));
	String temp = this.screenShot();
	return ("password invalid test passed "+temp+".png");
}
else if (criteria.equalsIgnoreCase("pwd_blank") && driver.findElement(By.xpath("(//*[@class='error'])[1]")).isDisplayed())
{
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[@class='error'])[1]")));
	String temp = this.screenShot();
	return ("password blank test passed "+temp+".png");
}
else {
	String temp = this.screenShot();
	return ("test failed and go to "+temp+".png");
}
}

public String closeSite(String elementLocator, String data, String criteria) throws Exception {
     Thread.sleep(5000);
	 driver.close();
	 return ("site closed");
}


public String screenShot() throws IOException
{
	Date d=new Date();
	SimpleDateFormat s=new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
	String temp=s.format(d)+".png";
	File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	File dest=new File(temp);
	FileHandler.copy(src,dest);
	return (temp);
}

}
