package org.katarsis.rentflat.repository;

import org.katarsis.rentflat.entities.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FlatRepository extends JpaRepository<Flat, Integer>{

	@Query("select avg(price) from Flat 	where id_location in (select id from Location where description = ?1)")
	Double findAvgPriceForStantion(String stantion);
}
