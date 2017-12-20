package lazadaTest;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import helper.*;
import lazadaPage.LazadaPage_Register;

public class LazadaTest_Register {
	WebDriver driver;
	
	String filePath = "E:\\AutomationTest\\ProjectJava\\LazadaSeleniumWebdriver\\lazadaTestData\\TestDataLazada.xlsx";
	
	@BeforeTest
	public void initTest() {
		System.setProperty("webdriver.chrome.driver", "E:\\AutomationTest\\Tools\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	
	@BeforeMethod
	public void initMethod() {
		String baseUrl = "https://www.lazada.vn/customer/account/create";
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@DataProvider(name="data")
	public Object[][] getData(Method name) throws Exception{
		Object[][] data = null;
		
		String sheetName = "";
		
		if(name.getName().equals("registerForm")) {
			sheetName = "3.1. ĐK_Form đăng ký";
			data = TestExcelUtil.testData(filePath, sheetName);
		}
		else if(name.getName().equals("registerWithFacebookOrGoogle")) {
			sheetName = "3.2. ĐK_Tài khoản liên kết";
			data = TestExcelUtil.testData(filePath, sheetName);
		}
		
		return data;
	}
	
	@Test(priority=1, dataProvider="data")
	public void registerForm(String id_case, String titleCase, String gender, String email, String msgEmail, String name, String msgName, String password1, String msgPassword1, String password2, String msgPassword2, String expectedTitle, String testResult) throws Exception {
		LazadaPage_Register register = new LazadaPage_Register(driver);
		ScreenshotUtil capture = new ScreenshotUtil(driver);
		
		String sheetName = "3.1. ĐK_Form đăng ký";
		String colName = "Status";
		String source = "E:\\AutomationTest\\ProjectJava\\LazadaSeleniumWebdriver\\lazadaScreenshot\\screenshot_Register\\" + id_case + "_Fail_" + titleCase + ".jpg";
		
		register.selectGender(gender);
		register.inputEmail(email);
		register.inputName(name);
		//register.selectDayOfBirth(day);
		//register.selectMonthOfBirth(month);
		//register.selectYearOfBirth(year);
		register.inputPassword1(password1);
		register.inputPassword2(password2);
		register.clickRegister();
		
		if(msgEmail!="" && msgName!="" && msgPassword1!="" && msgPassword2!="") {
			boolean resultEmail = register.getMessageEmail().equalsIgnoreCase(msgEmail);
			boolean resultName = register.getMessageName().equalsIgnoreCase(msgName);
			boolean resultPassword1 = register.getMessagePassword1().equalsIgnoreCase(msgPassword1);
			boolean resultPassword2 = register.getMessagePassword2().equalsIgnoreCase(msgPassword2);
			
			boolean result = false;
			
			if(resultEmail==true && resultName==true && resultPassword1==true && resultPassword2==true)
				result = true;
			else
				result = false;
			
			TestExcelUtil.writeResult(filePath, sheetName, colName, id_case, result);
			capture.takeScreenshot(result, source);
			
			Assert.assertEquals(register.getMessageEmail(), msgEmail);
			Assert.assertEquals(register.getMessageName(), msgName);
			Assert.assertEquals(register.getMessagePassword1(), msgPassword1);
			Assert.assertEquals(register.getMessagePassword2(), msgPassword2);
		}
		else if(msgEmail!="") {
			boolean result = register.getMessageEmail().equalsIgnoreCase(msgEmail);
			
			TestExcelUtil.writeResult(filePath, sheetName, colName, id_case, result);
			capture.takeScreenshot(result, source);
			
			Assert.assertEquals(register.getMessageEmail(), msgEmail);
		}
		else if(msgName!="") {
			boolean result = register.getMessageName().equalsIgnoreCase(msgName);
			
			TestExcelUtil.writeResult(filePath, sheetName, colName, id_case, result);
			capture.takeScreenshot(result, source);
			
			Assert.assertEquals(register.getMessageName(), msgName);
		}
		else if(msgPassword1!="" && msgPassword2!="") {
			boolean result1 = register.getMessagePassword1().equalsIgnoreCase(msgPassword1);
			boolean result2 = register.getMessagePassword2().equalsIgnoreCase(msgPassword2);
			
			boolean result = false;
			if(result1==true && result2==true)
				result = true;
			else 
				result = false;
			
			TestExcelUtil.writeResult(filePath, sheetName, colName, id_case, result);
			capture.takeScreenshot(result, source);
			
			Assert.assertEquals(register.getMessagePassword1(), msgPassword1);
			Assert.assertEquals(register.getMessagePassword2(), msgPassword2);
		}
		else if(msgEmail=="" && msgName=="" && msgPassword1=="" && msgPassword2=="") {
			String actualTitle = driver.getTitle();
			boolean result = actualTitle.equalsIgnoreCase(expectedTitle);
			
			TestExcelUtil.writeResult(filePath, sheetName, colName, id_case, result);
			capture.takeScreenshot(result, source);
			
			Assert.assertEquals(actualTitle, expectedTitle);
			if(result)
				register.logoutAccount();
		}
	}
	
	@Test(priority=2, dataProvider="data")
	public void registerWithFacebookOrGoogle(String id_case, String titleCase, String typeAccount, String email, String password, String expectedTitle, String testResult) throws Exception {
		LazadaPage_Register register = new LazadaPage_Register(driver);
		ScreenshotUtil capture = new ScreenshotUtil(driver);
		String sheetName = "3.2. ĐK_Tài khoản liên kết";
		String colName = "Status";
		String source = "E:\\AutomationTest\\ProjectJava\\LazadaSeleniumWebdriver\\lazadaScreenshot\\screenshot_Register\\" + id_case + "_Fail_" + titleCase + ".jpg";
		
		String baseHandle = driver.getWindowHandle();
		String handle = "";
		
		if(typeAccount.equalsIgnoreCase("Facebook")) {
			register.waitButtonFacebook();
			register.clickFacebookButton();
			
			Set<String> listHandles = driver.getWindowHandles();
			for(String elHandle : listHandles) {
				handle = elHandle;
			}
			driver.switchTo().window(handle);
			register.inputEmailFacebook(email);
			register.inputPassFacebook(password);
			register.clickLoginFacebook();
			
			driver.switchTo().window(baseHandle);
			register.waitConnectAccount(expectedTitle);
			
			boolean result = driver.getTitle().equals(expectedTitle);
			TestExcelUtil.writeResult(filePath, sheetName, colName, id_case, result);
			capture.takeScreenshot(result, source);
			Assert.assertEquals(driver.getTitle(), expectedTitle);
			if(result)
				register.logoutAccount();
		}
		else if(typeAccount.equalsIgnoreCase("Google+")) {
			register.clickGoogleButton();

			Set<String> listHandles = driver.getWindowHandles();
			for(String elHandle : listHandles) {
				handle = elHandle;
			}
			driver.switchTo().window(handle);
			register.inputEmailGoogle(email);
			register.clickNextToPasswordGoogle();
			register.waitPasswordFieldVisible();
			register.inputPasswordGoogle(password);
			register.clickNextToLoginGoogle();
			
			driver.switchTo().window(baseHandle);
			register.waitConnectAccount(expectedTitle);
			
			boolean result = driver.getTitle().equals(expectedTitle);
			TestExcelUtil.writeResult(filePath, sheetName, colName, id_case, result);
			capture.takeScreenshot(result, source);
			Assert.assertEquals(driver.getTitle(), expectedTitle);
			if(result)
				register.logoutAccount();
		}
	}
	
	@AfterTest
	public void finishTest() {
		driver.quit();
	}
}
