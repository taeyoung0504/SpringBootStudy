package com.mysite.sbb.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<SiteUser> _siteUser = this.userRepository.findByusername(username);
		if (_siteUser.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}

		SiteUser siteUser = _siteUser.get();
		Integer admin = siteUser.getAdmin();
		Integer partner = siteUser.getPartner();

		List<GrantedAuthority> authorities = new ArrayList<>();
		if (admin > 1000 && admin < partner) {
			siteUser.setRole(UserRole.ADMIN); // Set the role value to ADMIN
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		} else if (partner > 2000 && partner < admin) {
			siteUser.setRole(UserRole.PARTNER); // Set the role value to PARTNER
			authorities.add(new SimpleGrantedAuthority(UserRole.PARTNER.getValue()));
		} else {
			siteUser.setRole(UserRole.USER); // Set the role value to PARTNER
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}

		this.userRepository.save(siteUser); // Save the updated SiteUser object

		return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
	}
}