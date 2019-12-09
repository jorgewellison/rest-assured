package br.ce.wc.aquino.rest.tests;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import br.ce.wcaquino.rest.core.BaseTest;;

public class BarrigaTest extends BaseTest{
	
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
		Map<String, String> login = new HashMap<>();
		login.put("email", "jorge.wellison@dcx.ufpb.br");
		login.put("senha", "328406");
		
		String token = given()
			.body(login)
		.when()
			.post("/signin")
		.then()
			.statusCode(200)
			.extract().path("token");
		
		given()
			.header("Authorization", "JWT " + token)
			.body("{ \"nome\": \"conta qualquer\" }")	
		.when()
			.post("/contas")
		.then()
			.statusCode(201);
	}
}
