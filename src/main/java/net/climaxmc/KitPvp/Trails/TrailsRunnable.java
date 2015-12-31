package net.climaxmc.KitPvp.Trails;// AUTHOR: gamer_000 (12/26/2015)

import net.climaxmc.ClimaxPvp;

public class TrailsRunnable {

    private ClimaxPvp plugin;

    public TrailsRunnable(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    /*BukkitRunnable trails = new BukkitRunnable() {

        @Override
        public void run() {
            for (UUID uuid : KitPvp.trails.keySet()) {
                Player player = plugin.getServer().getPlayer(uuid);

                if (playerIsMoving(player) == true) {
                    if (KitPvp.trails.get(player.getUniqueId()).equals("fire")) {
                        ParticleEffect2.FLAME.display(0F, 0.1F, 0F, 0, 5, player.getLocation(), 100);
                    }
                }
            }
        }
    };

    public void startTrails() {
        trails.runTaskTimer(plugin, 1L, 1L).getTaskId();
    }

    public void stopTrails() {
        trails.cancel();
    }

    public boolean playerIsMoving(Player player) {
        if (player.getVelocity().getX() != 0 || player.getVelocity().getY() != 0.0 || player.getVelocity().getZ() != 0) {
            return true;
        } else if (player.getVelocity().getX() == 0 && player.getVelocity().getY() == 0.0 && player.getVelocity().getZ() == 0) {
            return false;
        }
        return false;
    }*/
}
