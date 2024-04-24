package dev.goldenedit.leaderboardplugin;

public class LeaderboardPlayer {
    private String name;

    private int points;

    private String uuid;

    public LeaderboardPlayer(String name, int points, String uuid) {
        this.name = name;
        this.points = points;
        this.uuid = uuid;
    }

    public String getName() {
        return this.name;
    }

    public int getPoints() {
        return this.points;
    }

    public String getUuid() {
        return this.uuid;
    }
}
