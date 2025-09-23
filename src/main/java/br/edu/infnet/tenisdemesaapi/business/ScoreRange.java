package br.edu.infnet.tenisdemesaapi.business;

public class ScoreRange {
	private int lowBound;
	private int highBound;
	private int winnerMultiplier;
	private int loserMultiplier;
	
	public int getLowBound() {
		return lowBound;
	}
	
	public void setLowBound(int lowBound) {
		this.lowBound = lowBound;
	}
	
	public int getHighBound() {
		return highBound;
	}
	
	public void setHighBound(int highBound) {
		this.highBound = highBound;
	}

	public int getwinnerMultiplier() {
		return winnerMultiplier;
	}
	
	public void setWinnerMultiplier(int winnerMultiplier) {
		this.winnerMultiplier = winnerMultiplier;
	}
	public int getLoserMulptiplier() {
		return loserMultiplier;
	}
	
	public void setLoserMultiplier(int loserMultiplier) {
		this.loserMultiplier = loserMultiplier;
	}
	
	public boolean inRange(int points) {
		return points >= lowBound && points <= highBound;
	}
}
