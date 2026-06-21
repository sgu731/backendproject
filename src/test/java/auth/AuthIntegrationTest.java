package auth;

import base.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AuthIntegrationTest extends IntegrationTestBase {

    @Test
    void login_should_return_token() {

        var request = Map.of(
                "username", "admin",
                "password", "admin123"
        );

        ResponseEntity<Map> response =
                restTemplate.postForEntity(
                        "/api/auth/login",
                        request,
                        Map.class
                );

        assertEquals(200, response.getStatusCode().value());

        assertNotNull(response.getBody().get("accessToken"));
    }
}