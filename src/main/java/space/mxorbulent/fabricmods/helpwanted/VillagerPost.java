package space.mxorbulent.fabricmods.helpwanted;

import java.io.File;
import java.util.*;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
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
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.event.listener.GameEventListener;
import com.mojang.datafixers.types.Type;
import org.jetbrains.annotations.Nullable;
import space.mxorbulent.fabricmods.helpwanted.config.Config;

public class VillagerPost extends BlockWithEntity {
   private static final String id = "villagerpost";
   public static final VillagerPost THIS_BLOCK;
   public static BlockEntityType VILLAGERPOST_ENTITY;
   public static Config configmanager;

   public static void init() {
      Registry.register((Registry)Registry.BLOCK, new Identifier("helpwanted", "villagerpost"), (Object)VillagerPost.THIS_BLOCK);
      Registry.register((Registry)Registry.ITEM, new Identifier("helpwanted", "villagerpost"), (Object)new BlockItem(VillagerPost.THIS_BLOCK, new Item.Settings().group(ItemGroup.MISC)));
      VillagerPost.VILLAGERPOST_ENTITY = (BlockEntityType<VillagerPostEntity>) FabricBlockEntityTypeBuilder.create(VillagerPost.VillagerPostEntity::new, new Block[]
              {VillagerPost.THIS_BLOCK}).build((Type)null);
      Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("helpwanted", "villagerpost"), VillagerPost.VILLAGERPOST_ENTITY);
      configmanager = new Config(new File("config/helpwantedextended/config.json"));
      if (configmanager.WasUnableToCreateOrLoadConfigFile == false) {
         if ((boolean)configmanager.configmap.get("DEBUGCONFIG") == true) {
            System.out.println("[HelpWantedExtended-D]: Printing the config values, you should see different values " +
                    "if you change stuff in the config after stopping and starting.");
            System.out.println("----------------------------------------");
            for (Map.Entry<String, Object> entry : configmanager.configmap.entrySet()) {
               System.out.println(entry.getKey() + ":" + entry.getValue().toString());
            }
            System.out.println("----------------------------------------");
            System.out.println("|                DONE                  |");
            System.out.println("----------------------------------------");
         }
      }
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

      public boolean searchForEntitiesAroundSign (BlockPos pos, World worldtosearch, EntityType typeofentity) {
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
         ArrayList<UUID> foundvillagers = new ArrayList<UUID>();
         if (searchradius == 0) {return false;}
         if (typeofentity.equals(EntityType.PIGLIN)) {limit = (Integer) configmanager.configmap.get("PIGLIN_LIMIT");
            if((boolean) configmanager.configmap.get("DEBUGMOD") == true && configmanager.WasUnableToCreateOrLoadConfigFile == false){
               System.out.println("[HelpWantedExtended-D]: We will search for piglins within the bounding box whose" +
                       " origin is the sign.");

            }}
         else if (typeofentity.equals(EntityType.VILLAGER)) {limit = (Integer) configmanager.configmap.get("VILLAGER_LIMIT");
         if((boolean) configmanager.configmap.get("DEBUGMOD") == true && configmanager.WasUnableToCreateOrLoadConfigFile == false){
            System.out.println("[HelpWantedExtended-D]: We will search for villagers within the bounding box whose" +
                    " origin is the sign.");
         }}
         if (limit < 2) {limit = 2;}

         Box boxtosearch = new Box(pos);


         boxtosearch =  boxtosearch.expand(searchradius*10);
         if((boolean) configmanager.configmap.get("DEBUGMOD") == true && configmanager.WasUnableToCreateOrLoadConfigFile == false){
            System.out.println("[HelpWantedExtended-D]: The bounding box's first corner is X: "+boxtosearch.minX+" " +
                    "and Z: "+boxtosearch.minZ);
            System.out.println("[HelpWantedExtended-D]: The bounding box's second corner is X: "+boxtosearch.maxX+" " +
                    "and Z: "+boxtosearch.maxZ);


         }



         List listofentitiesfound = worldtosearch.getEntitiesByType(typeofentity, boxtosearch, (VillagerEntity) -> true);
         if (listofentitiesfound.size() >= limit) {
            if((boolean) configmanager.configmap.get("DEBUGMOD") == true && configmanager.WasUnableToCreateOrLoadConfigFile == false){
               System.out.println("[HelpWantedExtended-D]: The limit of entities allowed has been reached or " +
                       "surpassed. We will not spawn another");
               System.out.println("[HelpWantedExtended-D]: Number of villagers found:" + listofentitiesfound.size());
            }

            return true;}
         if((boolean) configmanager.configmap.get("DEBUGMOD") == true && configmanager.WasUnableToCreateOrLoadConfigFile == false){
            System.out.println("[HelpWantedExtended-D]: The limit was not reached or surpassed, we will allow " +
                    "spawning!");
            System.out.println("[HelpWantedExtended-D]: Number of villagers found:" + listofentitiesfound.size());

         }
         return false;
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
                     //Nestling the chance to spawn within a for loop checking for blocks are a very bad solution
                     //in my opinion. TODO split blocklookup and chance to spawn and actual spawn mechanics.
                     //Basically "lucky" ran 8 times. meaning the actual chance was 8 out of 128. Or about 6,3% chance.


                     //I have no idea really what these mean, the
                     //Blockpos was named bp, which sounds like blockposition.
                     //Due to the this keyword, I'd say it's the blockposition of the sign.
                     BlockPos actualpositionofsign = this.pos;
                     BlockPos blockpositionofsign = this.pos.add(NL[i * 2 + 0], 0, NL[i * 2 + 1]);
                     BlockState bn = this.world.getBlockState(blockpositionofsign);
                     BlockState bu = this.world.getBlockState(blockpositionofsign.add(0, 1, 0));
                     Random rnd = new Random();
                     if (bu.getBlock() == Blocks.AIR) {
                        boolean lucky = false;
                        if (bn.getBlock() == Blocks.AIR) {
                           if ((Integer) configmanager.configmap.get("CHANCE_OF_ARRIVAL") < 0) {
                              configmanager.configmap.put("CHANCE_OF_ARRIVAL",0);
                           }
                           if ((Integer) configmanager.configmap.get("CHANCE_OF_ARRIVAL") > 0) {
                           if (rnd.nextInt(1600) <= (Integer) configmanager.configmap.get("CHANCE_OF_ARRIVAL")) {
                              lucky = true;

                           }


                           }
                        }

                        if (bn.getBlock() == Blocks.GOLD_BLOCK || bn.getBlock() == Blocks.EMERALD_BLOCK || bn.getBlock() == Blocks.DIAMOND_BLOCK || lucky ) {
                           if (!lucky && configmanager.configmap.get("EMERALD_BLOCK").toString().equals("false") && bn.getBlock() == Blocks.EMERALD_BLOCK) {
                              if((boolean) configmanager.configmap.get("DEBUGMOD") == true && configmanager.WasUnableToCreateOrLoadConfigFile == false){
                                 System.out.println("[HelpWantedExtended-D]: lucky was false and emerald blocks are " +
                                         "disabled as a block that villagers are attracted to, " +
                                         "so we won't spawn anything now.");

                              }

                              return;
                           }
                           boolean SkipSpawnDueToLimit = false;
                           if ((Boolean) configmanager.configmap.get("LIMIT_NUMBER_OF_REQRUITABLE_VILLAGERS_AROUND_SIGN") == true ||
                                   (Boolean) configmanager.configmap.get("LIMIT_NUMBER_OF_VISITING_PIGLINS_AROUND_SIGN") == true && configmanager.WasUnableToCreateOrLoadConfigFile == false)
                           {SkipSpawnDueToLimit = this.searchForEntitiesAroundSign(actualpositionofsign,this.world,EntityType.VILLAGER);}
                           if (SkipSpawnDueToLimit == true) {return;}
                           this.world.setBlockState(blockpositionofsign, Blocks.AIR.getDefaultState());
                           if (bn.getBlock() == Blocks.DIAMOND_BLOCK) {
                              if (rnd.nextInt(200) <= (Integer) configmanager.configmap.get("DIAMOND_EXTRA_VILLAGER_CHANCE")) {
                                 if((boolean) configmanager.configmap.get("DEBUGMOD") == true && configmanager.WasUnableToCreateOrLoadConfigFile == false){
                                    System.out.println("[HelpWantedExtended-D]: Successfull roll for spawning two " +
                                            "villagers due to a diamond block being nearby.");

                                 }


                                 VillagerEntity villager = EntityType.VILLAGER.create(this.world);
                                 villager.refreshPositionAndAngles(blockpositionofsign.getX(), blockpositionofsign.getY(), blockpositionofsign.getZ(), 0.0F, 0.0F);
                                 this.world.spawnEntity(villager);
                                 VillagerEntity villager2 = EntityType.VILLAGER.create(this.world);
                                 villager2.refreshPositionAndAngles(blockpositionofsign.getX(), blockpositionofsign.getY(), blockpositionofsign.getZ(), 0.0F, 0.0F);
                                 this.world.spawnEntity(villager);
                              }
                              return;
                           }
                           VillagerEntity villager = EntityType.VILLAGER.create(this.world);
                           villager.refreshPositionAndAngles(blockpositionofsign.getX(), blockpositionofsign.getY(), blockpositionofsign.getZ(), 0.0F, 0.0F);
                           this.world.spawnEntity(villager);
                           //This was some test code to see if everything works, and it does.
                           /*LightningEntity gonnagetroasted = new LightningEntity(EntityType.LIGHTNING_BOLT,this.world);
                           gonnagetroasted.setPosition(villager.world.getClosestPlayer(villager.getX(),villager.getY(),villager.getZ(),100,false).getPos());
                           this.world.spawnEntity(gonnagetroasted);
                           I'm leaving the code in incase someone wants to know how to make a thunderstrike at a player*/
                           //End of test code.
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
