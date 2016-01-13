package org.katarsis.rentflat.repository;

import org.katarsis.rentflat.entities.Role;
import org.katarsis.rentflat.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RoleRepository extends JpaRepository<UserAccount, Integer>{

	@Query("FROM Role where id = ?1")
	Role getRoleById(int idRole);
}
