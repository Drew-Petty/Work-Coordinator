package com.dp.workManager.oAuth2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.dp.workManager.models.User;
import com.dp.workManager.repos.RoleRepo;
import com.dp.workManager.services.UserService;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		CustomerOAuth2User oAuth2User =(CustomerOAuth2User)authentication.getPrincipal();
		String email = oAuth2User.getEmail();
		String name = oAuth2User.getFullName();
		User user = userService.findByEmail(email);
		
		if(user!=null) {
			
		}else {
			
			User newUser = new User(name, email);
			if(userService.allUsers().size()==0) {
				newUser.setRoles(roleRepo.findByName("ROLE_ADMIN"));
			}else {
				newUser.setRoles(roleRepo.findByName("ROLE_USER"));
			}
			userService.saveUser(newUser);
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
