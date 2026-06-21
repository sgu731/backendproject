package order;

import base.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import util.TestJwtUtil;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OrderIntegrationTest extends IntegrationTestBase {

    private String loginAsAdmin() {

        ResponseEntity<Map> response =
                restTemplate.postForEntity(
                        "/api/auth/login",
                        Map.of(
                                "username", "admin",
                                "password", "admin123"
                        ),
                        Map.class
                );

        return (String) response.getBody().get("accessToken");
    }

    @Test
    void create_order_should_success() {

        String token = loginAsAdmin();

        var body = Map.of(
                "productId", 1,
                "quantity", 2
        );

        HttpEntity<?> request =
                new HttpEntity<>(body, TestJwtUtil.bearer(token));

        ResponseEntity<Void> response =
                restTemplate.postForEntity(
                        "/api/orders",
                        request,
                        Void.class
                );

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void should_fail_when_stock_not_enough() {

        String token = loginAsAdmin();

        var body = Map.of(
                "productId", 1,
                "quantity", 9999
        );

        HttpEntity<?> request =
                new HttpEntity<>(body, TestJwtUtil.bearer(token));

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        "/api/orders",
                        request,
                        String.class
                );

        assertEquals(400, response.getStatusCode().value());
    }
}