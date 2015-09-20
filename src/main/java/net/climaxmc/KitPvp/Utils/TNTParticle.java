package net.climaxmc.KitPvp.Utils;

import net.climaxmc.ClimaxPvp;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TNTParticle extends BukkitRunnable {
    private double count = 0.0D;
    private Entity entity;

    public TNTParticle(TNTPrimed tnt) {
        this.entity = tnt;
        runTaskTimer(ClimaxPvp.getInstance(), 10, 5);
    }

    @Override
    public void run() {
        if (entity.isValid()) {
            Location location = entity.getLocation();

            double count2 = count;
            double hauteure2 = -1.0D;
            double rayon2 = 1.7D;

            List<Location> points2 = new ArrayList<>();
            ArrayList<Location> points = new ArrayList<>();
            for (; ; ) {
                double nombre2 = 3.141592653589793D + count2 * 3.141592653589793D / 7.0D;
                Location loc2 = new Location(entity.getWorld(), location.getX() +
                        Math.cos(nombre2 * 0.6) *
                                rayon2, location.getY() +
                        hauteure2, location.getZ() +
                        Math.sin(nombre2 * 0.6) *
                                rayon2);
                Location loc3 = new Location(entity.getWorld(), location.getX() +
                        Math.cos(nombre2 * 0.6) *
                                -rayon2, location.getY() +
                        hauteure2, location.getZ() +
                        Math.sin(nombre2 * 0.6) *
                                -rayon2);

                points2.add(loc2);
                points.add(loc3);
                if (count2 >= 36.0D + count) {
                    break;
                }
                rayon2 -= 0.04D;
                hauteure2 += 0.11D;
                count2 += 1.0D;
            }
            for (Location l4 : points2) {
                ParticleEffect2.REDSTONE.display(new ParticleEffect2.OrdinaryColor(Color.RED), l4, Bukkit.getOnlinePlayers().stream().collect(Collectors.toList()));
            }
            for (Location l3 : points) {
                ParticleEffect2.REDSTONE.display(new ParticleEffect2.OrdinaryColor(Color.RED), l3, Bukkit.getOnlinePlayers().stream().collect(Collectors.toList()));
            }
        } else {
            cancel();
        }
        if (count >= 30.0D) {
            count = 0.0D;
        }
        count += 0.2;
    }
}