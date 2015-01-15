package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WitchKit extends Kit {
    public WitchKit() {
        super("Witch", new ItemStack(Material.POTION), "", KitType.AMATEUR);
    }

    public void wear(Player player) {

    }
}
