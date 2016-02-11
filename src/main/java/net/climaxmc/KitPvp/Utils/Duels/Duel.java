package net.climaxmc.KitPvp.Utils.Duels;
/* Created by GamerBah on 2/9/2016 */

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class Duel {

    @Getter
    @Setter
    private UUID player1UUID;
    @Getter
    @Setter
    private UUID player2UUID;
    @Getter
    @Setter
    private boolean accepted;
    @Getter
    @Setter
    private boolean isStarted;

}
