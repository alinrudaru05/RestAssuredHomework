package com.endava.petclinic;

import com.endava.petclinic.models.Owner;
import com.endava.petclinic.util.EnvReader;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetOwnerById {

    @Test
    public void firstTest() {
        ValidatableResponse getResponse = given()
                .baseUri(EnvReader.getBaseUri())
                .port(EnvReader.getPort())
                .basePath(EnvReader.getBasePath())
                .get("/api/owners")
                .then().statusCode(HttpStatus.SC_OK);

        Owner []array = getResponse.extract().as(Owner[].class);
        int    index  = 0;
        ValidatableResponse getResponsePerOwner;

        while (index < array.length) {
            getResponsePerOwner = given()
                    .baseUri(EnvReader.getBaseUri())
                    .port(EnvReader.getPort())
                    .basePath(EnvReader.getBasePath())
                    .pathParam("ownerId", array[index].getId())
                    .get("/api/owners/{ownerId}")
                    .prettyPeek()
                    .then().statusCode(HttpStatus.SC_OK);

            index++;
        }
    }
}
