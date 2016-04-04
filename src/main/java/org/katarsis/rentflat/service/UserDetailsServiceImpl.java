package org.katarsis.rentflat.service;

import javax.inject.Inject;

import org.katarsis.rentflat.entities.UserAccount;
import org.katarsis.rentflat.repository.UserAccountRepository;
import org.katarsis.rentflat.util.UserDetailsAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{

	@Inject UserAccountRepository userAccountService;
	
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		UserAccount userAccount = userAccountService.getUserAccountByName(userName);
		if(userAccount==null||!userAccount.isEnabled())
			throw new UsernameNotFoundException("No such user"+userName);
		UserDetailsAdapter user =new UserDetailsAdapter(userAccount);
		return user;
	}

}
