package net.climaxmc.API;

import com.avaje.ebean.validation.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="Stats")
public class Statistics {
    @NotNull @Getter private UUID uuid;
    @NotNull @Getter @Setter private int kills;
    @NotNull @Getter @Setter private int deaths;
}
