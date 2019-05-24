package keypack;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Methods {
	
		public WebDriver driver;
		public WebDriverWait w;
		
		public String applaunch(String e, String d, String c) throws Exception
		{
			if (e.equalsIgnoreCase("chrome"))
			{
				ChromeOptions ops = new ChromeOptions();
		        ops.addArguments("--disable-notifications");
				System.setProperty("webdriver.chrome.driver", "D:\\shiva\\drivers\\chromedriver.exe");
				driver=new ChromeDriver(ops);
			}
		else if(e.equalsIgnoreCase("firefox"))	
			{
			System.setProperty("webdriver.gecko.driver", "D:\\Automation\\chromedriver.exe");
			driver=new FirefoxDriver();
			}
		
		else if(e.equalsIgnoreCase("ie"))	
			{
			System.setProperty("webdriver.ie.driver", "D:\\Automation\\iedriverserver.exe");
			driver=new  InternetExplorerDriver();
			}
		else
		  {
			return ("unknown broser");
		  }
			
				driver.get(d);
				w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@name='mobileNo'])[1]")));
				driver.manage().window().maximize();
			
			return ("Done");
	}  
		
	public String fill(String elementLocator, String data, String criteria) {
			driver.findElement(By.xpath(elementLocator)).sendKeys(data);
			return ("Input credentials entered");
	}
				
		
		public String click(String e, String d, String c) throws Exception
		{
			try
			{
				w.until(ExpectedConditions.elementToBeClickable(By.xpath(e)));
				driver.findElement(By.xpath(e)).click();
				return("Done");
			}
			catch(Exception ex)
			{
				return("Error "+ex.getMessage());
			}
		}
		public String validateLogin(String e, String d, String c) throws Exception
		 {
			 Thread.sleep(10000);
		  try
		  {
			  if(c.equalsIgnoreCase("all_valid") && driver.findElement(By.xpath("//*[text()='SendSMS']")).isDisplayed())
				  
		     {
			 return("passed");
		     }
			  else if(c.equalsIgnoreCase("mbno_blank") && driver.findElement(By.xpath("//*[text()='Enter your mobile number']")).isDisplayed())
			  {
				  return("passed");
			  }
			  else if(c.equalsIgnoreCase("pwd_blank") && driver.findElement(By.xpath("(//*[@class='error'])[1])")).isDisplayed())
			  {
				  return("passed");
			  }
			  else if(c.equalsIgnoreCase("pwd_invalid") && driver.findElement(By.xpath("//*[contains(text(),'Try Again')][1]")).isDisplayed()) 
			  {
				  return("passed");
			  }
			  else if (c.equalsIgnoreCase("mbno_invalid") && driver.findElement(By.xpath("//*[contains(text(),'Enter valid mobile number')][1]")).isDisplayed())
			  {
				  return("passed");
			  }
			  else
			  { 
				  String temp=this.screenshot();
				  return("Test Failed & go to "+temp+".png");
			  }
		 } // try block
		  catch(Exception ex)
			{
			  this.screenshot();
				return("Error "+ex.getMessage());
			}
		 }
		public String closeSite(String e, String d, String c)
		{
				driver.quit();
				return("Done");
		}
	    	public String screenshot() throws Exception
		{
			Date d=new Date();
			SimpleDateFormat s=new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
			String x=s.format(d)+".png";
			File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			File dest=new File(x);
			FileHandler.copy(src,dest);
			return(x);
		}
	}



