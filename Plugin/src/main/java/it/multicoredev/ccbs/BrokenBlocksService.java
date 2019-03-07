package it.multicoredev.ccbs;

import it.multicoredev.ccbs.objects.BrokenBlock;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.HashMap;

/**
 * Copyright © 2019 by Michele Giacalone
 * This file is part of UltraPrison.
 * UltraPrison is under "The 3-Clause BSD License", you can find a copy <a href="https://opensource.org/licenses/BSD-3-Clause">here</a>.
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
public class BrokenBlocksService {

    private static HashMap<Location, BrokenBlock> brokenBlocks = new HashMap<>();

    public void createBrokenBlock(Block block){
        createBrokenBlock(block, -1);
    }

    public void createBrokenBlock(Block block, int time){
        if(isBrokenBlock(block.getLocation())) return;
        BrokenBlock brokenBlock;
        if(time == -1) brokenBlock = new BrokenBlock(block, 0);
        else brokenBlock = new BrokenBlock(block, time);
        brokenBlocks.put(block.getLocation(), brokenBlock);
    }

    public void removeBrokenBLock(Location location){
        brokenBlocks.remove(location);
    }

    public BrokenBlock getBrokenBlock(Location location){
        createBrokenBlock(location.getBlock());
        return brokenBlocks.get(location);
    }

    public boolean isBrokenBlock(Location location){
        return brokenBlocks.containsKey(location);
    }
}
