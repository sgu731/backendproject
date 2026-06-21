package org.example.ratelimit;

import io.github.bucket4j.Bucket;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RateLimitFilter extends HttpFilter {

    private final RateLimitService rateLimitService;

    @Override
    protected void doFilter(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        String ip = request.getRemoteAddr();

        Bucket bucket =
                rateLimitService.resolveBucket(ip);

        if (!bucket.tryConsume(1)) {

            response.setStatus(429);

            response.getWriter()
                    .write("Too many requests");

            return;
        }

        chain.doFilter(
                request,
                response);
    }
}