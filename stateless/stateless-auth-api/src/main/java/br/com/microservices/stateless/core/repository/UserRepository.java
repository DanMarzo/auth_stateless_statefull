package br.com.microservices.stateless.core.repository;

import br.com.microservices.stateless.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//JpaRepository<User, Integer>
//JpaRepository<ENTIDADE, TIPO DA PK>
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
