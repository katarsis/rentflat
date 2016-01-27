package org.katarsis.rentflat.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.katarsis.rentflat.entities.Role;
import org.katarsis.rentflat.entities.UserAccount;
import org.katarsis.rentflat.repository.RoleRepository;
import org.katarsis.rentflat.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsersController {

	@Autowired
	UserAccountRepository userRepos;
	
	@Autowired
	RoleRepository roleRepos;
	
	@RequestMapping(value="administration/users",method=RequestMethod.GET)
	public String showAllUsers(Locale locale, Model model){
		model.addAttribute("users", userRepos.findAll());
		return "administration/users";
	}
	

	@RequestMapping(value="administration/users/save",method=RequestMethod.POST)
	public String saveUser(Locale locale, Model model,
							@RequestParam(required = true)Integer user_id, 
							@RequestParam(required = false)String user_login,
							@RequestParam(required = false) String user_email,
							@RequestParam(required = false) String user_passwd,
							@RequestParam(required = false) String user_roles) {
		UserAccount editUser = userRepos.findOne(user_id);
		editUser.setPasswd(user_passwd);
		editUser.setUserEmail(user_email);
		editUser.setUserLogin(user_login);
		List<Role> userRoles = new ArrayList<Role>();
		
		for(String roleName: user_roles.split(",")){
			userRoles.add(roleRepos.findRoleByName(roleName));
		}
		
		editUser.setRoles(userRoles);
		userRepos.save(editUser);
		return "redirect:/administration/users";
	}
	
	@RequestMapping(value="standart/profile/edit",method=RequestMethod.GET)
	public String editProfile(Locale locale, Model model)
	{
		return "standart/profile/edit";
	}

	
	@RequestMapping(value="profile/edit",method=RequestMethod.POST)
	public String updateUser(Locale locale, Model model,
							@RequestParam(required = false) String user_email,
							@RequestParam(required = false) String password
							) {
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      String name = auth.getName(); //get logged in username
		UserAccount editUser = userRepos.getUserAccountByName(name);
		editUser.setPasswd(password);
		editUser.setUserEmail(user_email);
		userRepos.save(editUser);
		return "redirect:/";
	}
	
}
