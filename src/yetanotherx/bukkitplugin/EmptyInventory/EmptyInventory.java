package yetanotherx.bukkitplugin.EmptyInventory;

//Bukkit imports
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;

//Java imports
import java.util.logging.Logger;

//Permissions imports
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

/*
 * EmptyInventory Version 1.0 - Empty your inventory
 * Copyright (C) 2011 Yetanotherx <yetanotherx -a--t- gmail -dot- com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class EmptyInventory extends JavaPlugin {

    /**
     * Logger magic
     */
    public static final Logger log = Logger.getLogger("Minecraft");

    /**
     * Permission plugin
     */
    public static PermissionHandler Permissions = null;

    /**
     * Outputs a message when disabled
     */
    public void onDisable() {
	log.info( "[" + this.getDescription().getName() + "]" + " Plugin disabled. (version" + this.getDescription().getVersion() + ")");
    }

    /**
     * Enables the plugin
     */
    public void onEnable() {

	setupPermissions();

	log.info( "[" + this.getDescription().getName() + "]" + " Plugin enabled! (version" + this.getDescription().getVersion() + ")");
    }

    /**
     * 
     * Checks that Permissions is installed.
     * 
     */
    public void setupPermissions() {

	Plugin perm_plugin = this.getServer().getPluginManager().getPlugin("Permissions");
	PluginDescriptionFile pdfFile = this.getDescription();

	if( Permissions == null ) {
	    if( perm_plugin != null ) {
		//Permissions found, enable it now
		this.getServer().getPluginManager().enablePlugin( perm_plugin );
		Permissions = ( (Permissions) perm_plugin ).getHandler();
	    }
	    else {
		//Permissions not found. Disable plugin
		log.info(pdfFile.getName() + " version " + pdfFile.getVersion() + "not enabled. Permissions not detected");
		this.getServer().getPluginManager().disablePlugin(this);
	    }
	}
    }

    /**
     * Called when a user performs a command
     */
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

	String[] split = args;
	String commandName = command.getName().toLowerCase();

	if(sender instanceof Player) {
	    Player player = (Player) sender;

	    if( commandName.equals("empty") ) {

		if( Permissions.has(player, "emptyinventory.use") ) {
		    
		    if (split.length == 1 && split[0].equalsIgnoreCase("-all") ) {

			player.getInventory().clear();
			player.updateInventory();

			player.sendMessage( ChatColor.WHITE + "Your inventory and hotbar are now cleared.");

		    }
		    else {
			
			for( int i = 9; i <= 35; i++ ) {
			    player.getInventory().clear(i);
			}
			player.updateInventory();
			
			player.sendMessage( ChatColor.WHITE + "Your inventory is now cleared.");

		    }
		}
		else {
		    return true;
		}

		return true;
	    }
	}
	return false;
    }


}
