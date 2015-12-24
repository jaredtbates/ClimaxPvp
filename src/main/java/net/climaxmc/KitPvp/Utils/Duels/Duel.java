package net.climaxmc.KitPvp.Utils.Duels;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
public class Duel {
    @Getter
    private final UUID player1UUID;
    @Getter
    private final UUID player2UUID;
    @Getter
    @Setter
    private boolean accepted;
}
