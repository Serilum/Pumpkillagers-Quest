package com.natamus.pumpkillagersquest.events;

import com.natamus.collective.functions.BlockPosFunctions;
import com.natamus.collective.functions.ExplosionFunctions;
import com.natamus.pumpkillagersquest.pumpkillager.Conversations;
import com.natamus.pumpkillagersquest.pumpkillager.Manage;
import com.natamus.pumpkillagersquest.util.Data;
import com.natamus.pumpkillagersquest.util.QuestData;
import com.natamus.pumpkillagersquest.util.Reference;
import com.natamus.pumpkillagersquest.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class PkOtherEvents {
    public static void onTNTExplode(Level level, Entity sourceEntity, Explosion explosion) {
        if (level.isClientSide) {
            return;
        }

        if (!(sourceEntity instanceof PrimedTnt)) {
            return;
        }

        BlockPos pos = sourceEntity.blockPosition();
        if (Data.allPrisoners.get(level).size() > 0) {
            for (Villager prisoner : Data.allPrisoners.get(level)) {
                BlockPos prisonerPos = prisoner.blockPosition();
                if (BlockPosFunctions.withinDistance(pos, prisonerPos, 10)) {
                    ExplosionFunctions.clearExplosion(explosion);
                }
            }
        }

        if (!(level.getBlockState(pos.north()).getBlock() instanceof RedStoneWireBlock)) {
            return;
        }

        List<Integer> redstonePositionsLeft = new ArrayList<Integer>(QuestData.questOneRitualBlockPositions);

        int i = 0;
        for (BlockPos bpa : BlockPos.betweenClosed(pos.getX()-3, pos.getY(), pos.getZ()-3, pos.getX()+3, pos.getY(), pos.getZ()+3)) {
            if (level.getBlockState(bpa).getBlock() instanceof RedStoneWireBlock) {
                redstonePositionsLeft.remove(Integer.valueOf(i));
            }
            i+=1;
        }

        if (redstonePositionsLeft.size() == 0) {
            ExplosionFunctions.clearExplosion(explosion);

            Player player = null;
            for (Entity ea : level.getEntities(null, new AABB(pos.getX()-20, pos.getY()-20, pos.getZ()-20, pos.getX()+20, pos.getY()+20, pos.getZ()+20))) {
                if (ea instanceof Player) {
                    player = (Player)ea;
                    break;
                }
            }

            if (player == null) {
                return;
            }

            if (player.getTags().contains(Reference.MOD_ID + ".unleashed")) {
                if (!player.getTags().contains(Reference.MOD_ID + ".completedquest")) {
                    Conversations.addEmptyMessage(level, null, player, 0);
                    Conversations.addMessageWithoutPrefix(level, null, player, "You feel a blast of magic, but nothing happens. The ritual must have been completed already. Maybe a prisoner in the prisoner camp could help find the Pumpkillager.", ChatFormatting.GRAY, 10);
                }
                return;
            }

            if (!player.getTags().contains(Reference.MOD_ID + ".questbookgiven")) {
                Conversations.addMessageWithoutPrefix(level, null, player, "You feel a blast of magic, but nothing happens. You'll probably need to talk to the Pumpkillager first.", ChatFormatting.GRAY, 10);
                return;
            }

            Manage.spawnPumpkillager(level, player, pos, 1, 1);
        }
    }

    public static boolean onPistonMove(Level level, BlockPos blockPos, Direction direction, boolean isExtending) {
        if (level.isClientSide) {
            return true;
        }

        BlockPos pos = blockPos.relative(direction);
        for (Entity ea : level.getEntities(null, new AABB(pos.getX()-3, pos.getY()-3, pos.getZ()-3, pos.getX()+3, pos.getY()+3, pos.getZ()+3))) {
            if (Util.isPumpkillager(ea)) {
                return false;
            }
        }
        return true;
    }
}
