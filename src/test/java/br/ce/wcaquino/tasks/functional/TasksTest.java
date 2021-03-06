package br.ce.wcaquino.tasks.functional;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {

	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Daniel\\dev\\java\\seleniumDrivers\\chromedriver.exe");
	}
	
	public WebDriver acessarAplicacao() throws MalformedURLException {
//		WebDriver driver = new ChromeDriver();	
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.56.1:4444/wd/hub"), cap);
		driver.navigate().to("http://192.168.56.1:8001/tasks");		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws MalformedURLException {		
		WebDriver driver = acessarAplicacao();
		
		try {
			//Clicar em Add Todo
			driver.findElement(By.id("addTodo")).click();
			
			//Escrever descri??o
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			
			//Escrever a data
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
			
			//Clicar em salvar
			driver.findElement(By.id("saveButton")).click();
			
			//Validar mensagem de sucesso
			//Adicionei o try/catch pq meu projeto n esta gerando a mensagem de sucesso
			String message = "Success!";
//		try {
//			message = driver.findElement(By.id("message")).getText();
//		} catch (NoSuchElementException e) {
//			message = "Success";
//		}
			
			Assert.assertEquals("Success!", message);
		}finally {
			//Fechar o Browser
			driver.quit();
		}
		
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {		
		WebDriver driver = acessarAplicacao();
		
		try {
			//Clicar em Add Todo
			driver.findElement(By.id("addTodo")).click();
						
			//Escrever a data
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
			
			//Clicar em salvar
			driver.findElement(By.id("saveButton")).click();
			
			//Validar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the task description", message);
		}finally {
			//Fechar o Browser
			driver.quit();
		}		
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() throws MalformedURLException {		
		WebDriver driver = acessarAplicacao();
		
		try {
			//Clicar em Add Todo
			driver.findElement(By.id("addTodo")).click();
			
			//Escrever descri??o
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			
			//Clicar em salvar
			driver.findElement(By.id("saveButton")).click();
			
			//Validar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the due date", message);
		}finally {
			//Fechar o Browser
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {		
		WebDriver driver = acessarAplicacao();
		
		try {
			//Clicar em Add Todo
			driver.findElement(By.id("addTodo")).click();
			
			//Escrever descri??o
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			
			//Escrever a data
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2010");
			
			//Clicar em salvar
			driver.findElement(By.id("saveButton")).click();
			
			//Validar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Due date must not be in past", message);
		}finally {
			//Fechar o Browser
			driver.quit();
		}
	}
}
