package br.com.almeida.managerpatrimony.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.almeida.managerpatrimony.persistence.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long>{

	Optional<Brand> findByName (String name);
}
