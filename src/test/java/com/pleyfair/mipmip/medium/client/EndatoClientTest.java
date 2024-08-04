package com.pleyfair.mipmip.medium.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pleyfair.mipmip.client.EndatoClient;
import com.pleyfair.mipmip.model.dto.endato.EndatoError;
import com.pleyfair.mipmip.model.dto.endato.Request;
import com.pleyfair.mipmip.model.dto.endato.Response;
import jakarta.ws.rs.client.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URISyntaxException;

@SpringBootTest
class EndatoClientTest {
    @Autowired
    private EndatoClient endatoClient;

    @Test
    void givenCurrentConfig_whenGetResponseForValidRequest_thenCorrectResponse() throws URISyntaxException {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Entity payloadEntity = Entity.json(gson.toJson(
                Request.builder()
                        .FirstName("Ilan")
                        .LastName("Granot")
                        .Phone("650-387-4464")
                        .build())
        );
        String jsonResponseString = endatoClient.getJsonResponseString(payloadEntity);
        Response response = gson.fromJson(jsonResponseString, Response.class);
        Assertions.assertEquals(response.getPersons().get(0).name.middleName, "Yehuda");
    }

    @Test
    public void givenCurrentConfig_whenGetResponseForInvalidRequest_thenErrorResponse() throws URISyntaxException {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Entity payloadEntity = Entity.json(gson.toJson(
                Request.builder().build())
        );
        String jsonResponseString = endatoClient.getJsonResponseString(payloadEntity);
        EndatoError endatoError = gson.fromJson(jsonResponseString, EndatoError.class);
        Assertions.assertEquals(endatoError.isError(), true);
        Assertions.assertEquals(endatoError.getError().getCode(), "Invalid Input");
    }
}