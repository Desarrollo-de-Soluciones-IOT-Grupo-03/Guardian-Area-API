package com.digitaldart.guardian.area.iam.infrastructure.persistence.jpa.repositories;

import com.digitaldart.guardian.area.iam.domain.model.aggregates.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
