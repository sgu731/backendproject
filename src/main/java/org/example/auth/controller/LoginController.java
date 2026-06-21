package org.example.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.auth.dto.LoginRequest;
import org.example.auth.dto.LoginResponse;
import org.example.auth.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Auth")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @Operation(summary = "Login")
    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        return loginService.login(request);
    }

}