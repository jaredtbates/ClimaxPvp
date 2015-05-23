package net.climaxmc.API;

import com.avaje.ebean.validation.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Statistics")
@Data
public class Statistics {
    @Id
    @NotNull
    private UUID uuid;
    @NotNull
    private int kills = 0;
    @NotNull
    private int deaths = 0;
}
