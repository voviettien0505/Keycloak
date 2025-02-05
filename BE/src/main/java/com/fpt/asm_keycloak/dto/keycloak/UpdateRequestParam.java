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
public class UpdateRequestParam {
    private String email;
    private String firstName;
    private String lastName;
}