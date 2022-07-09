package io.getarrays.userservice.repo;

import io.getarrays.userservice.domain.Message;
import io.getarrays.userservice.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepo extends JpaRepository<Message, UUID> {


}
