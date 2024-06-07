package com.ammar.topgamesapp;

public class GameModel {
    private String gameName;
    private int gameImage;

    public GameModel(String gameName, int gameImage) {
        this.gameName = gameName;
        this.gameImage = gameImage;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getGameImage() {
        return gameImage;
    }

    public void setGameImage(int gameImage) {
        this.gameImage = gameImage;
    }
}
