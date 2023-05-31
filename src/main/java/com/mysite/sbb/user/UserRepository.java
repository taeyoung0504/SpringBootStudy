package com.mysite.sbb.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
	Optional<SiteUser> findByusername(String username);

	List<SiteUser> findByUsername(String username);

	Optional<SiteUser> findByEmail(String email);
	
    @Query("SELECT u FROM SiteUser u WHERE u.username = :username AND u.email = :email")
    Optional<SiteUser> findByUsernameAndEmail(@Param("username") String username, @Param("email") String email);
}