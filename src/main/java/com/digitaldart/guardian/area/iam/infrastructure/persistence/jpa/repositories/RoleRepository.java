package com.digitaldart.guardian.area.iam.infrastructure.persistence.jpa.repositories;

import com.digitaldart.guardian.area.iam.domain.model.entities.Role;
import com.digitaldart.guardian.area.iam.domain.model.valueobjects.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Roles name);
    boolean existsByName(Roles name);
}
