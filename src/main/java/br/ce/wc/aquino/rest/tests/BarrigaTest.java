package br.ce.wc.aquino.rest.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.rest.core.BaseTest;;

public class BarrigaTest extends BaseTest{
	
	private String TOKEN;
	
	@Before
	public void login() {
		Map<String, String> login = new HashMap<>();
		login.put("email", "jorge.wellison@dcx.ufpb.br");
		login.put("senha", "328406");
		
		TOKEN = given()
			.body(login)
		.when()
			.post("/signin")
		.then()
			.statusCode(200)
			.extract().path("token");
	}
	
	@Test
	public void naoDeveAcessarAPISemToken() {
		given()
		.when()
			.get("/contas")
		.then()
			.statusCode(401);
		;
	}
	
	@Test
	public void deveIncluirContaComSucesso() {
		given()
			.header("Authorization", "JWT " + TOKEN)
			.body("{ \"nome\": \"conta qualquer\" }")	
		.when()
			.post("/contas")
		.then()
			.statusCode(201);
	}
	
	@Test
	public void deveAlterarContaComSucesso() {

		given()
			.header("Authorization", "JWT " + TOKEN)
			.body("{ \"nome\": \"conta alterada\" }")	
		.when()
			.put("/contas/46139")
		.then()
			.log().all()
			.statusCode(200)
			.body("nome", is("conta alterada"));
	}
}
