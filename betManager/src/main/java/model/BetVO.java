package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "bet")
public class BetVO {

    public static final String SEQUENCE_NAME = "bets_sequence";

    @Id
    private String id;
    private double value;
    private double odd;
    private String betStyle;
    private boolean greenOrRed;
    private Game game;
    private Date date;

    public BetVO() {}

    public BetVO(String id, double value, double odd, String betStyle, boolean green, Game game) {
        this.id = id;
        this.value = value;
        this.odd = odd;
        this.betStyle = betStyle;
        this.greenOrRed = greenOrRed;
        this.game = game;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Boolean isGreenOrRed() {
        return greenOrRed;
    }

    public void setGreenOrRed(boolean greenOrRed) {
        this.greenOrRed = greenOrRed;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
