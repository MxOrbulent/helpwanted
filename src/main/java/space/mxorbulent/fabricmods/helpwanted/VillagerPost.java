package space.mxorbulent.fabricmods.helpwanted;

import java.util.Random;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.event.listener.GameEventListener;
import com.mojang.datafixers.types.Type;
import org.jetbrains.annotations.Nullable;

public class VillagerPost extends BlockWithEntity {
   private static final String id = "villagerpost";
   public static final VillagerPost THIS_BLOCK;
   public static BlockEntityType VILLAGERPOST_ENTITY;

   public static void init() {
      Registry.register((Registry)Registry.BLOCK, new Identifier("helpwanted", "villagerpost"), (Object)VillagerPost.THIS_BLOCK);
      Registry.register((Registry)Registry.ITEM, new Identifier("helpwanted", "villagerpost"), (Object)new BlockItem(VillagerPost.THIS_BLOCK, new Item.Settings().group(ItemGroup.MISC)));
      VillagerPost.VILLAGERPOST_ENTITY = (BlockEntityType<VillagerPostEntity>) FabricBlockEntityTypeBuilder.create(VillagerPost.VillagerPostEntity::new, new Block[]
              {VillagerPost.THIS_BLOCK}).build((Type)null);
      Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("helpwanted", "villagerpost"), VillagerPost.VILLAGERPOST_ENTITY);
   }

   protected VillagerPost(Settings settings) {
      super(settings);
      this.setDefaultState(this.stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
   }

   protected void appendProperties(StateManager.Builder stateManager) {
      stateManager.add(new Property[]{Properties.HORIZONTAL_FACING});
   }

   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.MODEL;
   }

   public BlockEntity createBlockEntity(BlockPos arg0, BlockState arg1) {
      return new VillagerPost.VillagerPostEntity(VILLAGERPOST_ENTITY, arg0, arg1);
   }

   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return this.getDefaultState().with(Properties.HORIZONTAL_FACING, ctx.getPlayerFacing());
   }

   @Nullable
   public BlockEntityTicker getTicker(World param1, BlockState param2, BlockEntityType param3) {
      //return (BlockEntityTicker<BlockEntity>)checkType((BlockEntityType)param3, (BlockEntityType)VillagerPost.VILLAGERPOST_ENTITY, VillagerPost.VillagerPostEntity::ticker);
      return VillagerPost.checkType(param3, VILLAGERPOST_ENTITY, VillagerPostEntity::ticker);

   }

   @Nullable
   public GameEventListener getGameEventListener(World world, BlockEntity blockEntity) {
      return super.getGameEventListener(world, blockEntity);
   }

   static {
      THIS_BLOCK = new VillagerPost(Settings.of(Material.WOOD).strength(4.0F, 1.0F).nonOpaque().noCollision());
   }

   public static class VillagerPostEntity extends BlockEntity {
      int delay = 0;
      boolean wasDay = true;
      static final int[] NL = new int[]{-1, -1, -1, 0, -1, 1, 0, -1, 0, 1, 1, -1, 1, 0, 1, 1};

      public VillagerPostEntity(BlockPos pos, BlockState state) {
         super(VillagerPost.VILLAGERPOST_ENTITY, pos, state);
      }

      public VillagerPostEntity(BlockEntityType type, BlockPos pos, BlockState state) {
         super(type, pos, state);
      }

      public static void ticker(World world, BlockPos blockPos, BlockState blockState, VillagerPost.VillagerPostEntity sign) {
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
                     BlockPos bp = this.pos.add(NL[i * 2 + 0], 0, NL[i * 2 + 1]);
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
}
