package br.com.almeida.managerpatrimony.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.almeida.managerpatrimony.persistence.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

	Optional<Users> findByName(String name);

	Optional<Users> findByEmail(String name);
}
