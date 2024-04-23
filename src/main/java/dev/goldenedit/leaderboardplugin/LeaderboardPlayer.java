package dev.goldenedit.leaderboardplugin;

public class LeaderboardPlayer {
    private String name;

    private int kills;

    public LeaderboardPlayer(String name, int kills) {
        this.name = name;
        this.kills = kills;
    }

    public String getName() {
        return this.name;
    }

    public int getPoints() {
        return this.kills;
    }

    public boolean equals(String name) {
        return name.equals(name);
    }
}
