package model;

import java.util.List;

public class MultipleBetVO extends BetVO {
    private List<Game> games;

    public MultipleBetVO() {
        setBetStyle("M");
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
