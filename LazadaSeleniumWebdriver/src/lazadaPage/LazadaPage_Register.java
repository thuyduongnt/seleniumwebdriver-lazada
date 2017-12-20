package lazadaPage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LazadaPage_Register {
	WebDriver driver;
	
	//define WebElements
	By rdbMale = By.id("RegistrationForm_gender_0");
	By rdbFemale = By.id("RegistrationForm_gender_1");
	By txtEmail = By.id("RegistrationForm_email");
	By msgEmail = By.xpath("//input[@id='RegistrationForm_email']/following::div[@class='s-error']");
	By txtName = By.id("RegistrationForm_first_name");
	By msgName = By.xpath("//input[@id='RegistrationForm_first_name']/following::div[@class='s-error']");
	By selectDay = By.id("RegistrationForm_day");
	By selectMonth = By.id("RegistrationForm_month");
	By selectYear = By.id("RegistrationForm_year");
	By txtPassword1 = By.id("RegistrationForm_password");
	By msgPassword1 = By.xpath("//input[@id='RegistrationForm_password']/following::div[@class='s-error']");
	By txtPassword2 = By.id("RegistrationForm_password2");
	By msgPassword2 = By.xpath("//input[@id='RegistrationForm_password2']/following::div[@class='s-error']");
	By btnRegisterForm = By.id("send");

	By toggle_logout = By.cssSelector("p.dropdown__toggle_type_text");
	By logout = By.linkText("Đăng xuất");
	
	By btnFacebook = By.xpath("//div[@class='fb-wrapper']/a");
	By txtEmailFacebook = By.id("email");
	By txtPassFacebook = By.id("pass");
	By btnLoginFacebook = By.id("u_0_0");
	By btnAllowFacebook = By.cssSelector("button._51sy");
	
	By btnGoogle = By.xpath("//div[@class='google-wrapper']/div/a[@id='google-login-button']");
	By txtEmailGoogle = By.id("identifierId");
	By btnNextGoogle1 = By.id("identifierNext");
	By txtPassGoogle = By.xpath("//div[@id='password']/div[1]/div/div[1]/input");
	By btnNextGoogle2 = By.id("passwordNext");
	By btnAllowGoogle = By.xpath("//div[@id='submit_approve_access']/div[2]");
	
	//define methods
	public LazadaPage_Register(WebDriver driver) {
		this.driver = driver;
	}
	
	public void selectGender(String gender) {
		WebElement male = driver.findElement(rdbMale);
		WebElement female = driver.findElement(rdbFemale);
		
		if(male.isEnabled() && female.isEnabled()) {
			switch(gender) {
				case "Nam":
					male.click();
					break;
				case "Nữ":
					female.click();
					break;
			}
		}
	}
	
	public void inputEmail(String email) {
		WebElement emailField = driver.findElement(txtEmail);
		emailField.clear();
		emailField.sendKeys(email);
	}
	
	public String getMessageEmail() {
		if(driver.findElement(msgEmail).isDisplayed()) {
			WebElement msgEmailField = driver.findElement(msgEmail);
			return msgEmailField.getText();
		}
		else
			return "";
	}
	
	public void inputName(String name) {
		WebElement nameField = driver.findElement(txtName);
		nameField.clear();
		nameField.sendKeys(name);
	}
	
	public String getMessageName() {
		if(driver.findElement(msgName).isDisplayed()) {
			WebElement msgNameField = driver.findElement(msgName);
			return msgNameField.getText();
		}
		else
			return "";
	}
	/*
	public void selectDayOfBirth(String day) throws InterruptedException {
		WebElement selectDayField = driver.findElement(selectDay);
		
		Select sday = new Select(selectDayField);
		sday.selectByVisibleText(day);
		Thread.sleep(5000);
	}
	
	public void selectMonthOfBirth(String month) throws InterruptedException {
		WebElement selectMonthField = driver.findElement(selectMonth);
		
		Select smonth = new Select(selectMonthField);
		smonth.selectByVisibleText(month);
		Thread.sleep(5000);
	}
	
	public void selectYearOfBirth(String year) throws InterruptedException {
		WebElement selectYearField = driver.findElement(selectYear);
		
		Select syear = new Select(selectYearField);
		syear.selectByVisibleText(year);
		Thread.sleep(5000);
	}
	*/
	public void inputPassword1(String password1) {
		WebElement passwordField1 = driver.findElement(txtPassword1);
		passwordField1.clear();
		passwordField1.sendKeys(password1);
	}
	
	public String getMessagePassword1() {
		if(driver.findElement(msgPassword1).isDisplayed()) {
			WebElement msgPasswordField1 = driver.findElement(msgPassword1);
			return msgPasswordField1.getText();
		}
		else
			return "";
	}

	public void inputPassword2(String password2) {
		//JavascriptExecutor jse = (JavascriptExecutor) driver;
		//jse.executeScript("scrollBy(0,200)", "");
		
		WebElement passwordField2 = driver.findElement(txtPassword2);
		passwordField2.clear();
		passwordField2.sendKeys(password2);
	}
	
	public String getMessagePassword2() {
		if(driver.findElement(msgPassword2).isDisplayed()) {
			WebElement msgPasswordField2 = driver.findElement(msgPassword2);
			return msgPasswordField2.getText();
		}
		else
			return "";
	}
	
	public void clickRegister() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scrollBy(0,200)", "");
		
		driver.findElement(btnRegisterForm).click();
	}
	
	//Register with Facebook account
	public void waitButtonFacebook() {
		WebDriverWait waiter = new WebDriverWait(driver, 10);
		waiter.until(ExpectedConditions.visibilityOfElementLocated(btnFacebook));
	}
	
	public void clickFacebookButton() {
		driver.findElement(btnFacebook).click();
	}
	
	public void inputEmailFacebook(String email) {
		WebElement emailFacebook = driver.findElement(txtEmailFacebook);
		emailFacebook.clear();
		emailFacebook.sendKeys(email);
	}
	
	public void inputPassFacebook(String password) {
		WebElement passFacebook = driver.findElement(txtPassFacebook);
		passFacebook.clear();
		passFacebook.sendKeys(password);
	}
	
	public void clickLoginFacebook() {
		driver.findElement(btnLoginFacebook).click();
	}

	public void waitAllowButtonFacebook() {
		WebDriverWait waiter = new WebDriverWait(driver, 10);
		waiter.until(ExpectedConditions.visibilityOfElementLocated(btnAllowFacebook));
	}
	
	public boolean clickAllowButtonFacebook() {
		if(driver.findElement(btnAllowFacebook).isDisplayed()) {
			driver.findElement(btnAllowFacebook).click();
			return true;
		}
		else
			return false;
	}

	//Register with Google account
	public void clickGoogleButton() {
		driver.findElement(btnGoogle).click();
	}

	public void inputEmailGoogle(String email) {
		WebElement emailGoogleField = driver.findElement(txtEmailGoogle);
		emailGoogleField.clear();
		emailGoogleField.sendKeys(email);
	}
	
	public void clickNextToPasswordGoogle() {
		driver.findElement(btnNextGoogle1).click();
	}
	
	public void waitPasswordFieldVisible() {
		WebDriverWait waiter = new WebDriverWait(driver, 15);
		waiter.until(ExpectedConditions.visibilityOfElementLocated(txtPassGoogle));
	}
	
	public void inputPasswordGoogle(String password) {
		WebElement passGoogleField = driver.findElement(txtPassGoogle);
		passGoogleField.clear();
		passGoogleField.sendKeys(password);
	}
	
	public void clickNextToLoginGoogle() {
		driver.findElement(btnNextGoogle2).click();
	}
	
	public boolean clickAllowButtonGoogle() {
		if(driver.findElement(btnAllowGoogle).isDisplayed()) {
			driver.findElement(btnAllowGoogle).click();
			return true;
		}
		else
			return false;
	}
	
	public void waitConnectAccount(String expectedTitle) {
		WebDriverWait waiter = new WebDriverWait(driver, 20);
		waiter.until(ExpectedConditions.titleIs(expectedTitle));
	}
	
	
	public void logoutAccount() {
		WebElement account = driver.findElement(toggle_logout);
		account.click();
		
		WebElement ddlLogout = driver.findElement(logout);
		ddlLogout.click();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
}
