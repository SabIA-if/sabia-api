package edu.sabIA.infra.repository;

import edu.sabIA.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<UserDetails> findUserByEmail(String username);
}
