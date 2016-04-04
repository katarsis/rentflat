package org.katarsis.rentflat.repository;

import org.katarsis.rentflat.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer>{

	
	@Query("FROM VerificationToken where token = ?1")
	VerificationToken findAccountByToken(String token);
}
