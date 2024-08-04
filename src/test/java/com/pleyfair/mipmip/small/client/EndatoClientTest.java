package com.pleyfair.mipmip.small.client;

import com.pleyfair.mipmip.client.EndatoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EndatoClientTest {
    @Mock //(Answers.RETURNS_DEEP_STUBS)
    private EndatoClient endatoClientMock;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getJsonResponseString() {

    }
}