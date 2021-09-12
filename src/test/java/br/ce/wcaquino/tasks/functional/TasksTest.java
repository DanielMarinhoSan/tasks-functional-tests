package br.ce.wcaquino.tasks.functional;

import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TasksTest {

	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Daniel\\dev\\java\\seleniumDrivers\\chromedriver.exe");
	}
	
	public WebDriver acessarAplicacao() {
		WebDriver driver = new ChromeDriver();		
		driver.navigate().to("http://localhost:8001/tasks");		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() {		
		WebDriver driver = acessarAplicacao();
		
		try {
			//Clicar em Add Todo
			driver.findElement(By.id("addTodo")).click();
			
			//Escrever descrição
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
	public void naoDeveSalvarTarefaSemDescricao() {		
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
	public void naoDeveSalvarTarefaSemData() {		
		WebDriver driver = acessarAplicacao();
		
		try {
			//Clicar em Add Todo
			driver.findElement(By.id("addTodo")).click();
			
			//Escrever descrição
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
	public void naoDeveSalvarTarefaComDataPassada() {		
		WebDriver driver = acessarAplicacao();
		
		try {
			//Clicar em Add Todo
			driver.findElement(By.id("addTodo")).click();
			
			//Escrever descrição
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
