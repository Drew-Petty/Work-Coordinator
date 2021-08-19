package com.dp.workManager.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.dp.workManager.oAuth2.OAuth2LoginSuccessHandler;
import com.dp.workManager.oAuth2.OAuth2UserService;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private UserDetailsService userDetailsService;
	
	public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.
        authorizeRequests()
            .antMatchers("/static/**","/css/**","/img/**", "/registration").permitAll()
            .antMatchers("/admin/**").access("hasRole('ADMIN')")
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login")
            .usernameParameter("email")
            .successForwardUrl("/")
            .permitAll()
        .and()
        .oauth2Login()
        	.loginPage("/login")
        	.userInfoEndpoint()
        	.userService(oAuth2UserService)
        .and()
        .successHandler(oAuth2LoginSuccessHandler)
//        .successHandler(customSuccessHandler)
        .and()
        .logout()
            .permitAll();
	
			
	}
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	@Autowired
	private OAuth2UserService oAuth2UserService;
	@Autowired
	private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
//	@Autowired
//	private CustomSuccessHandler customSuccessHandler;
}
