package com.fpt.asm_keycloak.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.asm_keycloak.dto.keycloak.KeyCloakError;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class ErrorNornalizer {

    private final ObjectMapper objectMapper;
    private final Map<String, ErrorCode> errorCodeMap;

    public ErrorNornalizer() {
        objectMapper = new ObjectMapper();
        errorCodeMap = new HashMap<>();

        errorCodeMap.put("User exists with same username", ErrorCode.USER_EXISTED);
        errorCodeMap.put("User exists with same email", ErrorCode.EMAIL_EXISTED);
        errorCodeMap.put("User name is missing", ErrorCode.USERNAME_IS_MISSING);
        errorCodeMap.put("Invalid user credentials", ErrorCode.INVALID_CREDENTIALS);

    }

    public AppException handleKeyCloakException(FeignException e) {
        try {
            log.warn("Cannot complete request", e);
            var response = objectMapper.readValue(e.contentUTF8(), KeyCloakError.class);
            if (Objects.nonNull(response.getErrorMessage()) &&
                    Objects.nonNull(errorCodeMap.get(response.getErrorMessage()))) {
                return new AppException(errorCodeMap.get(response.getErrorMessage()));
            }

        } catch (JsonProcessingException ex) {
            log.error("Cannot deserialize content", ex);
        }

        return new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }
}