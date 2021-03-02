package it.multicoredev.ccbs.listeners;

import com.cryptomorin.xseries.XMaterial;
import it.multicoredev.ccbs.Main;
import it.multicoredev.ccbs.utls.Utls;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

/**
 * Copyright © 2018 by Michele Giacalone
 * All rights reserved. No part of this code may be reproduced, distributed, or transmitted in any form or by any means,
 * including photocopying, recording, or other electronic or mechanical methods, without the prior written permission
 * of the creator.
 */
public class BreakListeners implements Listener {

    private static HashSet<Material> transparentBlocks = new HashSet<>();

    static {
        transparentBlocks.add(XMaterial.WATER.parseMaterial());
        transparentBlocks.add(XMaterial.AIR.parseMaterial());
    }


    @EventHandler
    public void onBlockBreak(BlockDamageEvent event){

        Main.brokenBlocksService.createBrokenBlock(event.getBlock(), 30);
    }

    @EventHandler
    public void onBreakingBlock(PlayerAnimationEvent event){
        Player player = event.getPlayer();

        Block block = player.getTargetBlock(transparentBlocks, 5);
        Location blockPosition = block.getLocation();

        if(!Main.brokenBlocksService.isBrokenBlock(blockPosition)) return;

        /*
        Use player#getItemInHand for backwards compatibility
         */
        ItemStack itemStack = player.getItemInHand();

        double distanceX = blockPosition.getX() - player.getLocation().getX();
        double distanceY = blockPosition.getY() - player.getLocation().getY();
        double distanceZ = blockPosition.getZ() - player.getLocation().getZ();

        if(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ >= 1024.0D) return;
        Utls.addSlowDig(event.getPlayer(), 200);
        Main.brokenBlocksService.getBrokenBlock(blockPosition).incrementDamage(player, 1);
    }
}
