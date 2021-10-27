package com.nilcelsoestudo.apidatahora;

import io.restassured.RestAssured;
import net.bytebuddy.agent.VirtualMachine;
import org.apache.coyote.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestDaraHora {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp(){
        RestAssured.port = port;
    }

    @Test
    void deveRetornarDataHoraComTimezoneDefault(){
        var metodo = "GET";
        var endpoint = "/data-hora";

        var response = when()
                .request(metodo, endpoint)
                .then()
                .extract()
                .response();

        var json = response.jsonPath();

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertNotNull(json.get("dataHora"));

    }

    @Test
    void deveLancaExcecaoTimezoneInvalida(){
        var metodo = "GET";
        var endpoint = "/data-hora?timezone=teste";

        var response = when()
                .request(metodo, endpoint)
                .then()
                .extract()
                .response();

        var json = response.jsonPath();

        assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatusCode());
        assertNotNull(json.get("mensagem"));

    }

    private void assertEquals(int scBadRequest, int statusCode) {
    }



}
