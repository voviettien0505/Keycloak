package com.fpt.asm_keycloak.mapper;

import com.fpt.asm_keycloak.dto.request.RegisterRequest;
import com.fpt.asm_keycloak.dto.request.UpdateRequest;
import com.fpt.asm_keycloak.dto.response.ProfileResponse;
import com.fpt.asm_keycloak.model.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile toProfile(RegisterRequest request);
    ProfileResponse toProfileResponse(Profile profile);
    Profile toUpdateProfile(UpdateRequest request);
    UpdateRequest toUpdateUserResponse(Profile profile);

}