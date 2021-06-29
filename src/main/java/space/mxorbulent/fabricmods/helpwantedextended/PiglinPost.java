package space.mxorbulent.fabricmods.helpwantedextended;

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

import java.util.List;
import java.util.Random;

public class PiglinPost extends BlockWithEntity {
   private static final String id = "piglinpost";
   public static final PiglinPost THIS_BLOCK;
   public static BlockEntityType PIGLINPOST_ENTITY;
   public static Config configmanager;

   public static void init() {
      //Piglin Post
      Registry.register((Registry)Registry.BLOCK, new Identifier("helpwantedextended", "piglinpost"), (Object)PiglinPost.THIS_BLOCK);
      Registry.register((Registry)Registry.ITEM, new Identifier("helpwantedextended", "piglinpost"), (Object)new BlockItem(PiglinPost.THIS_BLOCK, new Item.Settings().group(ItemGroup.MISC)));
      PiglinPost.PIGLINPOST_ENTITY = FabricBlockEntityTypeBuilder.create(PiglinPost.PiglinPostEntity::new, new Block[]
              {PiglinPost.THIS_BLOCK}).build(null);
      Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("helpwantedextended", "piglinpost"), PiglinPost.PIGLINPOST_ENTITY);
      //End piglin post

      configmanager = HelpWantedMod.configManager;
   }

   protected PiglinPost(Settings settings) {
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
      return new PiglinPost.PiglinPostEntity(PIGLINPOST_ENTITY, arg0, arg1);
   }

   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return this.getDefaultState().with(Properties.HORIZONTAL_FACING, ctx.getPlayerFacing());
   }

   @Nullable
   public BlockEntityTicker getTicker(World param1, BlockState param2, BlockEntityType param3) {
      //return (BlockEntityTicker<BlockEntity>)checkType((BlockEntityType)param3, (BlockEntityType)PiglinPost.PIGLINPOST_ENTITY, PiglinPost.PiglinPostEntity::ticker);
      return PiglinPost.checkType(param3, PIGLINPOST_ENTITY, PiglinPostEntity::ticker);

   }

   @Nullable
   public GameEventListener getGameEventListener(World world, BlockEntity blockEntity) {
      return super.getGameEventListener(world, blockEntity);
   }

   static {
      THIS_BLOCK = new PiglinPost(Settings.of(Material.WOOD).strength(4.0F, 1.0F).nonOpaque().noCollision());
   }

   public static class PiglinPostEntity extends BlockEntity {
      int delay = 0;
      boolean wasDay = true;
      static final int[] NL = new int[]{-1, -1, -1, 0, -1, 1, 0, -1, 0, 1, 1, -1, 1, 0, 1, 1};

      public PiglinPostEntity(BlockPos pos, BlockState state) {
         super(PiglinPost.PIGLINPOST_ENTITY, pos, state);
      }

      public PiglinPostEntity(BlockEntityType type, BlockPos pos, BlockState state) {
         super(type, pos, state);
      }

      public static void ticker(World world, BlockPos blockPos, BlockState blockState, PiglinPost.PiglinPostEntity sign) {
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
         System.out.println("Formula is:" + searchradius*10);
         System.out.println("searchradius is: " + searchradius);
         int limit = 4; //DefaultLimit for both villagers and piglins.

         if (searchradius == 0) {return 0;}
         if (typeofentity.equals(EntityType.PIGLIN)) {limit = (Integer) configmanager.configmap.get("PIGLIN_LIMIT");
            if((boolean) configmanager.configmap.get("DEBUGMOD") && !configmanager.WasUnableToCreateOrLoadConfigFile){
               System.out.println("[HelpWantedExtended-D]: We will search for piglins within the bounding box whose" +
                       " origin is the sign.");

            }}
         else if (typeofentity.equals(EntityType.PIGLIN)) {limit = (Integer) configmanager.configmap.get("PIGLIN_LIMIT");
         if((boolean) configmanager.configmap.get("DEBUGMOD") && !configmanager.WasUnableToCreateOrLoadConfigFile){
            System.out.println("[HelpWantedExtended-D]: We will search for villagers within the bounding box whose" +
                    " origin is the sign.");
         }}
         if (limit < 2) {limit = 2;}

         Box boxtosearch = new Box(pos);


         boxtosearch =  boxtosearch.expand(searchradius*10);
         if((boolean) configmanager.configmap.get("DEBUGMOD") && !configmanager.WasUnableToCreateOrLoadConfigFile){
            System.out.println("[HelpWantedExtended-D]: The bounding box's first corner is X: "+boxtosearch.minX+" " +
                    "and Z: "+boxtosearch.minZ);
            System.out.println("[HelpWantedExtended-D]: The bounding box's second corner is X: "+boxtosearch.maxX+" " +
                    "and Z: "+boxtosearch.maxZ);


         }



         List listofentitiesfound = worldtosearch.getEntitiesByType(typeofentity, boxtosearch, (PiglinEntity) -> true);
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
         if (this.world != null && !this.world.isClient && this.world.getRegistryKey() == World.NETHER && this.world.getRegistryKey() != World.END) {
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
                  if ((Boolean) configmanager.configmap.get("LIMIT_NUMBER_OF_REQRUITABLE_PIGLINS_AROUND_SIGN") && !configmanager.WasUnableToCreateOrLoadConfigFile) {

                     SkipSpawnDueToLimit = this.searchForEntitiesAroundSign(this.pos, this.world, EntityType.PIGLIN);
                  }
                  if (SkipSpawnDueToLimit >= (int)configmanager.configmap.get("PIGLIN_LIMIT")) {
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
                           //return;
                        }
                     }
                  //}
               }
            }

         }
      }

