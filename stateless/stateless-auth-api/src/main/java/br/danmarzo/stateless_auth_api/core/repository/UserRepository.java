package br.danmarzo.stateless_auth_api.core.repository;

import br.danmarzo.stateless_auth_api.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//JpaRepository<User, Integer>
//JpaRepository<ENTIDADE, TIPO DA PK>
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
