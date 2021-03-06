package com.app.jwtauthentication.repository;


import com.app.jwtauthentication.domain.Role;
import com.app.jwtauthentication.domain.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(RoleName roleName);
}