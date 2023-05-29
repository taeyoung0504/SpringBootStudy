package com.mysite.sbb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.mysite.sbb.user.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private  CustomOAuth2UserService customOAuth2UserService;
	
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	   
    	http.authorizeHttpRequests().requestMatchers(
    	            new AntPathRequestMatcher("/admin/*")).hasRole("ADMIN")
   	 		.anyRequest().permitAll()
   	 	.and()
        .formLogin()
        .loginPage("/user/login")
        .defaultSuccessUrl("/")
        .and()
        /*
        .csrf().ignoringRequestMatchers(
            new AntPathRequestMatcher("/user/check"))
            
            
        .and()
        */
        .csrf().disable()
    	.logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
        .logoutSuccessUrl("/")
        .invalidateHttpSession(true)
        .and()
        .oauth2Login()
        .defaultSuccessUrl("/question/list")
        .userInfoEndpoint()
        .userService(customOAuth2UserService);
   	 		
    	
        return http.build();
        }   
    
    

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}