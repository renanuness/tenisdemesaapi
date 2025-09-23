package br.edu.infnet.tenisdemesaapi.model;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	public User() {}
	
	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String password;
	private int rankingPoints;
	private ZonedDateTime createdAt;
	
	public Long getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getRankingPoints() {
		return this.rankingPoints;
	}
	
	public void setRankingPoints(int points) {
		this.rankingPoints = points;
	}
	
	public void updateRankingPoints(int points) {
		this.rankingPoints += points;
	}
	
	public ZonedDateTime getCreateDate() {
		return this.createdAt;
	}
	
	public void setCreateDate(ZonedDateTime date) {
		this.createdAt = date;
	}
	
	public static User NewUser(String name, String email, String password) {
		var user = new User(name, email, password);
		user.setRankingPoints(250);
		user.setCreateDate(ZonedDateTime.now(ZoneId.of("UTC")));
		
		return user;
	}
}
