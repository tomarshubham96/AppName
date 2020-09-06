package com.java.appbackend.Controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.appbackend.Model.User;
import com.java.appbackend.Repository.SecurityServiceRepository;
import com.java.appbackend.Repository.UserServiceRepository;
import com.java.appbackend.Service.EmailService;

@Controller
public class UserController {

	@Autowired
    private UserServiceRepository userServiceRepository;

    @Autowired
    private SecurityServiceRepository securityServiceRepository;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm) {
       
        userServiceRepository.save(userForm);

        securityServiceRepository.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
        return "welcome";
    }
    
    @GetMapping("/forgotPassword")
    public String forgotPassword(Model model, User user) {
    	model.addAttribute("userForm", user);
        return "forgotPassword";
    }
    
    @PostMapping("/forgotPassword")
    public String forgotPassword(@ModelAttribute("userForm") User userForm, Model model, HttpServletRequest request) {
    	
    	User userExists = userServiceRepository.findByEmail(userForm.getEmail());
    	
    	String response = userServiceRepository.forgotPassword(userForm.getEmail());
    	
    	if(!response.startsWith("Invalid")) {    		
    		String resetUrl = request.getRequestURL().toString().replace("/forgotPassword", "");
    		
    		SimpleMailMessage registrationEmail = new SimpleMailMessage();
			registrationEmail.setTo(userForm.getEmail());
			registrationEmail.setSubject("Password Reset");
			registrationEmail.setText("To reset your password please click the link below:\n"
					+ resetUrl + "/resetPassword?token=" + response);
			registrationEmail.setFrom("noreply@domain.com");
			
			emailService.sendEmail(registrationEmail);
			
			model.addAttribute("displayMessage", "Link has been sent to your registered mail.");
			
    		return "forgotPassword";
    	}
    	else {
    		model.addAttribute("displayMessage", "Not a registered Email Id. Enter the registered Email-Id.");
    		return "forgotPassword";
    	}
    }


@GetMapping(value="/resetPassword")
public String resetpassword(@ModelAttribute("userForm") User userForm, @RequestParam("token") String token, Model model, RedirectAttributes redir) {
	
	User user = userServiceRepository.findByToken(token);
		
	if (user == null) {
		redir.addFlashAttribute("invalidToken", "Oops!  This is an invalid link////.");
		
		return "redirect:/forgotPassword";
	} else {
		model.addAttribute("token", user.getToken());
	}
	
	return "resetPassword";		
 }

@PostMapping("/resetPassword")
public String resetPassword(@ModelAttribute("userForm") User userForm, Model model, RedirectAttributes redir) {
		
     String response = userServiceRepository.resetPassword(userForm.getToken(), userForm.getPassword());
     
     if(!response.startsWith("Invalid")) {
    	 
    	redir.addFlashAttribute("successMessage", "You have successfully reset your password.  You may now login.");
 		
 		return "redirect:login"; 
     }
     else
     {
    	 model.addAttribute("errorMessage", "Oops!  This is an invalid password reset link.");		
 		return "resetPassword";
    	 
     }
}

@ExceptionHandler(MissingServletRequestParameterException.class)
public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
	return new ModelAndView("redirect:login");
}
}
