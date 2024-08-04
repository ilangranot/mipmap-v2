package com.pleyfair.mipmip.client;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.logging.LoggingFeature;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Singleton class to manage a single REST connection for PERSON_SEARCH
 * uses Client (Jakarta)
 */
@Slf4j
public class EndatoClient {
    private final Client client;
    private final String endatoKey;
    private final String endatoSecret;
    private final String endatoProtocol;
    private final String endatoHost;
    private final int endatoPort;
    private final String endatoSearchPath;
    private final String endatoSearchType;

    // Private constructor
    public EndatoClient(String endatoKey, String endatoSecret, String endatoProtocol, String endatoHost, int endatoPort, String endatoSearchPath, String endatoSearchType){
        this.endatoKey = endatoKey;
        this.endatoSecret = endatoSecret;
        this.endatoProtocol = endatoProtocol;
        this.endatoHost = endatoHost;
        this.endatoPort = endatoPort;
        this.endatoSearchPath = endatoSearchPath;
        this.endatoSearchType = endatoSearchType;
        this.client = ClientBuilder
                .newBuilder()
                .register(HttpAuthenticationFeature.basic(endatoKey, endatoSecret))
                .register(new LoggingFeature())
                .build();
        log.info("Started endato client successfully");
    };

    /**
     * gets a response from Endato
     * @param payload the payload Entity from a PersonRequest
     * @return String with json of PersonResponse
     * @throws URISyntaxException
     */
    public String getJsonResponseString(Entity payload) throws URISyntaxException {
        log.info("Read from Endato");
        return client.target(
                        new URI(
                                endatoProtocol,
                                null,
                                endatoHost,
                                endatoPort,
                                endatoSearchPath,
                                null,
                                null
                        ).toString())
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .header("galaxy-search-type", endatoSearchType)
                .header("galaxy-ap-name", endatoKey)
                .header("galaxy-ap-password", endatoSecret)
                .post(payload)
                .readEntity(String.class);
    }
}
