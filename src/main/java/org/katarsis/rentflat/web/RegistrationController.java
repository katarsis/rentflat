package org.katarsis.rentflat.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.katarsis.rentflat.entities.Role;
import org.katarsis.rentflat.entities.UserAccount;
import org.katarsis.rentflat.repository.RoleRepository;
import org.katarsis.rentflat.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegistrationController {

	@Autowired
	UserAccountRepository userAccountRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Locale locale, Model model){
		model.addAttribute("user", new UserAccount());
		return "registration";
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String doRegistration(@ModelAttribute ("user") UserAccount user, BindingResult result){
		List<Role> userDefaultRole = new ArrayList<Role>();
		Role defaultRole = roleRepository.getRoleById(1);
		userDefaultRole.add(defaultRole);
		user.setRoles(userDefaultRole);
		userAccountRepository.save(user);
		return "redirect:/";
	}
}
