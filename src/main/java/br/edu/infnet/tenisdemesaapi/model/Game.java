package br.edu.infnet.tenisdemesaapi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="games")
public class Game {
	
	public Game(Long matchId, short playerAScore, short playerBScore) {
		this.match = new Match(matchId);
		this.playerAScore = playerAScore;
		this.playerBScore = playerBScore;
		this.createdAt = LocalDateTime.now();
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;
    
    @Column(name = "player_a_score") 
	private short playerAScore;
    @Column(name = "player_b_score")
	private short playerBScore;
	private LocalDateTime createdAt;
	
	public short getPlayerAScore() {
		return playerAScore;
	}
	
	public short getPlayerBScore() {
		return playerBScore;
	}
	
}
