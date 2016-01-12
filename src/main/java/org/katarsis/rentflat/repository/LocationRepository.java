package org.katarsis.rentflat.repository;

import org.katarsis.rentflat.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer>{

	@Query("FROM Location where description = ?1")
	Location findStantion(String stantion);
}
