package org.katarsis.rentflat.repository;

import org.katarsis.rentflat.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer>{

	@Query("FROM UserAccount where userLogin = ?1")
	UserAccount getUserAccountByName(String userName);
}
