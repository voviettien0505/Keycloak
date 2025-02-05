package com.fpt.asm_keycloak.dto.keycloak;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Credential {
    private String type;
    private String value;
    private boolean temporary;
}