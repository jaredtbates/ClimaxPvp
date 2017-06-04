package net.climaxmc.KitPvp.Utils.Fair;

import java.util.UUID;

public class MatchManager {

    public static boolean isInMatch(UUID uuid) {
        for (Match match : FairUtils.runningMatches) {
            if (match.player.equals(uuid)) {
                return true;
            }
            if (match.opponent.equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    public static Match getMatch(UUID uuid) {
        for (Match match : FairUtils.runningMatches) {
            if (match.player.equals(uuid)) {
                return match;
            }
        }
        return null;
    }

    public static UUID getOpponent(UUID uuid) {
        for (Match match : FairUtils.runningMatches) {
            if (match.player.equals(uuid)) {
                return match.opponent;
            }
            if (match.opponent.equals(uuid)) {
                return match.player;
            }
        }
        return null;
    }
}
