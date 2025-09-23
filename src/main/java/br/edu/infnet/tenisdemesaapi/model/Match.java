package br.edu.infnet.tenisdemesaapi.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import br.edu.infnet.tenisdemesaapi.repository.MatchRepository;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="matches")
public class Match {
	public Match() {}
	
	public Match(Long id) {
		this.id = id;
	}
	
	public Match(User playerA, User playerB, short bestOf) {
		this.playerA = playerA;
		this.playerB = playerB;
		this.bestOf = bestOf;
		this.createdAt = LocalDateTime.now();
		this.status = MatchStatus.WAITING_ACCEPT;
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
	@ManyToOne
    @JoinColumn(name = "player_a_id")
    private User playerA;
    
    @ManyToOne
    @JoinColumn(name = "player_b_id")
    private User playerB;
    
    private short bestOf;
    
    private LocalDateTime createdAt;
    
    @Enumerated(EnumType.STRING)
    private MatchStatus status;
    
    @ManyToOne
    @JoinColumn(name = "winner_id")
    private User winner;
    
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Game> games = new ArrayList<>();
    
    public Long getId() {
    	return id;
    }
    
    public Long getPlayerAId() {
    	return playerA.getId();
    }
    
    public Long getPlayerBId() {
    	return playerB.getId();
    }
    
    public MatchStatus getStatus() {
    	return status;
    }

	public void setStatus(MatchStatus status) {
		this.status = status;
	}
	
	public void addGame(Game game) {
		if(this.games == null) {
			this.games = new ArrayList<Game>();
		}
		this.games.add(game);
		
		updateMatchStatus();
	}
	
	public User getWinner() {
		return winner;
	}
	
	public void updateMatchStatus() {
		var gamesCount = games.size();
		var gamesToWin = (bestOf + 1) /2;
		
		if(gamesCount >= gamesToWin) {
			var winsA = games.stream().filter(g -> g.getPlayerAScore() > g.getPlayerBScore());
			var winsB = games.stream().filter(g -> g.getPlayerBScore() > g.getPlayerAScore());
			
			User winner = winsA.count() == gamesToWin ? playerA : winsB.count() == gamesToWin ? playerB : null;
			
			if(winner != null) {
				this.winner = winner;
				this.status = MatchStatus.FINISHED;
			}
		}
	}
}
