package com.picpaysimplificado.repositories.User;

import com.picpaysimplificado.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findById(Long id);
    Optional<User> findByDocument(String document);
}
