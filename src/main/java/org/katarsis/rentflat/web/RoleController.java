package org.katarsis.rentflat.web;

import java.util.Locale;

import org.katarsis.rentflat.entities.Role;
import org.katarsis.rentflat.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RoleController {

	@Autowired
	RoleRepository roleRepos;
	
	@RequestMapping(value="administration/roles",method=RequestMethod.GET)
	public String showAllRoles(Locale locale, Model model){
		model.addAttribute("roles", roleRepos.findAll());
		return "administration/roles";
	}
	
	@RequestMapping(value="administration/roles/save",method=RequestMethod.POST)
	public String saveRole(Locale locale, Model model,@RequestParam(required = false)Integer role_id, 
							@RequestParam(required = false)String role_name, @RequestParam(required = false) String role_desc) {
		Role editRole = roleRepos.findOne(role_id);
		editRole.setDescription(role_desc);
		editRole.setRole(role_name);
		roleRepos.save(editRole);

		return "redirect:/administration/roles";
	}
}
