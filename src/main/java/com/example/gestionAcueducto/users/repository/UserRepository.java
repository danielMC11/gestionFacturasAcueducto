package com.example.gestionAcueducto.users.repository;

import com.example.gestionAcueducto.users.entity.User;
import com.example.gestionAcueducto.users.repository.projections.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Repository;
import com.example.gestionAcueducto.users.dto.UserDTO;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	@Modifying
	@Query("update User u set u.password = :password where u.id = :id")
	int updatePassword(@Param("password") String password, @Param("id") Long id);

	@Query("SELECT u.id as id, u.document as document, u.firstName as firstName, " +
			"u.lastName as lastName, u.email as email FROM User u")
	Page<UserProjection> findAllSummary(Pageable pageable);


}
