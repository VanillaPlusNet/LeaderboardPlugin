package dev.goldenedit.leaderboardplugin;

import java.util.UUID;

public class LeaderboardPlayer {
    private String name;

    private int points;

    private UUID uuid;

    public LeaderboardPlayer(String name, int points) {
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

    public UUID getUuid() {
        return this.uuid;
    }
}
