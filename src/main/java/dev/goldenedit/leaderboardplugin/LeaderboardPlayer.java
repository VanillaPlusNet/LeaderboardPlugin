package dev.goldenedit.leaderboardplugin;

public class LeaderboardPlayer {
    private String name;

    private int points;

    public LeaderboardPlayer(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return this.name;
    }

    public int getPoints() {
        return this.points;
    }

    public boolean equals(String name) {
        return name.equals(name);
    }
}
