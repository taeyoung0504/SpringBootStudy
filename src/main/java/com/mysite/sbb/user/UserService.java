package com.mysite.sbb.user;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        this.userRepository.save(user);
        return user;
    }
    
    public List<SiteUser> check(String username){
    	return userRepository.findByUsername(username);
    }
    
    
    public Optional<SiteUser> check2(String email){
    	return userRepository.findByEmail(email);
    }
    
    public List<SiteUser> find(String username, String email) {
    	
        return userRepository.findByUsernameAndEmail(username, email)
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());
    }
    
//임시비밀번호로 패스워드 변경
    public void save(SiteUser user) {
    	this.userRepository.save(user);
        System.out.println("Saving user: " + user.getUsername() + ", Password: " + user.getPassword());
    }
  
}