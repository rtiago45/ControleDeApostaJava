package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bet")
public class BetVO {
	@Id
	private String id;
	private double value;
	private double odd;
	private String betStyle;
	private String[] teams;
	private String competition;

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getOdd() {
		return odd;
	}

	public void setOdd(double odd) {
		this.odd = odd;
	}

	public String getBetStyle() {
		return betStyle;
	}

	public void setBetStyle(String betStyle) {
		this.betStyle = betStyle;
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
}
