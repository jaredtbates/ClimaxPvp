package net.climaxmc.Administration.Punishments;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Punishment {
    private UUID playerUUID;
    private PunishType type;
    private long time;
    private long expiration;
    private UUID punisherUUID;
    private String reason;

    public enum PunishType {
        BAN,
        MUTE,
        KICK
    }
}