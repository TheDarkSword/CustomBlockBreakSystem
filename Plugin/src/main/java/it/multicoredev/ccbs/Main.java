package it.multicoredev.ccbs;

import it.mineblock.mbcore.spigot.Chat;
import it.multicoredev.ccbs.api.NMS;
import it.multicoredev.ccbs.listeners.BreakListeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright © 2019 by Michele Giacalone
 * This file is part of CustomBlockBreakSystem.
 * CustomBlockBreakSystem is under "The 3-Clause BSD License", you can find a copy <a href="https://opensource.org/licenses/BSD-3-Clause">here</a>.
 * <p>
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
public class Main extends JavaPlugin {

    public static NMS nmsHandler;

    public static BrokenBlocksService brokenBlocksService = new BrokenBlocksService();

    @Override
    public void onEnable() {
        loadNMS();

        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {

    }

    private void loadNMS(){
        String packageName = getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf('.')+1);
        try{
            Class<?> clazz = Class.forName("it.multicoredev.nms." + version + ".NMSHandler");

            if(NMS.class.isAssignableFrom(clazz)){
                this.nmsHandler = (NMS) clazz.getConstructor().newInstance();
            }

        } catch (Exception e){
            e.printStackTrace();
            Chat.getLogger("Could not find support for this CraftBukkit version.", "severe");
            setEnabled(false);
            return;
        }

        Chat.getLogger("Loading support for " + version, "info");
    }

    private void registerCommands(){

    }

    private void registerListeners(){
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new BreakListeners(), this);
    }
}
