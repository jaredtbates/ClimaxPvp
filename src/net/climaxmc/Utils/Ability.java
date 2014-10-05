package net.climaxmc.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.entity.Player;

public class Ability {
    /**
     * Contains the number of charges and cooldown.
     * @author Kristian Stangeland
     */
    public static class Status {
        private int charges;
        
        private long cooldown;
        private boolean recharged;
 
        public Status(int charges) {
            this.charges = charges;
            this.cooldown = 0;
            this.recharged = true;
        }
 
        /**
         * Retrieve the number of times the current player can use this ability before initiating a new cooldown.
         * @return Number of charges.
         */
        public int getCharges() {
            return charges;
        }
        
        /**
         * Set the number of valid charges left
         * @param charges - the new number of charges.
         */
        public void setCharges(int charges) {
            this.charges = charges;
        }
        
        /**
         * Determine if this ability has been recharged when the cooldown last expired.
         * @return TRUE if it has, FALSE otherwise.
         */
        public boolean isRecharged() {
            return recharged;
        }
        
        /**
         * Set if this ability has been recharged when the cooldown last expired.
         * @param recharged - whether or not the ability has been recharged.
         */
        public void setRecharged(boolean recharged) {
            this.recharged = recharged;
        }
    
        /**
         * Lock the current ability for the duration of the cooldown.
         * @param delay - the number of milliseconds to wait.
         */
        public void setCooldown(long delay, TimeUnit unit) {
            this.cooldown = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(delay, unit);
        }
        
        /**
         * Determine if the cooldown has expired.
         * @return TRUE if it has, FALSE otherwise.
         */
        public boolean isExpired() {
            long rem = getRemainingTime(TimeUnit.MILLISECONDS);
            return rem < 0;
        }
        
        /**
         * Retrieve the of milliseconds until the cooldown expires.
         * @param unit - the unit of the resulting time.
         * @return Number of milliseconds until expiration.
         */
        public long getRemainingTime(TimeUnit unit) {
            return unit.convert(cooldown - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }
    }
    
    private Map<UUID, Status> playerStatus = new HashMap<UUID, Status>();
    
    private final int defaultCharges;
    private final long defaultDelay;
    
    public Ability(int defaultCharges) {
        this(defaultCharges, 5, TimeUnit.SECONDS);
    }
    
    public Ability(int defaultCharges, int defaultDelay, TimeUnit unit) {
        this.defaultCharges = defaultCharges;
        this.defaultDelay = TimeUnit.MILLISECONDS.convert(defaultDelay, unit);
    }
    
    /**
     * Retrieve the cooldown time and charge count for a given player.
     * @param player - the player.
     * @return Associated status.
     */
    public Status getStatus(Player player) {
        Status status = playerStatus.get(player.getUniqueId());
        
        if (status == null) {
            status = createStatus(player);
            playerStatus.put(player.getUniqueId(), status);
        } else {
            checkStatus(player, status);
        }
        return status;
    }
    
    /**
     * Attempt to use this ability. The player must have at least once charge for this operation 
     * to be successful. The player's charge count will be decremented by the given amount.
     * <p>
     * Otherwise, initiate the recharging cooldown and return FALSE.
     * @param player - the player.
     * @param charges - the number of charges to consume. 
     * @return TRUE if the operation was successful, FALSE otherwise.
     */
    public boolean tryUse(Player player) {
        return tryUse(player, 1, defaultDelay, TimeUnit.MILLISECONDS);
    }
    
    /**
     * Attempt to use this ability. The player must have at least once charge for this operation 
     * to be successful. The player's charge count will be decremented by the given amount.
     * <p>
     * Otherwise, initiate the recharging cooldown and return FALSE.
     * @param player - the player.
     * @param delay - the duration of the potential cooldown.
     * @param unit - the unit of the delay parameter.
     * @return TRUE if the operation was successful, FALSE otherwise.
     */
    public boolean tryUse(Player player, long delay, TimeUnit unit) {
        return tryUse(player, delay, unit);
    }
    
    /**
     * Attempt to use this ability. The player must have at least once charge for this operation 
     * to be successful. The player's charge count will be decremented by the given amount.
     * <p>
     * Otherwise, initiate the recharging cooldown and return FALSE.
     * @param player - the player.
     * @param charges - the number of charges to consume. 
     * @param delay - the duration of the potential cooldown.
     * @param unit - the unit of the delay parameter.
     * @return TRUE if the operation was successful, FALSE otherwise.
     */
    public boolean tryUse(Player player, int charges, long delay, TimeUnit unit) {
        Status status = getStatus(player);
        int current = status.getCharges();
 
        // Check cooldown
        if (!status.isExpired())
            return false;
        
        if (current <= charges) {
            status.setRecharged(false);
            status.setCharges(0);
            status.setCooldown(delay, unit);
        } else {
            status.setCharges(current - charges);
        }
        return current > 0;
    }
    
    private void checkStatus(Player player, Status status) {
        if (!status.isRecharged() && status.isExpired()) {
            rechargeStatus(player, status);
        }
    }
    
    /**
     * Invoked when a status must be recharged. 
     * @param player - the player to recharge.
     * @param status - the status to update.
     * @return The updated status.
     */
    protected Status rechargeStatus(Player player, Status status) {
        status.setRecharged(true);
        status.setCharges(defaultCharges);
        return status;
    }
    
    /**
     * Invoked when we need to create a status object for a player.
     * @param player - the player to create for.
     * @return The new status object.
     */
    protected Status createStatus(Player player) {
        return new Status(defaultCharges);
    }
}
