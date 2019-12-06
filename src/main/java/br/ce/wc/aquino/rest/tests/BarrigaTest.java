package br.ce.wc.aquino.rest.tests;

import static io.restassured.RestAssured.given;

import org.junit.Test;

import br.ce.wcaquino.rest.core.BaseTest;;

public class BarrigaTest extends BaseTest{
	
	@Test
	public void naoDeveAcessarAPISemToken() {
		given()
		.when()
			.get("/contas")
		.then()
			.statusCode(400);
		;
	}
}
