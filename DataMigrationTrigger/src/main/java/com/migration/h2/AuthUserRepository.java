package com.migration.h2;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Boolean existsByUsername(String username);
    Optional<AuthUser> findByUsername(String username);
}
