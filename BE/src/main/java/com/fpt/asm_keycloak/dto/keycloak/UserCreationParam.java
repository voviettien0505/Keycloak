package com.fpt.asm_keycloak.dto.keycloak;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreationParam {
    private String username;
    private boolean enabled;
    private String email;
    private boolean emailVerified;
    private String firstName;
    private String lastName;
    private List<Credential> credentials;
}