package lazadaPage;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LazadaPage_Login {
	WebDriver driver;
	
	//Dinh nghia cac WebElement
	By txtEmail = By.id("LoginForm_email");
	By msgEmail = By.xpath("//input[@id=\"LoginForm_email\"]/following::div[@class=\"s-error msg\"]");
	By txtPassword = By.id("LoginForm_password");
	By msgPassword = By.xpath("//input[@id=\"LoginForm_password\"]/following::div[@class=\"s-error msg\"]");
	By btnLogin = By.cssSelector("button.ui-buttonCta");
	
	By btnFacebook = By.xpath("//div[@class='fb-wrapper']/a");
	By txtEmailFacebook = By.id("email");
	By txtPassFacebook = By.id("pass");
	By btnLoginFacebook = By.id("u_0_0");
	By btnAllowFacebook = By.cssSelector("button._51sy");
	
	By btnGoogle = By.xpath("//div[@class='google-wrapper']/div/a");
	By txtEmailGoogle = By.id("identifierId");
	By btnNextGoogle1 = By.id("identifierNext");
	By txtPassGoogle = By.xpath("//div[@id='password']/div[1]/div/div[1]/input");
	By btnNextGoogle2 = By.id("passwordNext");
	By btnAllowGoogle = By.xpath("//div[@id='submit_approve_access']/div[2]");
	
	By toggle_logout = By.cssSelector("p.dropdown__toggle_type_text");
	By logout = By.linkText("Đăng xuất");
	
	//Dinh nghia cac phuong thuc
	public LazadaPage_Login(WebDriver driver) {
		this.driver = driver;
	}
	
	//Phuong thuc on page
	public void inputEmail(String email) {
		WebElement emailField = driver.findElement(txtEmail);
		emailField.clear();
		emailField.sendKeys(email);
	}
	
	//methods: login with form login
	public void inputPassword(String password) {
		WebElement passwordField = driver.findElement(txtPassword);
		passwordField.clear();
		passwordField.sendKeys(password);
	}
	
	public void submit() {
		driver.findElement(btnLogin).click();
	}
	
	public String getMessageEmail() {
		WebElement messEmail = driver.findElement(msgEmail);
		return messEmail.getText();
	}
	
	public String getMessagePassword() {
		WebElement messPassword = driver.findElement(msgPassword);
		return messPassword.getText();
	}
	
	//methods: login with facebook account
	public void clickFacebookButton() {
		driver.findElement(btnFacebook).click();
	}
	
	public void inputEmailFacebook(String email) {
		WebElement emailFacebookField = driver.findElement(txtEmailFacebook);
		emailFacebookField.clear();
		emailFacebookField.sendKeys(email);
	}
	
	public void inputPassFacebook(String password) {
		WebElement passFacebookField = driver.findElement(txtPassFacebook);
		passFacebookField.clear();
		passFacebookField.sendKeys(password);
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
	
	//methods: login with google+ account
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
	}
	
	public void checkLink(String linkTest) {
		driver.findElement(By.linkText(linkTest)).click();
	}
	
}
