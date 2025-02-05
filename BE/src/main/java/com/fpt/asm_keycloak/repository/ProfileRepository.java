package com.fpt.asm_keycloak.repository;

import com.fpt.asm_keycloak.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProfileRepository extends JpaRepository<Profile, Long> {

}