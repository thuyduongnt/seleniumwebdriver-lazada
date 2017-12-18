package lazadaTest;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.*;
import org.testng.annotations.*;

import helper.*;
import lazadaPage.LazadaPage_Login;

public class LazadaTest_Login {
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
		String baseUrl = "https://www.lazada.vn/customer/account/login";
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	//Data
	@DataProvider(name="data")
	public Object[][] getData(Method name) throws Exception {
		String sheetName = "";
		
		Object[][] data = null;
		
		if(name.getName().equals("checkLinks")) {
			sheetName = "2.1. ĐN_Kiểm tra links";
			data = TestExcelUtil.testData(filePath, sheetName);
		}
		else if(name.getName().equals("loginForm")) {
			sheetName = "2.2. ĐN_Form đăng nhập";
			data = TestExcelUtil.testData(filePath, sheetName);
		}
		else if(name.getName().equals("loginWithGoogleOrFacebook")) {
			sheetName = "2.3. ĐN_Tài khoản liên kết";
			data = TestExcelUtil.testData(filePath, sheetName);
		}
		
		return data;
	}
	
	/*
	@Test(priority=1, dataProvider="data")
	public void checkLinks(String id_case, String linkText, String expectedTitle, String testResult) throws Exception {
		LazadaPage_Login login = new LazadaPage_Login(driver);
		ScreenshotUtil capture = new ScreenshotUtil(driver);
		
		login.checkLink(linkText);
		
		String actualTitle = driver.getTitle();
		boolean result = actualTitle.equals(expectedTitle);
		
		String sheetName = "2.1. ĐN_Kiểm tra links";
		String colName = "Status";
		
		TestExcelUtil.writeResult(filePath, sheetName, colName, id_case, result);
		capture.takeScreenshot(result, "E:\\AutomationTest\\ProjectJava\\LazadaSeleniumWebdriver\\lazadaScreenshot\\screenshot_Login\\" + id_case + "_Fail_" + actualTitle + ".jpg");
		
		Assert.assertEquals(actualTitle, expectedTitle);
	}
	
	@Test(priority=2, dataProvider="data")
	public void loginForm(String id_case, String email, String msgEmail, String password, String msgPassword, String expectedTitle, String testResult) throws Exception {
		LazadaPage_Login login = new LazadaPage_Login(driver);
		ScreenshotUtil capture = new ScreenshotUtil(driver);
		
		login.inputEmail(email);
		login.inputPassword(password);
		login.submit();
		
		String sheetName = "2.2. ĐN_Form đăng nhập";
		String colName = "Status";
		
		if(msgEmail != "") {
			boolean result = login.getMessageEmail().equals(msgEmail);
			
			TestExcelUtil.writeResult(filePath, sheetName, colName, id_case, result);
			
			capture.takeScreenshot(result, "E:\\AutomationTest\\ProjectJava\\LazadaSeleniumWebdriver\\lazadaScreenshot\\screenshot_Login\\" + id_case + "_Fail_" + login.getMessageEmail() + ".jpg");
			
			Assert.assertEquals(login.getMessageEmail(), msgEmail);
		}
		else if(msgPassword != "") {
			boolean result = login.getMessagePassword().equals(msgPassword);
			
			TestExcelUtil.writeResult(filePath, sheetName, colName, id_case, result);
			
			capture.takeScreenshot(result, "E:\\AutomationTest\\ProjectJava\\LazadaSeleniumWebdriver\\lazadaScreenshot\\screenshot_Login\\" + id_case + "_Fail_" + login.getMessagePassword() + ".jpg");
			
			Assert.assertEquals(login.getMessagePassword(), msgPassword);
		}
		else if(msgEmail == "" && msgPassword == "") {
			String actualTitle = driver.getTitle();
			boolean result = actualTitle.equals(expectedTitle);
			
			capture.takeScreenshot(result, "E:\\AutomationTest\\ProjectJava\\LazadaSeleniumWebdriver\\lazadaScreenshot\\screenshot_Login\\" + id_case + "_Fail_" + actualTitle + ".jpg");
			
			TestExcelUtil.writeResult(filePath, sheetName, colName, id_case, actualTitle.equals(expectedTitle));
			
			Assert.assertEquals(actualTitle, expectedTitle);
			if(result) {
				login.logoutAccount();
			}
		}
	}
	*/
	@Test(priority=3, dataProvider="data")
	public void loginWithGoogleOrFacebook(String id_case, String typeAccount, String email, String password, String expectedTitle, String testResult) throws Exception {
		LazadaPage_Login login = new LazadaPage_Login(driver);
		ScreenshotUtil capture = new ScreenshotUtil(driver);
		
		String sheetName = "2.3. ĐN_Tài khoản liên kết";
		String colName = "Status";
		
		String baseHandle = driver.getWindowHandle();
		String handle = "";
		
		if(typeAccount.equalsIgnoreCase("Facebook")) {
			login.clickFacebookButton();
			
			Set<String> listHandle = driver.getWindowHandles();
			for(String elHandle : listHandle) {
				handle = elHandle;
			}
			driver.switchTo().window(handle);
			
			login.inputEmailFacebook(email);
			login.inputPassFacebook(password);
			login.clickLoginFacebook();
			//login.waitAllowButtonFacebook();
			//login.clickAllowButtonFacebook();
			
			driver.switchTo().window(baseHandle);
			
			login.waitConnectAccountFacebookToLazada(expectedTitle);
			
			String actualTitle = driver.getTitle();
			boolean result = actualTitle.equals(expectedTitle);
			
			capture.takeScreenshot(result, "E:\\AutomationTest\\ProjectJava\\LazadaSeleniumWebdriver\\lazadaScreenshot\\screenshot_Login\\" + id_case + "_Fail_" + actualTitle + ".jpg");
			
			TestExcelUtil.writeResult(filePath, sheetName, colName, id_case, result);
			
			Assert.assertEquals(actualTitle, expectedTitle);
			
			if(result) {
				login.logoutAccount();
			}
		}
		else if(typeAccount.equalsIgnoreCase("Google+")) {
			login.clickGoogleButton();

			Set<String> listHandle = driver.getWindowHandles();
			for(String elHandle : listHandle) {
				handle = elHandle;
			}
			driver.switchTo().window(handle);
			
			login.inputEmailGoogle(email);
			login.clickNextToPasswordGoogle();
			login.waitPasswordFieldVisible();
			login.inputPasswordGoogle(password);
			login.clickNextToLoginGoogle();
			
			driver.switchTo().window(baseHandle);
			
			login.waitConnectAccountGoogle(expectedTitle);
			
			String actualTitle = driver.getTitle();
			boolean result = actualTitle.equals(expectedTitle);
			

			capture.takeScreenshot(result, "E:\\AutomationTest\\ProjectJava\\LazadaSeleniumWebdriver\\lazadaScreenshot\\screenshot_Login\\" + id_case + "_Fail_" + actualTitle + ".jpg");
			
			TestExcelUtil.writeResult(filePath, sheetName, colName, id_case, result);
			
			Assert.assertEquals(actualTitle, expectedTitle);
			
			if(result) {
				login.logoutAccount();
			}
		}
	}

	
	@AfterTest
	public void finishTest() {
		driver.quit();
	}

}
