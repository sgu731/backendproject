package util;

import org.springframework.http.HttpHeaders;

public class TestJwtUtil {

    public static HttpHeaders bearer(String token) {

        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(token);

        return headers;
    }
}