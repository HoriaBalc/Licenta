package io.getarrays.userservice.repo;

import io.getarrays.userservice.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepo extends JpaRepository<Role, UUID> {
    Role findByName(String name);

}