package com.example.gestionAcueducto.users.repository;

import com.example.gestionAcueducto.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	@Modifying
	@Query("update User u set u.password = :password where u.id = :id")
	int updatePassword(@Param("password") String password, @Param("id") Long id);
}
