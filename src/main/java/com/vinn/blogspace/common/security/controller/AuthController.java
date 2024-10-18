package com.vinn.blogspace.common.security.controller;

import com.vinn.blogspace.common.security.jwt.JwtTokenProvider;
import com.vinn.blogspace.common.security.dto.ApiResponse;
import com.vinn.blogspace.common.security.dto.JwtAuthenticationResponse;
import com.vinn.blogspace.common.security.dto.LoginRequest;
import com.vinn.blogspace.user.dto.UserCreateDto;
import com.vinn.blogspace.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserCreateDto signUpRequest) {

        userService.createUser(signUpRequest);

        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signOut(HttpServletRequest request) {
        String jwt = getJwtFromRequest(request);
        if (jwt != null && tokenProvider.validateToken(jwt)) {
            Long userId = tokenProvider.getUserIdFromJWT(jwt);
            tokenProvider.invalidateToken(jwt);
            return ResponseEntity.ok(new ApiResponse(true, "User signed out successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid token"));
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}