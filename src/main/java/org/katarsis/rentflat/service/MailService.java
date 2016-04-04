package org.katarsis.rentflat.service;

import org.katarsis.rentflat.entities.UserAccount;
import org.katarsis.rentflat.entities.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	private MailSender mailSender;
	
	private String DOMAIN_MAIL = "noreply@rent-flat.com";
	private String APPLICATION_URL = "http://rentflat-katarsis.rhcloud.com/";
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void sendMail(UserAccount user, VerificationToken token){
		String template = "Dear "+user.getUserLogin()+". Please verify your email address so we know that it's really you! "+APPLICATION_URL+"regConfirm?token="+token.getToken();
		this.sendMail(user.getUserEmail(),"Please verify your email address",template);
	}
	
	public void sendMail( String to, String subject, String msg) {

		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setFrom(DOMAIN_MAIL);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);	
	}
}
