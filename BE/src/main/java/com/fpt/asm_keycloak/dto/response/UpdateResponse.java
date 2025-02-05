package com.fpt.asm_keycloak.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateResponse {
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private LocalDate dob;
}