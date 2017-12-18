package lazadaTest;

import java.lang.reflect.Method;
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
	
	
	@Test(priority=1, dataProvider="data")
	public void checkLinks(String id_case, String linkText, String expectedTitle, String testResult) throws Exception {
		LazadaPage_Login login = new LazadaPage_Login(driver);
		
		login.checkLink(linkText);
		
		String sheetName = "2.1. ĐN_Kiểm tra links";
		String colName = "Status";
		
		TestExcelUtil.writeResult(filePath, sheetName, colName, id_case, driver.getTitle().equals(expectedTitle));
		
		Assert.assertEquals(driver.getTitle(), expectedTitle);
	}
	
	@Test(priority=2, dataProvider="data")
	public void loginForm(String id_case, String email, String msgEmail, String password, String msgPassword, String expectedTitle, String testResult) throws Exception {
		LazadaPage_Login login = new LazadaPage_Login(driver);
		ScreenshotUtils capture = new ScreenshotUtils(driver);
		
		login.inputEmail(email);
		login.inputPassword(password);
		login.submit();
		
		String sheetName = "2.2. ĐN_Form đăng nhập";
		String colName = "Status";
		
		if(msgEmail != "") {
			TestExcelUtil.writeResult(filePath, sheetName, colName, id_case, login.getMessageEmail().equals(msgEmail));
			Assert.assertEquals(login.getMessageEmail(), msgEmail);
		}
		else if(msgPassword != "") {
			TestExcelUtil.writeResult(filePath, sheetName, colName, id_case, login.getMessagePassword().equals(msgPassword));
			Assert.assertEquals(login.getMessagePassword(), msgPassword);
		}
		else if(msgEmail == "" && msgPassword == "") {
			String actualTitle = driver.getTitle();
			
			if(actualTitle.compareTo(expectedTitle)!=0) {
				capture.CaptureMethod("E:\\AutomationTest\\ProjectJava\\SeleniumWebdriver\\lazadaScreenshot\\screenshot_login\\" + id_case + "_(" + email + ") va (" + password + ").jpg");
			}
			TestExcelUtil.writeResult(filePath, sheetName, colName, id_case, actualTitle.equals(expectedTitle));
			Assert.assertEquals(actualTitle, expectedTitle);
			
			login.logoutAccount();
		}
	}
	
	@Test(priority=3, dataProvider="data")
	public void loginWithGoogleOrFacebook(String id_case, String typeAccount, String email, String password, String expectedTitle, String testResult) {
		if(typeAccount.equalsIgnoreCase("Facebook")) {
			
		}
		else if(typeAccount.equalsIgnoreCase("Google+")) {
			
		}
	}

	
	@AfterTest
	public void finishTest() {
		driver.quit();
	}

}
