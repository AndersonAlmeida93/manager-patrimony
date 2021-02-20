package br.com.almeida.managerpatrimony.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.almeida.managerpatrimony.persistence.entity.Patrimony;

@Repository
public interface PatrimonyRepository extends JpaRepository<Patrimony, Long>{

	Optional<Patrimony> findByName (String name);
	
	List<Patrimony> findAllByBrandIdOrderByDescriptionAsc (long brandId);
	
	Optional<Patrimony> findByNumberFall (long numberFall);
	
	@Query(value = "SELECT COALESCE(MAX(number_fall),0) as number_fall FROM patrimony", nativeQuery = true)
	Long lastNumberFall();
}
