package com.fpt.asm_keycloak.service;

import com.fpt.asm_keycloak.dto.keycloak.LoginRequestParam;
import com.fpt.asm_keycloak.dto.request.LoginRequest;
import com.fpt.asm_keycloak.dto.request.RegisterRequest;
import com.fpt.asm_keycloak.dto.response.LoginResponse;
import com.fpt.asm_keycloak.dto.response.ProfileResponse;
import com.fpt.asm_keycloak.exception.AppException;
import com.fpt.asm_keycloak.exception.ErrorCode;
import com.fpt.asm_keycloak.exception.ErrorNornalizer;
import com.fpt.asm_keycloak.mapper.ProfileMapper;
import com.fpt.asm_keycloak.model.Profile;
import com.fpt.asm_keycloak.repository.KeycloakRepository;
import com.fpt.asm_keycloak.repository.ProfileRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import static lombok.AccessLevel.PRIVATE;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ProfileService {

    ProfileRepository profileRepository;
    KeycloakRepository keycloakRepository;
    ProfileMapper profileMapper;
    ErrorNornalizer errorNornalizer;
    PasswordEncoder passwordEncoder;


    @Value("${idp.client-id}")
    @NonFinal
    String clientId;

    @Value("${idp.client-secret}")
    @NonFinal
    String clientSecret;

    //lấy tất cả thông tin profile
    @PreAuthorize("hasRole('ADMIN')")
    public List<ProfileResponse> getAllProfiles() {
        var profiles = profileRepository.findAll();
        return profiles.stream().map(profileMapper::toProfileResponse).toList();
    }

    //lấy thông tin profile của user hiện tại
    public ProfileResponse getMyProfile() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        var profile = profileRepository.findById(Long.valueOf(userId)).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return profileMapper.toProfileResponse(profile);
    }

    //đăng ký tài khoản
    public ProfileResponse register(RegisterRequest request) {
        // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        // Tạo người dùng mới và lưu vào cơ sở dữ liệu
        Profile user = new Profile();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstname());
        user.setLastName(request.getLastname());
        user.setPassword(encryptedPassword);
        user = profileRepository.save(user);

        // Chuyển đổi thành response nếu cần
        return profileMapper.toProfileResponse(user);
    }

    //lấy userId từ response
    private String extractUserId(ResponseEntity<?> response) {
        // Extract userId from response
        String location = response.getHeaders().get("Location").get(0);
        String[] splitedStr = location.split("/");
        return splitedStr[splitedStr.length - 1];
    }

    public LoginResponse login(LoginRequest request) {
        try {
            var token = keycloakRepository.exchangeToken(LoginRequestParam.builder()
                    .grant_type("password")
                    .client_id(clientId)
                    .client_secret(clientSecret)
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .scope("openid")
                    .build());
            System.out.println("Token Response: " + token);
            System.out.println("Access Token: " + token.getAccessToken());
            System.out.println("Refresh Token: " + token.getRefreshToken());

            return LoginResponse.builder()
//                    .preferredUsername(token.getPreferredUsername())
                    .accessToken(token.getAccessToken())
                    .refreshToken(token.getRefreshToken())
                    .build();
        } catch (FeignException e) {
            throw errorNornalizer.handleKeyCloakException(e);
        }
    }


}