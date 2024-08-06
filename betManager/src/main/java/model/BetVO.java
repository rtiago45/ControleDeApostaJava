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
    private boolean green;
    private Game game;

    public BetVO() {}

    public BetVO(String id, double value, double odd, String betStyle, boolean green, Game game) {
        this.id = id;
        this.value = value;
        this.odd = odd;
        this.betStyle = betStyle;
        this.green = green;
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

    public boolean isGreen() {
        return green;
    }

    public void setGreen(boolean green) {
        this.green = green;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
