package net.climaxmc.API;

import com.avaje.ebean.validation.NotNull;
import lombok.Data;
import org.bukkit.OfflinePlayer;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Statistics")
@Data
public class Statistics {
    @Id
    private final UUID uuid;
    @NotNull
    private int kills;
    @NotNull
    private int deaths;

    public static Statistics getStatistics(OfflinePlayer player) {
        return getStatistics(player.getUniqueId());
    }

    public static Statistics getStatistics(UUID uuid) {
        return new Statistics(uuid);
    }
}
