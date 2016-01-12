package org.katarsis.rentflat.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.katarsis.rentflat.entities.Role;
import org.katarsis.rentflat.entities.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsAdapter implements UserDetails {

	private UserAccount userAccount;
	private String password;
	
	
	public UserDetailsAdapter(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorites = new HashSet<GrantedAuthority>();
		for(Role role:userAccount.getRoles())
		{
			authorites.add(new SimpleGrantedAuthority(role.getRole()));
		}
		return authorites;
	}

	@Override
	public String getPassword() {
		return userAccount.getPasswd();
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public UserAccount getUserAccount(){
		return userAccount;
	}
	
	@Override
	public String getUsername() {
		return userAccount.getUserLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
