package com.artoftesting.base;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.DriverManager;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.artoftesting.util.ExcelUtil;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.apache.commons.io.FileUtils;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * The TestBase class is the base class to fetch environment specific
 * configuration parameters from Jenkins/Maven. Based on the parameters, it
 * performs the browser setup and tear-down functions.
 * 
 *
 */

public class TestBase {

	protected static WebDriver driver;
	public static Properties envConfig;
	WebDriverWait wait;
	public static ExtentReports extentReport;
	public static ExtentSparkReporter htmlReporter;
	public static ExtentTest testcase;
	public static String id;
	LocalDate currentDate = LocalDate.now();
	 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmss");
	   LocalDateTime now = LocalDateTime.now();
     
	// To get detail which mentioned in testng annotation
	TestDetail tc;
	// It is used to store string passed in Screenshot method
	public static ArrayList<String> Logs = new ArrayList<>();
	// It is used to store path of screenshot passed in Screenshot method
	public static ArrayList<String> ss_paths = new ArrayList<>();
	// It is used to store path of screenshot passed in Screenshot method
	String SS_Path;
	// Store data in excel
	public static String[][] excelDataArray = null;

	public static ExcelUtil data = new ExcelUtil();

	// Environment value fetched from POM with 'careersIn' and 'production' being
	// the valid values
	public static final String ENV = System.getProperty("env", "dev");

	// BROWSER value fetched from POM with Chrome being the default value
	private static final String BROWSER = System.getProperty("browser", "Chrome");

	// Automation suite setup method to configure and instantiate a particular
	// browser
	@BeforeSuite
	public void suiteSetup() throws Exception {

		// Path for Metadafile
		
		// Browser configuration - can add more browsers and remote driver here
		if (BROWSER.equals("Firefox")) {
			WebDriverManager.firefoxdriver().setup(); // can also use set property method for browser executables
			driver = new FirefoxDriver();
		} else if (BROWSER.equals("Chrome")) {
			String s = System.getProperty("user.dir") + "driver\\chromedriver.exe";

			System.setProperty("webdriver.chrome.driver","C:\\Users\\acer\\git\\booking\\driver\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-orgins=*");
			driver = new ChromeDriver(options);

		} else if (BROWSER.equals("IE")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		} else {
			throw new RuntimeException("Browser type unsupported");
		}

		// Setting implicit wait
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.manage().window().maximize();

		// Setting WebDriverWait with max timeout value of 20 seconds
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Environment specific properties file loading

		InputStream configFile = new FileInputStream(System.getProperty("user.dir")
				+ "\\src\\test\\java\\com\\artoftesting\\config\\" + ENV + ".properties");
		envConfig = new Properties();
		envConfig.load(configFile);
		// It is used to create a testng extended report
		extentReport = new ExtentReports();
		// It is used to create a html file in mentioned below location
		htmlReporter = new ExtentSparkReporter("./Output/" + currentDate + "/" + now.format(dtf) + "/CEPT.html");
		// It set report name 
		htmlReporter.config().setReportName("BOOKING SOLUTION");
		htmlReporter.loadXMLConfig(new File("C:/Users/acer/git/booking/src/test/java/com/artoftesting/config/logo.xml"));

		extentReport.attachReporter(htmlReporter);

		// It is used to delete the log folder
		String folderPath = "C:/Users/acer/git/booking/logs";

		// Create a File object for the folder
		File folder = new File(folderPath);

		// Check if the folder exists
		if (folder.exists()) {
			// Delete the folder and its contents
			deleteFolder(folder);
			System.out.println("Folder deleted successfully.");
		} else {
			System.out.println("Folder does not exist.");
		}

	}

	@BeforeMethod()
	public void loadBaseUrl(Method method) {
		driver.get(envConfig.getProperty("baseUrl"));

		tc = method.getAnnotation(TestDetail.class);

		testcase = extentReport.createTest(tc.testCaseName());

		testcase.assignAuthor(tc.author());
		testcase.assignCategory(tc.category());
		testcase.info("Testcase ID -" + tc.testCaseID());
		testcase.info("Module Name -" + tc.module());
		id = tc.testCaseID();

	}

	@AfterMethod
	public void screenshotAndDeleteCookies(ITestResult testResult, Method method) throws IOException {

		for (int i = 0; i < Logs.size(); i++) {
			//check whether screenshot is taken for the log or not
			if (ss_paths.get(i) != "No Screenshot attached")
				// Add  Log message and screenshot to the report
				testcase.pass(Logs.get(i),
						MediaEntityBuilder.createScreenCaptureFromBase64String(ss_paths.get(i)).build());
			else
				// Add only Log message to the report
				testcase.pass(Logs.get(i));

		}
		if (testResult.getStatus() == ITestResult.FAILURE) {
			String scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);

			testcase.fail("fail", MediaEntityBuilder.createScreenCaptureFromBase64String(scrFile).build());
			testcase.fail(testResult.getThrowable());
		}

		Logs.clear();
		ss_paths.clear();

	}

	/**
	 * @ It is used store screenshot in array
	 * 
	 * @param Log    message need to be shown in report with screenshots
	 * @param Number of second to be delayed . It can be zero,if delays is not
	 *               needed
	 *  @param        Based on boolean value it takes screenshot       
	 * @throws IOException
	 * 
	 */

	public void logger(String log, int Seconds, boolean value) {
		if (Seconds != 0) {
			try {
				Thread.sleep(Seconds * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Logs.add(log);
		if (value == true) {
			ss_paths.add(screenshotbase64());
		} else {
			ss_paths.add("No Screenshot attached");
		}

	}

	public String screenshotbase64() {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
	}

	@AfterSuite
	public void suiteTearDown() {
		extentReport.flush();
		driver.quit();
	}

	/**
	 * 
	 * It is method to delete the folder
	 * 
	 * @param Folder location need to be deleted
	 */

	public static void deleteFolder(File folder) {
		if (folder.isDirectory()) {
			// List all files and subdirectories in the folder
			File[] files = folder.listFiles();

			if (files != null) {
				for (File file : files) {
					// Recursively delete files and subdirectories
					deleteFolder(file);
				}
			}
		}

		// Delete the folder itself
		folder.delete();
	}

	public String tcdetail() {
		if (id == null) {
			System.out.println("TC Detail Is null");
		}
		return id;
	}

	/**
	 * 
	 * @param data Pass the String that you want to fetch in Excel
	 * @return Value stored in excel file
	 */
	public static String excelreader(String data) {
		String value = null;
		for (int i = 0; i < excelDataArray.length; i++) {
			for (int j = 0; j <= excelDataArray.length; j++) {
				if (data.equalsIgnoreCase(excelDataArray[i][j])) {
					value = excelDataArray[i + 1][j];
					break;
				}
				if (value != null)
					break;
			}

		}

		return value;

	}

}
