package com.endava.petclinic;

import com.endava.petclinic.models.Owner;
import com.endava.petclinic.util.EnvReader;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AddOwner {

    private Faker faker = new Faker();

    @Test
    public void firstTest(){
        Owner owner = new Owner();
        owner.setAddress( faker.address().streetAddress() );
        owner.setCity( faker.address().city() );
        owner.setFirstName( faker.name().firstName() );
        owner.setLastName( faker.name().lastName() );
        owner.setTelephone( faker.number().digits( 10 ) );


        ValidatableResponse response = given()
                .baseUri(EnvReader.getBaseUri())
                .port(EnvReader.getPort())
                .basePath(EnvReader.getBasePath())
                .contentType(ContentType.JSON)
                .body(owner)
                .log().all()
                .post("/api/owners")
                .prettyPeek()
                .then().statusCode(HttpStatus.SC_CREATED);

        Integer id = response.extract().jsonPath().getInt("id" );


        ValidatableResponse getResponse = given()
                .baseUri(EnvReader.getBaseUri())
                .port(EnvReader.getPort())
                .basePath(EnvReader.getBasePath())
                .pathParam("ownerId", id)
                .get("/api/owners/{ownerId}")
                .prettyPeek()
                .then().statusCode(HttpStatus.SC_OK);



        Owner actualOwner = getResponse.extract().as( Owner.class );

        assertThat( actualOwner, is( owner ));
    }
}

