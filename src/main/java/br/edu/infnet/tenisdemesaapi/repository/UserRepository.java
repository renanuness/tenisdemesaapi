package br.edu.infnet.tenisdemesaapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.infnet.tenisdemesaapi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	@Query("select u from User u where email = ?1")
	User findByEmail(String email);
	
	@Query("select u from User u where id = ?1")
	User getById(Long id);
	
	@Query("select u from User u where id != ?1")
	List<User> listOtherUsers(Long id);
	
	@Modifying
	@Query("update User	 u set u.rankingPoints = ?2 where u.id = ?1")
	int updateRankingPoints(Long id, int rankingPoints);
	
}
