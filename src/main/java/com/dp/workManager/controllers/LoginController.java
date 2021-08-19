package com.dp.workManager.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dp.workManager.models.User;
import com.dp.workManager.services.UserService;

@Controller
public class LoginController {
private final UserService userService;

	
	public LoginController(UserService userService) {
		this.userService = userService;
	}
	@RequestMapping("/registration")
	public String registerForm(@Valid @ModelAttribute("user")User user) {
		return "register.jsp";
	}
	@RequestMapping("/login")
    public String login(@Valid @ModelAttribute("user")User user, @RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model) {
        if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successful!");
        }
        return "login.jsp";
    }
	@PostMapping("/registration")
	public String registration(@Valid @ModelAttribute("user")User user, BindingResult result, Model model, HttpSession session) {
		if (result.hasErrors()) {
			return "register.jsp";
		}
		userService.saveWithRole(user);
		return "redirect:/login";
	}
	
//	@RequestMapping("/admin")
//	public String adminPage(Principal principal, Model model) {
//		String email = principal.getName();
//		model.addAttribute("currentUser", userService.findByEmail(email));
//		return "adminPage.jsp";
//	}
}
