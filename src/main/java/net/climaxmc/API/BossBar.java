package net.climaxmc.API;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.*;
import java.util.*;

public class BossBar {
    private static BossBar instance;
    private Map<String, Dragon> dragonMap = new HashMap<String, Dragon>();

    public static BossBar getInstance() {
        if (BossBar.instance == null)
            BossBar.instance = new BossBar();

        return BossBar.instance;
    }

    public void setStatus(Player player, String text, float percent, boolean reset)
            throws IllegalArgumentException, SecurityException, InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Dragon dragon = null;

        if (dragonMap.containsKey(player.getName()) && !reset) {
            dragon = dragonMap.get(player.getName());
        } else {
            dragon = new Dragon(text, player.getLocation().add(0, -100, 0), percent);
            Object mobPacket = dragon.getSpawnPacket();
            sendPacket(player, mobPacket);
            dragonMap.put(player.getName(), dragon);
        }

        if (text.equals("")) {
            Object destroyPacket = dragon.getDestroyPacket();
            sendPacket(player, destroyPacket);
            dragonMap.remove(player.getName());
        } else {
            dragon.setName(text);
            dragon.setHealth(percent);
            Object metaPacket = dragon.getMetaPacket(dragon.getWatcher());
            Object teleportPacket = dragon
                    .getTeleportPacket(player.getLocation().add(0, -100, 0));
            sendPacket(player, metaPacket);
            sendPacket(player, teleportPacket);
        }
    }

    private void sendPacket(Player player, Object packet) {
        try {
            Object nmsPlayer = ReflectionUtils.getHandle(player);
            Field con_field = nmsPlayer.getClass().getField("playerConnection");
            Object con = con_field.get(nmsPlayer);
            Method packet_method = ReflectionUtils.getMethod(con.getClass(), "sendPacket");
            packet_method.invoke(con, packet);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private static class ReflectionUtils {
        public static void sendPacket(List<Player> players, Object packet) {
            for (Player p : players) {
                sendPacket(p, packet);
            }
        }

        public static void sendPacket(Player p, Object packet) {
            try {
                Object nmsPlayer = getHandle(p);
                Field con_field = nmsPlayer.getClass().getField("playerConnection");
                Object con = con_field.get(nmsPlayer);
                Method packet_method = getMethod(con.getClass(), "sendPacket");
                packet_method.invoke(con, packet);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        public static Class<?> getCraftClass(String ClassName) {
            String name = Bukkit.getServer().getClass().getPackage().getName();
            String version = name.substring(name.lastIndexOf('.') + 1) + ".";
            String className = "net.minecraft.server." + version + ClassName;
            Class<?> c = null;
            try {
                c = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return c;
        }

        public static Object getHandle(Entity entity) {
            Object nms_entity = null;
            Method entity_getHandle = getMethod(entity.getClass(), "getHandle");
            try {
                nms_entity = entity_getHandle.invoke(entity);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return nms_entity;
        }

        public static Object getHandle(World world) {
            Object nms_entity = null;
            Method entity_getHandle = getMethod(world.getClass(), "getHandle");
            try {
                nms_entity = entity_getHandle.invoke(world);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return nms_entity;
        }

        public static Field getField(Class<?> cl, String field_name) {
            try {
                Field field = cl.getDeclaredField(field_name);
                return field;
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static Method getMethod(Class<?> cl, String method, Class<?>[] args) {
            for (Method m : cl.getMethods()) {
                if (m.getName().equals(method) && ClassListEqual(args, m.getParameterTypes())) {
                    return m;
                }
            }
            return null;
        }

        public static Method getMethod(Class<?> cl, String method, Integer args) {
            for (Method m : cl.getMethods()) {
                if (m.getName().equals(method)
                        && args.equals(Integer.valueOf(m.getParameterTypes().length))) {
                    return m;
                }
            }
            return null;
        }

        public static Method getMethod(Class<?> cl, String method) {
            for (Method m : cl.getMethods()) {
                if (m.getName().equals(method)) {
                    return m;
                }
            }
            return null;
        }

        public static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
            boolean equal = true;

            if (l1.length != l2.length)
                return false;
            for (int i = 0; i < l1.length; i++) {
                if (l1[i] != l2[i]) {
                    equal = false;
                    break;
                }
            }

            return equal;
        }
    }

    private class Dragon {
        private static final int MAX_HEALTH = 200;
        private int id;
        private int x;
        private int y;
        private int z;
        private int pitch = 0;
        private int yaw = 0;
        private byte xvel = 0;
        private byte yvel = 0;
        private byte zvel = 0;
        private float health;
        private boolean visible = false;
        private String name;
        private Object world;

        private Object dragon;

        public Dragon(String name, Location loc, float percent) {
            this.name = name;
            this.x = loc.getBlockX();
            this.y = loc.getBlockY();
            this.z = loc.getBlockZ();
            this.health = percent / 100F * MAX_HEALTH;
            this.world = ReflectionUtils.getHandle(loc.getWorld());
        }

        public void setHealth(float percent) {
            this.health = percent / 100F * MAX_HEALTH;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getSpawnPacket() throws IllegalArgumentException, SecurityException,
                InstantiationException, IllegalAccessException, InvocationTargetException,
                NoSuchMethodException {
            Class<?> Entity = ReflectionUtils.getCraftClass("Entity");
            Class<?> EntityLiving = ReflectionUtils.getCraftClass("EntityLiving");
            Class<?> EntityEnderDragon = ReflectionUtils.getCraftClass("EntityEnderDragon");
            dragon = EntityEnderDragon.getConstructor(ReflectionUtils.getCraftClass("World"))
                    .newInstance(world);

            Method setLocation = ReflectionUtils.getMethod(EntityEnderDragon, "setLocation",
                    new Class<?>[]
                            {double.class, double.class, double.class, float.class, float.class});
            setLocation.invoke(dragon, x, y, z, pitch, yaw);

            Method setInvisible = ReflectionUtils.getMethod(EntityEnderDragon, "setInvisible",
                    new Class<?>[]
                            {boolean.class});
            setInvisible.invoke(dragon, visible);

            Method setCustomName = ReflectionUtils.getMethod(EntityEnderDragon, "setCustomName",
                    new Class<?>[]
                            {String.class});
            setCustomName.invoke(dragon, name);

            Method setHealth = ReflectionUtils.getMethod(EntityEnderDragon, "setHealth",
                    new Class<?>[]
                            {float.class});
            setHealth.invoke(dragon, health);

            Field motX = ReflectionUtils.getField(Entity, "motX");
            motX.set(dragon, xvel);

            Field motY = ReflectionUtils.getField(Entity, "motX");
            motY.set(dragon, yvel);

            Field motZ = ReflectionUtils.getField(Entity, "motX");
            motZ.set(dragon, zvel);

            Method getId = ReflectionUtils.getMethod(EntityEnderDragon, "getId",
                    new Class<?>[]{});
            this.id = (Integer) getId.invoke(dragon);

            Class<?> PacketPlayOutSpawnEntityLiving = ReflectionUtils
                    .getCraftClass("PacketPlayOutSpawnEntityLiving");

            Object packet = PacketPlayOutSpawnEntityLiving.getConstructor(new Class<?>[]
                    {EntityLiving}).newInstance(dragon);

            return packet;
        }

        public Object getDestroyPacket() throws IllegalArgumentException, SecurityException,
                InstantiationException, IllegalAccessException, InvocationTargetException,
                NoSuchMethodException {
            Class<?> PacketPlayOutEntityDestroy = ReflectionUtils
                    .getCraftClass("PacketPlayOutEntityDestroy");

            Object packet = PacketPlayOutEntityDestroy.getConstructors()[0].newInstance(id);

            return packet;
        }

        public Object getMetaPacket(Object watcher) throws IllegalArgumentException,
                SecurityException, InstantiationException, IllegalAccessException,
                InvocationTargetException, NoSuchMethodException {
            Class<?> DataWatcher = ReflectionUtils.getCraftClass("DataWatcher");

            Class<?> PacketPlayOutEntityMetadata = ReflectionUtils
                    .getCraftClass("PacketPlayOutEntityMetadata");

            Object packet = PacketPlayOutEntityMetadata.getConstructor(new Class<?>[]
                    {int.class, DataWatcher, boolean.class}).newInstance(id, watcher, true);

            return packet;
        }

        public Object getTeleportPacket(Location loc) throws IllegalArgumentException,
                SecurityException, InstantiationException, IllegalAccessException,
                InvocationTargetException, NoSuchMethodException {
            Class<?> PacketPlayOutEntityTeleport = ReflectionUtils
                    .getCraftClass("PacketPlayOutEntityTeleport");

            Object packet = PacketPlayOutEntityTeleport.getConstructor(new Class<?>[]
                    {int.class, int.class, int.class, int.class, byte.class, byte.class})
                    .newInstance(this.id, loc.getBlockX() * 32, loc.getBlockY() * 32,
                            loc.getBlockZ() * 32, (byte) ((int) loc.getYaw() * 256 / 360),
                            (byte) ((int) loc.getPitch() * 256 / 360));

            return packet;
        }

        public Object getWatcher() throws IllegalArgumentException, SecurityException,
                InstantiationException, IllegalAccessException, InvocationTargetException,
                NoSuchMethodException {
            Class<?> Entity = ReflectionUtils.getCraftClass("Entity");
            Class<?> DataWatcher = ReflectionUtils.getCraftClass("DataWatcher");

            Object watcher = DataWatcher.getConstructor(new Class<?>[]
                    {Entity}).newInstance(dragon);

            Method a = ReflectionUtils.getMethod(DataWatcher, "a", new Class<?>[]
                    {int.class, Object.class});

            a.invoke(watcher, 0, visible ? (byte) 0 : (byte) 0x20);
            a.invoke(watcher, 6, (Float) health);
            a.invoke(watcher, 7, (Integer) 0);
            a.invoke(watcher, 8, (Byte) (byte) 0);
            a.invoke(watcher, 10, name);
            a.invoke(watcher, 11, (Byte) (byte) 1);
            return watcher;
        }
    }
}