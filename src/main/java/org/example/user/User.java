package org.example.user;

public record User(
        Long id,
        String username,
        String password,
        String role
) {
}