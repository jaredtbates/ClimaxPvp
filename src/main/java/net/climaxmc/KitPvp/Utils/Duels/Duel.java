package net.climaxmc.KitPvp.Utils.Duels;
/* Created by GamerBah on 2/9/2016 */

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class Duel {

    @Getter
    @Setter
    private UUID player1uuid;
    @Getter
    @Setter
    private UUID player2uuid;
    @Getter
    @Setter
    private boolean accepted;

}
