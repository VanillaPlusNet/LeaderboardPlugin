# LeaderboardPlugin

## About

This is a Minecraft plugin that adds a leaderboard for points that players earn when killing other players. Points are calculated based on the armour that the player was wearing.

Could be used for servers that want to:

- Add more of a competitive edge to the server.
- Encourage more PvP in expensive gear.
- Get players to spend more time griding on the server to be at the top of the leaderboard.

Requires [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) for the placeholder functionality.

![image](https://github.com/VanillaPlusNet/LeaderboardPlugin/assets/45533337/5709df20-4818-4990-90cc-e793150b6bf1)



## Commands and Permissions

`/points <player>` `leaderboardplugin.points` - See how many points a specific player has.

`/killboard` `leaderboardplugin.killboard` - See the top 1000 players on the scoreboard.

`/calcpoints <player>` `leaderboardplugin.calcpoints` - Calculate the number of points a player is worth.


## Placeholders

`%leaderboardplugin_position1%` - Player with the 1st highest points.

`%leaderboardplugin_position2%` - Player with the 2nd highest points.

`%leaderboardplugin_position3%` - Player with the 3rd highest points.

`%leaderboardplugin_position4%` - Player with the 4th highest points.

`%leaderboardplugin_position5%` - Player with the 5th highest points.

`%leaderboardplugin_self%` - Your current points.


## Resetting the Database

1. Shut down the server.
2. Delete the `data.json` file in the `/LeaderboardPlugin` folder.
3. Start the server back up.


