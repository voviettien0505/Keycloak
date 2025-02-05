package com.fpt.asm_keycloak.controller;


import com.fpt.asm_keycloak.dto.ApiResponse;
import com.fpt.asm_keycloak.dto.request.LoginRequest;
import com.fpt.asm_keycloak.dto.request.RegisterRequest;
import com.fpt.asm_keycloak.dto.response.LoginResponse;
import com.fpt.asm_keycloak.dto.response.ProfileResponse;
import com.fpt.asm_keycloak.service.ProfileService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileController {
    ProfileService profileService;

    @PostMapping("/register")
    ApiResponse<ProfileResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ApiResponse.<ProfileResponse>builder()
                .data(profileService.register(request))
                .code(200)
                .build();
    }

    @GetMapping("/profiles")
    ApiResponse<List<ProfileResponse>> getAllProfiles() {
        return ApiResponse.<List<ProfileResponse>>builder()
                .data(profileService.getAllProfiles())
                .code(200)
                .build();
    }

    @GetMapping("/my-profile")
    ApiResponse<ProfileResponse> getMyProfile() {
        return ApiResponse.<ProfileResponse>builder()
                .data(profileService.getMyProfile())
                .code(200)
                .build();
    }

    @PostMapping("/login")
    ApiResponse<LoginResponse> Login(@RequestBody LoginRequest request) {
        var response = profileService.login(request);
        return ApiResponse.<LoginResponse>builder()
                .code(200)
                .data(response)
                .build();
    }
}