package com.fpt.asm_keycloak.repository;
import com.fpt.asm_keycloak.dto.keycloak.LoginRequestParam;
import com.fpt.asm_keycloak.dto.keycloak.TokenExchangeParam;
import com.fpt.asm_keycloak.dto.keycloak.TokenExchangeResponse;
import com.fpt.asm_keycloak.dto.keycloak.UserCreationParam;
import com.fpt.asm_keycloak.dto.response.LoginResponse;
import feign.QueryMap;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "identity-client", url = "http://localhost:9090")
public interface KeycloakRepository {
    @PostMapping(value = "/realms/viettien/protocol/openid-connect/token",
            consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenExchangeResponse exchangeToken(@QueryMap TokenExchangeParam param);
    @PostMapping(value = "/admin/realms/viettien/users",
            consumes= MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createUser(
            @RequestHeader("authorization") String token,
            @RequestBody UserCreationParam param);
    @PostMapping(value = "/realms/viettien/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    LoginResponse exchangeToken(@QueryMap LoginRequestParam param);

}