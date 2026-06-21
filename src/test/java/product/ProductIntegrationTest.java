package product;

import base.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import util.TestJwtUtil;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ProductIntegrationTest extends IntegrationTestBase {

    private String login(String username, String password) {

        ResponseEntity<Map> response =
                restTemplate.postForEntity(
                        "/api/auth/login",
                        Map.of(
                                "username", username,
                                "password", password
                        ),
                        Map.class
                );

        return (String) response.getBody().get("accessToken");
    }

    @Test
    void admin_can_create_product() {

        String token = login("admin", "admin123");

        var body = Map.of(
                "name", "iPhone",
                "price", 1000,
                "stock", 10
        );

        HttpEntity<?> request =
                new HttpEntity<>(body, TestJwtUtil.bearer(token));

        ResponseEntity<Void> response =
                restTemplate.postForEntity(
                        "/api/products",
                        request,
                        Void.class
                );

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void user_cannot_create_product() {

        String token = login("user", "admin123");

        var body = Map.of(
                "name", "iPhone",
                "price", 1000,
                "stock", 10
        );

        HttpEntity<?> request =
                new HttpEntity<>(body, TestJwtUtil.bearer(token));

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        "/api/products",
                        request,
                        String.class
                );

        assertEquals(403, response.getStatusCode().value());
    }
}