package it.multicoredev.nms.v_1_14_R1;

import it.multicoredev.ccbs.api.NMS;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

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
public class NMSHandler implements NMS {

    private static Field breakSound;
    private static Field minecraftKey;

    static {
        try {
            breakSound = SoundEffectType.class.getDeclaredField("y");
            breakSound.setAccessible(true);

            minecraftKey = SoundEffect.class.getDeclaredField("a");
            minecraftKey.setAccessible(true);


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendBreakPacket(int animation, Block block) {
        ((CraftServer) Bukkit.getServer()).getHandle().sendPacketNearby(null, block.getX(), block.getY(), block.getZ(), 120,
                ((CraftWorld) block.getWorld()).getHandle().worldProvider.getDimensionManager(), new PacketPlayOutBlockBreakAnimation(getBlockEntityId(block), getBlockPosition(block), animation));
    }

    public void sendBreakBlock(Player player, Block block) {
        ((CraftPlayer) player).getHandle().playerInteractManager.breakBlock(getBlockPosition(block));
    }

    public void playBlockSound(Block block) {
        try {
            net.minecraft.server.v1_14_R1.Block nmsBlock = ((CraftBlock) block).getNMS().getBlock();
            SoundEffectType soundEffectType = nmsBlock.getStepSound(nmsBlock.getBlockData());
            SoundEffect soundEffect = (SoundEffect) breakSound.get(soundEffectType);
            MinecraftKey minecraftKey = (MinecraftKey) NMSHandler.minecraftKey.get(soundEffect);
            String soundName = minecraftKey.getKey();
            soundName = soundName.toUpperCase().replaceAll("\\.", "_");

            block.getWorld().playSound(block.getLocation(), Sound.valueOf(soundName), 1, 1);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private BlockPosition getBlockPosition(Block block){
        return new BlockPosition(block.getX(), block.getY(), block.getZ());
    }

    private int getBlockEntityId(Block block){
        return ((block.getX() & 0xFFF) << 20 | (block.getZ() & 0xFFF) << 8) | (block.getY() & 0xFF);
    }
}
