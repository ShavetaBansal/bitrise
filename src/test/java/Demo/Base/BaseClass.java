package Demo.Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;


public class BaseClass {
	 public static Properties prop;
	public static  AndroidDriver driver;
	public static ExtentReports extent;// start from here only
	public static ExtentTest test;
	public static ExtentHtmlReporter htmlReporter;// uptohere
 public static String Email;
 public static String Password;
	
	  public void setUp() throws MalformedURLException
	  {
	DesiredCapabilities dc = new DesiredCapabilities();
	dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");
	dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
	dc.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
	dc.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir")+"/apk/app-scale_1.0.0_32.apk");
URL url = new URL("http://localhost:4723/wd/hub");
driver= new AndroidDriver(url, dc);

	  }

	@BeforeSuite
	public void extent() throws Exception
	{
		// htmlReporter= new ExtentHtmlReporter(System.getProperty("user.dir")+"//test-output//ExtentReport//Extentreports.html");
		htmlReporter= new ExtentHtmlReporter(System.getProperty("user.dir")+"//test-output//ExtentReport//Extentreports.html");
		 htmlReporter.loadXMLConfig(System.getProperty("user.dir")+"/extent-config.xml");
		
			extent = new ExtentReports();
			extent.attachReporter(htmlReporter);
			extent.setSystemInfo("HostName", "MyHost");
			extent.setSystemInfo("ProjectName", "HB Scale");
			extent.setSystemInfo("Tester", "Shaveta");
			extent.setSystemInfo("Device", "Android v12");
		
			
		
		
		
		
		prop= new Properties();
		 System.out.println("super constructor invoked");

File file = new File(System.getProperty("user.dir")+"/configuration/config.properties");


		 FileInputStream fp = new FileInputStream(file);
		 prop.load(fp);
		 Email = prop.getProperty("email");
		 Password =prop.getProperty("password");
		 
	}
	
	 public String screenshot(String Filename)
		{  String dateName = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date());
			TakesScreenshot takescreenshot = (TakesScreenshot) driver;
			File SCfile	=takescreenshot.getScreenshotAs(OutputType.FILE);
		 
			try {
				FileUtils.copyFile(SCfile,new File(System.getProperty("user.dir")+"\\screenshot\\"+Filename+ "_"
						+ dateName+".png") );
			} catch (IOException e) {
				
				e.getMessage();
			}
		File dest=	new File(System.getProperty("user.dir")+"\\screenshot\\"+Filename+ "_"
					+ dateName+".png");
			String Path= dest.getAbsolutePath();
			return Path;
		}
	 @AfterSuite
	 public void close()
	 {
	 	extent.flush();

	 }


}
