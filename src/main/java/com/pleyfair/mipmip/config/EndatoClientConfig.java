package com.pleyfair.mipmip.config;

import com.pleyfair.mipmip.client.EndatoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndatoClientConfig {
    @Value("${endato.key}")
    private String endatoKey;

    @Value("${endato.secret}")
    private String endatoSecret;

    @Value("${endato.protocol}")
    private String endatoProcol;

    @Value("${endato.host}")
    private String endatoHost;

    @Value("${endato.port}")
    private int endatoPort;

    @Value("${endato.searchPath}")
    private String endatoSearchPath;

    @Value("${endato.searchType}")
    private String endatoSearchType;


    @Bean
    public EndatoClient endatoClient() {
        return new EndatoClient
                (endatoKey, endatoSecret, endatoProcol, endatoHost, endatoPort,endatoSearchPath,endatoSearchType);
    }

}
