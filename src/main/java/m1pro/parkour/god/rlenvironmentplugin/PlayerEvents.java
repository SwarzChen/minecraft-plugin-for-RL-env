package m1pro.parkour.god.rlenvironmentplugin;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public class PlayerEvents implements Listener {
    ArrayList<IntegerLocation> PastLocationList = new ArrayList<>();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location locationBelowPlayer = player.getLocation().subtract(0, 1, 0);
        int xInteger = (int) Math.floor(locationBelowPlayer.getX());
        int yInteger = (int) Math.floor(locationBelowPlayer.getY());
        int zInteger = (int) Math.floor(locationBelowPlayer.getZ());
        IntegerLocation integerLocation = new IntegerLocation(xInteger, yInteger, zInteger);

        String blockNameBelowPlayer = locationBelowPlayer.getBlock().getType().toString();

        if(!this.PastLocationList.contains(integerLocation) && !blockNameBelowPlayer.equals("AIR")) {
            String realLocationX = String.valueOf(xInteger);
            String realLocationY = String.valueOf(yInteger);
            String realLocationZ = String.valueOf(zInteger);

            player.sendMessage(ChatColor.LIGHT_PURPLE + "New block explored in location : (" + realLocationX + ", " + realLocationY + ", " + realLocationZ + ")");
            player.sendMessage(ChatColor.GREEN + "Block type: " + blockNameBelowPlayer);
            this.PastLocationList.add(integerLocation);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if(player.getHealth() <= e.getFinalDamage()){
                this.PastLocationList = new ArrayList<>();
                e.setCancelled(true);
                player.setHealth(20.0);
                player.teleport(new Location(player.getWorld(), 100, 63, 109));
                player.sendMessage(ChatColor.RED + "Player " + player.getName() + "is dead");
            }
        }
    }
}
