package space.mxorbulent.fabricmods.helpwantedextended;

import java.util.*;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;
import space.mxorbulent.fabricmods.helpwantedextended.config.Config;

public class VillagerPost extends BlockWithEntity {
   private static final String id = "villagerpost";
   public static final VillagerPost THIS_BLOCK;
   public static BlockEntityType VILLAGERPOST_ENTITY;
   public static Config configmanager;



   public static void init() {
      configmanager = HelpWantedMod.configManager;
      if((boolean) configmanager.configmap.get("DEBUGMOD") && !configmanager.WasUnableToCreateOrLoadConfigFile){
         System.out.println("[HelpWantedExtended-D]: About to register block helpwantedextended:villagerpost at line 46 VillagerPost.java");
      }
      Registry.register((Registry)Registry.BLOCK, new Identifier("helpwantedextended", "villagerpost"), (Object)VillagerPost.THIS_BLOCK);
      if((boolean) configmanager.configmap.get("DEBUGMOD") && !configmanager.WasUnableToCreateOrLoadConfigFile){
         System.out.println("[HelpWantedExtended-D]: About to register item helpwantedextended:villagerpost at line 50 VillagerPost.java");
      }
      Registry.register((Registry)Registry.ITEM, new Identifier("helpwantedextended", "villagerpost"), (Object)new BlockItem(VillagerPost.THIS_BLOCK, new Item.Settings().group(ItemGroup.MISC)));
      VillagerPost.VILLAGERPOST_ENTITY = FabricBlockEntityTypeBuilder.create(VillagerPostEntity::new, new Block[]
              {VillagerPost.THIS_BLOCK}).build(null);
      if((boolean) configmanager.configmap.get("DEBUGMOD") && !configmanager.WasUnableToCreateOrLoadConfigFile){
         System.out.println("[HelpWantedExtended-D]: About to register block entity type helpwantedextended:villagerpost 56 at line VillagerPost.java");
      }
      Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("helpwantedextended", "villagerpost"), VillagerPost.VILLAGERPOST_ENTITY);


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

      public Integer searchForEntitiesAroundSign (BlockPos pos, World worldtosearch, EntityType typeofentity) {

         double searchradius = (Integer) configmanager.configmap.get("SEARCH_RADIUS_AROUND_SIGN");
         if (searchradius > 100) {
            searchradius = 100;
            System.out.println("[HelpWantedExtended]: Max for SEARCH_RADIUS_AROUND_SIGN is 100, using 100 now.");
         } else if (searchradius < 4) {
            searchradius = 4;
            System.out.println("[HelpWantedExtended]: The lowest for SEARCH_RADIUS_AROUND_SIGN is 4, using 4 now.");
         }
         System.out.println("Formula is:" + searchradius);
         System.out.println("searchradius is: " + searchradius);
         int limit = 4; //DefaultLimit for both villagers and piglins.

         if (searchradius == 0) {return 0;}
         if (typeofentity.equals(EntityType.PIGLIN)) {limit = (Integer) configmanager.configmap.get("PIGLIN_LIMIT");
            if((boolean) configmanager.configmap.get("DEBUGMOD") && !configmanager.WasUnableToCreateOrLoadConfigFile){
               System.out.println("[HelpWantedExtended-D]: We will search for piglins within the bounding box whose" +
                       " origin is the sign.");

            }}
         else if (typeofentity.equals(EntityType.VILLAGER)) {limit = (Integer) configmanager.configmap.get("VILLAGER_LIMIT");
         if((boolean) configmanager.configmap.get("DEBUGMOD") && !configmanager.WasUnableToCreateOrLoadConfigFile){
            System.out.println("[HelpWantedExtended-D]: We will search for villagers within the bounding box whose" +
                    " origin is the sign.");
         }}
         if (limit < 2) {limit = 2;}

         Box boxtosearch = new Box(pos);


         boxtosearch =  boxtosearch.expand(searchradius);
         if((boolean) configmanager.configmap.get("DEBUGMOD") && !configmanager.WasUnableToCreateOrLoadConfigFile){
            System.out.println("[HelpWantedExtended-D]: The bounding box's first corner is X: "+boxtosearch.minX+" " +
                    "and Z: "+boxtosearch.minZ);
            System.out.println("[HelpWantedExtended-D]: The bounding box's second corner is X: "+boxtosearch.maxX+" " +
                    "and Z: "+boxtosearch.maxZ);


         }



         List listofentitiesfound = worldtosearch.getEntitiesByType(typeofentity, boxtosearch, (VillagerEntity) -> true);
         if (listofentitiesfound.size() >= limit) {
            if((boolean) configmanager.configmap.get("DEBUGMOD") && !configmanager.WasUnableToCreateOrLoadConfigFile){
               System.out.println("[HelpWantedExtended-D]: The limit of entities allowed has been reached or " +
                       "surpassed. We will not spawn another");
               System.out.println("[HelpWantedExtended-D]: Number of villagers found:" + listofentitiesfound.size());
            }

            return limit;}
         if((boolean) configmanager.configmap.get("DEBUGMOD") && !configmanager.WasUnableToCreateOrLoadConfigFile){
            System.out.println("[HelpWantedExtended-D]: The limit was not reached or surpassed, we will allow " +
                    "spawning!");
            System.out.println("[HelpWantedExtended-D]: Number of villagers found:" + listofentitiesfound.size());

         }
         return listofentitiesfound.size();
      }



      public void tick() {
         if (this.world != null && !this.world.isClient && this.world.getRegistryKey() != World.END) {


            if (this.delay++ > 20) {
               this.delay = 0;
               boolean daychange = !this.wasDay && this.world.isDay();

               this.wasDay = this.world.isDay();
               if (daychange) {
                  BlockSearcher blocksearchmanager = new BlockSearcher(this.pos, this.world,
                          (Integer) configmanager.configmap.get("DIAMOND_EXTRA_VILLAGER_CHANCE"),
                          (boolean) configmanager.configmap.get("DEBUGMOD") && !configmanager.WasUnableToCreateOrLoadConfigFile,configmanager);
                  Spawner spawnmanager = new Spawner(this.world, configmanager,blocksearchmanager);


                     Random rnd = new Random();
                     blocksearchmanager.scanBlocks();



                           Integer SkipSpawnDueToLimit = 0;
                           if ((Boolean) configmanager.configmap.get("LIMIT_NUMBER_OF_REQRUITABLE_VILLAGERS_AROUND_SIGN") && !configmanager.WasUnableToCreateOrLoadConfigFile) {

                              SkipSpawnDueToLimit = this.searchForEntitiesAroundSign(this.pos, this.world, EntityType.VILLAGER);
                           }
                           if (SkipSpawnDueToLimit >= (int)configmanager.configmap.get("VILLAGER_LIMIT")) {
                              return;
                           }

                           if (blocksearchmanager.isRollLuckInstead() && rnd.nextInt(200) <= (Integer) configmanager.configmap.get("CHANCE_OF_ARRIVAL")) {

                              spawnmanager.doSpawn(this.pos);
                     return;
                  }

                           spawnmanager.doSpawnRoutine(blocksearchmanager.number_of_emerald_blocks,
                                   blocksearchmanager.number_of_gold_blocks,
                                   blocksearchmanager.number_of_diamond_blocks, this.pos,SkipSpawnDueToLimit);


                  //This was some test code to see if everything works, and it does.
                              /*LightningEntity gonnagetroasted = new LightningEntity(EntityType.LIGHTNING_BOLT,this.world);
                              gonnagetroasted.setPosition(villager.world.getClosestPlayer(villager.getX(),villager.getY(),villager.getZ(),100,false).getPos());
                              this.world.spawnEntity(gonnagetroasted);
                              I'm leaving the code in incase someone wants to know how to make a thunderstrike at a player*/
                           //End of test code.

                        }
                     }

               }
            }

         }
      }

