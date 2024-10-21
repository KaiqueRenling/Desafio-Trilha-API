package com.cgitar;

import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matcher.*;
import static org.hamcrest.core.IsEqual.equalTo;

public class CgitarTests {

    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c3VhcmlvaWQiOiIxMjI3IiwidXN1YXJpb25vbWUiOiJLYWlxdWUgRG92YWwgUmVubGluZyIsInVzdWFyaW9lbWFpbCI6ImthaXF1ZTE5OTZAZ21haWwuY29tIiwidXN1YXJpb3RlbGVmb25lIjoiNTE5OTM5ODkxMDgifQ.qwKoZU3gl_IkYxGRjnSORMDgQNpFZpRDBfYqcoAbnlg";

    @Before
    public void setup(){
        baseURI = "http://165.227.93.41/cgitar";

    }



    @Test
    public void testDadoCrioNovoUsuarioEntaoObtenhoStatusCode201(){

        String usuario = "{\n" +
                "  \"usuarioNomeCompleto\": \"Kaique Doval Renling\",\n" +
                "  \"usuarioEmail\": \"kaiqued1996@gmail.com\",\n" +
                "  \"usuarioSenha\": \"54321\",\n" +
                "  \"usuarioTelefone\": \"51993989108\"\n" +
                "}";
        given()
                .body(usuario)
                .contentType(ContentType.JSON)
        .when()
                    .post("/usuarios")
        .then()
                .statusCode(201)
                .body("data.usuarioEmail", equalTo("kaiqued1996@gmail.com"))
                .log().all();

    }

    @Test
    public void testDadoqueAutenticonaAPIEntaoOtenhoStatus200(){
        token = given()
                .body("{\n" +
                        "  \"usuarioEmail\": \"kaique1996@gmail.com\",\n" +
                        "  \"usuarioSenha\": \"54321\"\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                .post("/autenticacao")
                .then()
                .statusCode(200)
                .log().all()
                .extract()
                .path("data.token");
    }



    @Test
    public void testDadoQuePreenchoOTokenEntaoObtenhoPerguntas(){
        given()
                .header("token", token)
                .contentType(ContentType.JSON)
            .when()
                .get("/perguntas")
            .then()
                .assertThat()
                .statusCode(200)
                .log().all();


    }


    @Test
    public void testDadoQueRespondoUmaPerguntaEntaoObtenhoStatus201(){
        given()
                .header("token", token)
                .body("{\n" +
                        "  \"perguntaId\": 25,\n" +
                        "  \"respostaDada\": true\n" +
                        "}")
                .contentType(ContentType.JSON)
            .when()
                .post("/respostas")
            .then()
                .assertThat()
                .statusCode(201)
                .log().all();
    }

    @Test
    public void testDadoQuevisualizoTodasAsRespostasEntaoDeveSerObtidoStatus200(){
        given()
                .header("token", token)
                .contentType(ContentType.JSON)
            .when()
                .get("/respostas")
            .then()
                .assertThat()
                .statusCode(200)
                .log().all();
    }
    @Test
    public void testDadoQueSubmetoAsPerguntasEntaoObtenhoOCertificadoStatus201(){
        given()
                .header("token", token)
                .body("{\n" +
                        "  \"apresentarPercentual\": true\n" +
                        "}")
                .contentType(ContentType.JSON)
            .when()
                .post("/certificados")
            .then()
                .assertThat()
                .statusCode(201)
                .log().all();
    }

}
