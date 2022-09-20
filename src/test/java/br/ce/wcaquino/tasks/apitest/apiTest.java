package br.ce.wcaquino.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class apiTest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://192.168.5.135:8001/tasks-backend";
	}
	
	@Test
	public void test() {
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200)
		;
	}

	@Test
	public void deveAdicionarTarefaComSucesso() {
		RestAssured.given()
			.body("{ \"task\" : \"Teste via API\", \"dueDate\" : \"2030-12-30\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(201)
		;
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		RestAssured.given()
			.body("{ \"task\" : \"Teste via API\", \"dueDate\" : \"2020-12-30\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))
		;
	}
}
