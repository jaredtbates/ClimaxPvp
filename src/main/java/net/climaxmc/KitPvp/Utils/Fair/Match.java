package net.climaxmc.KitPvp.Utils.Fair;

import java.util.UUID;

public class Match {

    public UUID player;
    public UUID opponent;

    public Match(UUID player, UUID opponent) {
        this.player = player;
        this.opponent = opponent;
    }

}
