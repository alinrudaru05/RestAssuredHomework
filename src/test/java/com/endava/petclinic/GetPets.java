package com.endava.petclinic;

import com.endava.petclinic.util.EnvReader;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class GetPets {

    @Test
    public void firstTest() {
        ValidatableResponse getResponse = given()
                .baseUri(EnvReader.getBaseUri())
                .port(EnvReader.getPort())
                .basePath(EnvReader.getBasePath())
                .log().all()
                .get("/api/pets")
                .prettyPeek()
                .then().statusCode(HttpStatus.SC_OK);

    }
}
