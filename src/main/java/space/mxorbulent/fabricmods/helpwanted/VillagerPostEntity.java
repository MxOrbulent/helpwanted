package space.mxorbulent.fabricmods.helpwanted;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VillagerPostEntity extends BlockEntity {
   int delay = 0;
   boolean wasDay = true;
   static final int[] NL = new int[]{-1, -1, -1, 0, -1, 1, 0, -1, 0, 1, 1, -1, 1, 0, 1, 1};

   public VillagerPostEntity(BlockPos pos, BlockState state) {
      super(VillagerPost.VILLAGERPOST_ENTITY, pos, state);
   }

   public VillagerPostEntity(BlockEntityType type, BlockPos pos, BlockState state) {
      super(type, pos, state);
   }

   public static void ticker(World world, BlockPos blockPos, BlockState blockState, VillagerPostEntity sign) {
      sign.tick();
   }

   public void tick() {
      if (!this.world.isClient) {
         if (this.delay++ > 20) {
            this.delay = 0;
            boolean daychange = false;
            if (!this.wasDay && this.world.isDay()) {
               daychange = true;
            }

            this.wasDay = this.world.isDay();
            if (daychange) {
               for(int i = 0; i < 8; ++i) {
                  BlockPos bp = this.pos.add(NL[i * 2], 0, NL[i * 2 + 1]);
                  BlockState bn = this.world.getBlockState(bp);
                  BlockState bu = this.world.getBlockState(bp.add(0, 1, 0));
                  if (bu.getBlock() == Blocks.AIR) {
                     boolean lucky = false;
                     if (bn.getBlock() == Blocks.AIR) {
                        Random rnd = new Random();
                        if (rnd.nextInt(128) == 0) {
                           lucky = true;
                        }
                     }

                     if (lucky || bn.getBlock() == Blocks.GOLD_BLOCK || bn.getBlock() == Blocks.EMERALD_BLOCK || bn.getBlock() == Blocks.DIAMOND_BLOCK) {
                        this.world.setBlockState(bp, Blocks.AIR.getDefaultState());
                        VillagerEntity villager = EntityType.VILLAGER.create(this.world);
                        villager.refreshPositionAndAngles(bp.getX(), bp.getY(), bp.getZ(), 0.0F, 0.0F);
                        this.world.spawnEntity(villager);
                        return;
                     }
                  }
               }
            }
         }

      }
   }
}

