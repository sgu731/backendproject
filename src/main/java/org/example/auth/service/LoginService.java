package org.example.auth.service;
import org.example.auth.dto.LoginRequest;
import org.example.auth.dto.LoginResponse;
import org.example.user.User;
import org.example.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.security.JwtService;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository
                .findByUsername(request.username())
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );

        boolean matched =
                passwordEncoder.matches(
                        request.password(),
                        user.password());

        if (!matched) {
            throw new RuntimeException(
                    "Invalid password");
        }

        String token =
                jwtService.generateToken(
                        user.id(),
                        user.username(),
                        user.role());

        return new LoginResponse(token);
    }

}