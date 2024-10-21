package com.montanha.gerenciador.viagens;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;


public class ViagensTest {
    @Test
    public void testDadoUmAdministradorQuandoCadastroViagensEntaoObtenhoStatusCode201() {
        //configurar o caminho
        baseURI = "http://localhost";
        port = 8089;
        basePath = "/api";

        //login na api rest como admin
        String token = given()
                .body("{\n" +
                        "  \"email\": \"admin@email.com\",\n" +
                        "  \"senha\": \"654321\"\n" +
                        "}")
                    .contentType(ContentType.JSON)
                .when()
                    .post("/v1/auth")
                .then()
                .log().all()
                .extract()
                .path("data.token");


        //Cadastrar a viagem //validar que o status code é 201 created
        given()
                .header("Authorization", token)
                .body("{\n" +
                        "  \"acompanhante\": \"Isabelle\",\n" +
                        "  \"dataPartida\": \"2024-10-15\",\n" +
                        "  \"dataRetorno\": \"2024-11-15\",\n" +
                        "  \"localDeDestino\": \"Manaus\",\n" +
                        "  \"regiao\": \"Norte\"\n" +
                        "}")
                .contentType(ContentType.JSON)
        .when()
                .post("/v1/viagens")
        .then()
                .log().all()
                .assertThat()
                .statusCode(201);




    }

}
