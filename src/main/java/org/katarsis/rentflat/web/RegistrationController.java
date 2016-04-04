package org.katarsis.rentflat.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.katarsis.rentflat.entities.Role;
import org.katarsis.rentflat.entities.UserAccount;
import org.katarsis.rentflat.entities.VerificationToken;
import org.katarsis.rentflat.repository.RoleRepository;
import org.katarsis.rentflat.repository.UserAccountRepository;
import org.katarsis.rentflat.repository.VerificationTokenRepository;
import org.katarsis.rentflat.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

	@Autowired
	UserAccountRepository userAccountRepository;
	
	@Autowired
	VerificationTokenRepository verificationTokenRepo;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	RoleRepository roleRepository;
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Locale locale, Model model){
		model.addAttribute("user", new UserAccount());
		return "registration";
	}
	
	@RequestMapping(value = "/registrationWait", method = RequestMethod.GET)
	public String registrationWait(Locale locale, Model model){
		//model.addAttribute("user", new UserAccount());
		return "registrationWait";
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String doRegistration(@ModelAttribute ("user") UserAccount user, BindingResult result){
		List<Role> userDefaultRole = new ArrayList<Role>();
		Role defaultRole = roleRepository.getRoleById(1);
		userDefaultRole.add(defaultRole);
		user.setRoles(userDefaultRole);
		String token = UUID.randomUUID().toString();
		VerificationToken vt = new VerificationToken(token, user);
		userAccountRepository.save(user);
		verificationTokenRepo.save(vt);
		mailService.sendMail( user,vt);
		
		return "redirect:/registrationWait";
	}
	
	@RequestMapping(value = "/regConfirm", method = RequestMethod.GET)
	public String confirmRegistration(@RequestParam("token") String token)
    {
		VerificationToken verifyToken = verificationTokenRepo.findAccountByToken(token);
		UserAccount user = verifyToken.getUser();
		user.setEnabled(true);
		userAccountRepository.save(user);
		return "redirect:/login";
    }
}
