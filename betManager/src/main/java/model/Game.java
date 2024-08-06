package model;

import java.util.List;

public class Game {
	private String[] teams;
	private String competition;
	private List<OneEntranceDetail> entrances;

	public Game() {}

	public Game(String[] teams, String competition, List<OneEntranceDetail> entrances) {
		this.teams = teams;
		this.competition = competition;
		this.entrances = entrances;
	}

	public String[] getTeams() {
		return teams;
	}

	public void setTeams(String[] teams) {
		this.teams = teams;
	}

	public String getCompetition() {
		return competition;
	}

	public void setCompetition(String competition) {
		this.competition = competition;
	}

	public List<OneEntranceDetail> getEntrances() {
		return entrances;
	}

	public void setEntrances(List<OneEntranceDetail> entrances) {
		this.entrances = entrances;
	}
}
