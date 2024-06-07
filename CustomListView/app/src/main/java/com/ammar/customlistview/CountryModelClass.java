package com.ammar.customlistview;

public class CountryModelClass {
    private String countryName, worldCupWinCount;
    private int countryFlagImage;

    public CountryModelClass(String countryName, String worldCupWinCount, int countryFlagImage) {
        this.countryName = countryName;
        this.worldCupWinCount = worldCupWinCount;
        this.countryFlagImage = countryFlagImage;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getWorldCupWinCount() {
        return worldCupWinCount;
    }

    public void setWorldCupWinCount(String worldCupWinCount) {
        this.worldCupWinCount = worldCupWinCount;
    }

    public int getCountryFlagImage() {
        return countryFlagImage;
    }

    public void setCountryFlagImage(int countryFlagImage) {
        this.countryFlagImage = countryFlagImage;
    }
}
