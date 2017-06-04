package net.climaxmc.KitPvp.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import net.climaxmc.ClimaxPvp;
import org.bukkit.Bukkit;

public class PacketCore {

    public PacketCore(ClimaxPvp plugin) {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, PacketType.Play.Server.ENTITY_STATUS) {
            @Override
            public void onPacketSending(PacketEvent event) {
                event.setCancelled(false);
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, PacketType.Play.Server.ENTITY_VELOCITY) {
            @Override
            public void onPacketSending(PacketEvent event) {
                event.setCancelled(false);
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, PacketType.Play.Server.ENTITY_EFFECT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                event.setCancelled(false);
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, PacketType.Play.Server.ENTITY) {
            @Override
            public void onPacketSending(PacketEvent event) {
                event.setCancelled(false);
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, PacketType.Play.Server.ENTITY_DESTROY) {
            @Override
            public void onPacketSending(PacketEvent event) {
                event.setCancelled(false);
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, PacketType.Play.Server.ENTITY_METADATA) {
            @Override
            public void onPacketSending(PacketEvent event) {
                event.setCancelled(false);
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, PacketType.Play.Server.REMOVE_ENTITY_EFFECT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                event.setCancelled(false);
            }
        });

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, PacketType.Play.Server.KEEP_ALIVE) {
            @Override
            public void onPacketSending(PacketEvent event) {
                Bukkit.getServer().getPluginManager().callEvent(new PacketServerKeepAliveEvent(event.getPlayer()));
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, PacketType.Play.Client.KEEP_ALIVE) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Bukkit.getServer().getPluginManager().callEvent(new PacketClientKeepAliveEvent(event.getPlayer()));
            }
        });
    }
}
