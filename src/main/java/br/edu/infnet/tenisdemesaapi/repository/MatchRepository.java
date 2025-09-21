package br.edu.infnet.tenisdemesaapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.infnet.tenisdemesaapi.model.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {
	@Query("select m from Match m where m.id = ?1")
	Match getById(Long id);
	
	@Query("select count(m)>0 from Match m where m.status = 'WAITING_ACCEPT' AND m.playerA.id = ?1")
	boolean hasPendingInvitesSent(Long userId);

	@Query("select m from Match m where m.status = 'WAITING_ACCEPT' AND m.playerB.id = ?1")
	List<Match> getPendingInvitesReceived(Long userId);
}
