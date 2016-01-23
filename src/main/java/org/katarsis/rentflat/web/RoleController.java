package org.katarsis.rentflat.web;

import java.util.Locale;

import org.katarsis.rentflat.repository.RoleRepository;
import org.katarsis.rentflat.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RoleController {

	@Autowired
	RoleRepository roleRepos;
	
	@RequestMapping(value="administration/roles",method=RequestMethod.GET)
	public String showTaskLog(Locale locale, Model model){
		model.addAttribute("roles", roleRepos.findAll());
		return "administration/roles";
	}
}
