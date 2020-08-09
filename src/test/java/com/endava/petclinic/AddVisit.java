package com.endava.petclinic;

import com.endava.petclinic.models.Owner;
import com.endava.petclinic.models.Visit;
import com.endava.petclinic.util.EnvReader;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AddVisit {

    private Faker faker = new Faker();

    @Test
    public void firstTest(){
        Visit visit = new Visit();
        visit.setDate(faker.date().birthday().toString());
        visit.setDescription("none");


        ValidatableResponse response = given()
                .baseUri(EnvReader.getBaseUri())
                .port(EnvReader.getPort())
                .basePath(EnvReader.getBasePath())
                .contentType(ContentType.JSON)
                .body(visit)
                .log().all()
                .post("/api/visits")
                .prettyPeek()
                .then().statusCode(HttpStatus.SC_CREATED);

        Integer id = response.extract().jsonPath().getInt("id" );


        ValidatableResponse getResponse = given()
                .baseUri(EnvReader.getBaseUri())
                .port(EnvReader.getPort())
                .basePath(EnvReader.getBasePath())
                .pathParam("visitId", id)
                .get("/api/visits/{visitId}")
                .prettyPeek()
                .then().statusCode(HttpStatus.SC_OK);



        Visit actualVisit = getResponse.extract().as( Visit.class );

        assertThat( actualVisit, is( visit ));
    }
}


