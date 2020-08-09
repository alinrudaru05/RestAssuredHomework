package com.endava.petclinic;


import com.endava.petclinic.models.Pet;
import com.endava.petclinic.util.EnvReader;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class AddPet {

    private Faker faker = new Faker();

    @Test
    public void firstTest(){
        Pet pet = new Pet();
        pet.setName(faker.name().name());
        pet.setBirthDate(faker.date().birthday().toString());


        ValidatableResponse response = given()
                .baseUri(EnvReader.getBaseUri())
                .port(EnvReader.getPort())
                .basePath(EnvReader.getBasePath())
                .contentType(ContentType.JSON)
                .body(pet)
                .log().all()
                .post("/api/pets")
                .prettyPeek()
                .then().statusCode(HttpStatus.SC_CREATED);

        Integer id = response.extract().jsonPath().getInt("id" );


        ValidatableResponse getResponse = given()
                .baseUri(EnvReader.getBaseUri())
                .port(EnvReader.getPort())
                .basePath(EnvReader.getBasePath())
                .pathParam("petId", id)
                .get("/api/pets/{petId}")
                .prettyPeek()
                .then().statusCode(HttpStatus.SC_OK);



        Pet actualPet = getResponse.extract().as( Pet.class );

        assertThat( actualPet, is( pet ));
    }
}

