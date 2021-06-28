package space.mxorbulent.fabricmods.helpwanted;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import space.mxorbulent.fabricmods.helpwanted.config.Config;

import java.util.Random;

public class Spawner {
    public int numberOfEntitiesToSpawn = 0;
    World worldtospawnin;
    Config config;
    BlockSearcher blocksearcher;
    public Spawner (World world, Config config, BlockSearcher _blocksearcher) {
        this.worldtospawnin = world;
        this.config = config;
        this.blocksearcher = _blocksearcher;
    }

    public boolean doSpawnRoutine (int numberofemeraldblocks, int numberofgoldblocks, int numberofdiamondblocks, BlockPos sign, int numberOfFoundEntities) {
        int totalnumberofblocks = 0;
        int subtractTotalNumberOfBlocks = 0;
        int numberofdiamondblocks_remove = 0;
        int emeraldblocksroundeddown;
        Random rnd = new Random();
        System.out.println("doSpawnRoutine triggered!");
        System.out.println("numberofemeraldblocks ="+numberofemeraldblocks);
        System.out.println("numberofgoldblocks ="+numberofgoldblocks);
        System.out.println("numberofdiamondblocks ="+numberofdiamondblocks);
        System.out.println("totalnumberofblocks ="+totalnumberofblocks);
        System.out.println("numberOfFoundEntities ="+numberOfFoundEntities);

        /*if (numberOfFoundEntities > 2 && (boolean)config.configmap.get("SCALE_EXPENSIVITY")) {
            if (config.configmap.get("EMERALD_BLOCK").toString().equals("2x")) {
                for (int i = 0; i < numberofemeraldblocks; i++) {
                    subtractTotalNumberOfBlocks = -2;
                }
                subtractTotalNumberOfBlocks -= (numberOfFoundEntities * 2 - 1);
            } else {
                subtractTotalNumberOfBlocks -= (numberOfFoundEntities * 2 - 1);
            }
            totalnumberofblocks = totalnumberofblocks - subtractTotalNumberOfBlocks;
        }

        for (int i = 0; i < totalnumberofblocks; i++) {
            numberOfEntitiesToSpawn += 1;
            if (numberOfEntitiesToSpawn >= (int)config.configmap.get("VILLAGER_LIMIT")) {
                numberOfEntitiesToSpawn = (int)config.configmap.get("VILLAGER_LIMIT");

                break;
            }
        }
        if (numberofdiamondblocks > 1 && (Integer)config.configmap.get("DIAMOND_EXTRA_VILLAGER_CHANCE") > 0) {
            for (int i = 0; i < numberofdiamondblocks; i++) {
                if (rnd.nextInt(200) <= (Integer)config.configmap.get("DIAMOND_EXTRA_VILLAGER_CHANCE")) {
                    numberOfEntitiesToSpawn += 1;

                }
            }
            if (numberOfEntitiesToSpawn >= (int)config.configmap.get("VILLAGER_LIMIT")) {
                numberOfEntitiesToSpawn = (int)config.configmap.get("VILLAGER_LIMIT");


            }
        }
        if((boolean) config.configmap.get("DEBUGMOD") && !config.WasUnableToCreateOrLoadConfigFile){
            System.out.println("[HelpWantedExtended-D]: numberOfEntitiesToSpawn is: !" + numberOfEntitiesToSpawn);


        }*/
        //numberofemeraldblocks + numberofgoldblocks + numberofdiamondblocks
        int derp = (int) Math.floor((numberOfFoundEntities / 2) + (numberOfFoundEntities - 2));
        if ((boolean)config.configmap.get("SCALE_EXPENSIVITY") && numberOfFoundEntities > 2) {
            if (config.configmap.get("EMERALD_BLOCK").toString().equals("2x")) {
                emeraldblocksroundeddown = (int) Math.floor(numberofemeraldblocks / 2);


                numberOfEntitiesToSpawn = totalnumberofblocks - emeraldblocksroundeddown - derp;
                if (numberofdiamondblocks > 1 && (Integer) config.configmap.get("DIAMOND_EXTRA_VILLAGER_CHANCE") > 0) {
                    for (int i = 0; i < numberofdiamondblocks; i++) {
                        if (rnd.nextInt(200) <= (Integer) config.configmap.get("DIAMOND_EXTRA_VILLAGER_CHANCE")) {
                            numberOfEntitiesToSpawn += 1;

                        }
                    }
                }
            } else if (config.configmap.get("EMERALD_BLOCK").toString().equals("true")) {
                //emeraldblocksroundeddown = (int) Math.floor(numberofemeraldblocks / 2);


                numberOfEntitiesToSpawn = totalnumberofblocks - derp;
                if (numberofdiamondblocks > 1 && (Integer) config.configmap.get("DIAMOND_EXTRA_VILLAGER_CHANCE") > 0) {
                    for (int i = 0; i < numberofdiamondblocks; i++) {
                        if (rnd.nextInt(200) <= (Integer) config.configmap.get("DIAMOND_EXTRA_VILLAGER_CHANCE")) {
                            numberOfEntitiesToSpawn += 1;

                        }
                    }
                }
            } else {
                numberOfEntitiesToSpawn = totalnumberofblocks - derp;
                if (numberofdiamondblocks > 1 && (Integer) config.configmap.get("DIAMOND_EXTRA_VILLAGER_CHANCE") > 0) {
                    for (int i = 0; i < numberofdiamondblocks; i++) {
                        if (rnd.nextInt(200) <= (Integer) config.configmap.get("DIAMOND_EXTRA_VILLAGER_CHANCE")) {
                            numberOfEntitiesToSpawn += 1;

                        }
                    }
                }
            }
        } else { //Beginning of not using SCALE_EXPENSIVITY
            if (config.configmap.get("EMERALD_BLOCK").toString().equals("2x")) {
                emeraldblocksroundeddown = (int) Math.floor(numberofemeraldblocks / 2);
                numberOfEntitiesToSpawn = emeraldblocksroundeddown + numberofgoldblocks + numberofdiamondblocks;
                if (numberofdiamondblocks > 1 && (Integer) config.configmap.get("DIAMOND_EXTRA_VILLAGER_CHANCE") > 0) {
                    for (int i = 0; i < numberofdiamondblocks; i++) {
                        if (rnd.nextInt(200) <= (Integer) config.configmap.get("DIAMOND_EXTRA_VILLAGER_CHANCE")) {
                            numberOfEntitiesToSpawn += 1;

                        }
                    }
                }
            } else if (config.configmap.get("EMERALD_BLOCK").toString().equals("true")) {
                numberOfEntitiesToSpawn = totalnumberofblocks;
                if (numberofdiamondblocks > 1 && (Integer) config.configmap.get("DIAMOND_EXTRA_VILLAGER_CHANCE") > 0) {
                    for (int i = 0; i < numberofdiamondblocks; i++) {
                        if (rnd.nextInt(200) <= (Integer) config.configmap.get("DIAMOND_EXTRA_VILLAGER_CHANCE")) {
                            numberOfEntitiesToSpawn += 1;

                        }
                    }
                }
            } else {
                numberOfEntitiesToSpawn = numberofgoldblocks + numberofdiamondblocks;
                if (numberofdiamondblocks > 1 && (Integer) config.configmap.get("DIAMOND_EXTRA_VILLAGER_CHANCE") > 0) {
                    for (int i = 0; i < numberofdiamondblocks; i++) {
                        if (rnd.nextInt(200) <= (Integer) config.configmap.get("DIAMOND_EXTRA_VILLAGER_CHANCE")) {
                            numberOfEntitiesToSpawn += 1;

                        }
                    }
                }
            }
        }

        if (numberOfEntitiesToSpawn <= 0) {return false;}
        if (numberOfEntitiesToSpawn > (int)config.configmap.get("VILLAGER_LIMIT")) {
            numberOfEntitiesToSpawn = (int)config.configmap.get("VILLAGER_LIMIT");
        }
        for (int i = 0; i < numberOfEntitiesToSpawn; i++) {


                    VillagerEntity villager = EntityType.VILLAGER.create(this.worldtospawnin);
                    assert villager != null;
                    villager.refreshPositionAndAngles(sign.getX(), sign.getY(), sign.getZ(), 0.0F, 0.0F);
                    this.worldtospawnin.spawnEntity(villager);
                    blocksearcher.removeBoxes();
            if((boolean) config.configmap.get("DEBUGMOD") && !config.WasUnableToCreateOrLoadConfigFile){
                System.out.println("[HelpWantedExtended-D]: Spawned Villager!");


            }



        }

        return false;
    }

    public boolean doSpawnRoutinePiglin (int numberofemeraldblocks, int numberofgoldblocks, int numberofdiamondblocks, BlockPos sign, int numberOfFoundEntities) {
        int subtractTotalNumberOfBlocks = 0;
        int numberofdiamondblocks_remove = 0;
        Random rnd = new Random();
        int derp = (int) Math.floor((numberOfFoundEntities / 2) + (numberOfFoundEntities - 2));
        if (numberOfFoundEntities > 2 && (boolean)config.configmap.get("SCALE_EXPENSIVITY")) {



            numberOfEntitiesToSpawn = numberofgoldblocks - derp;
        }

        if((boolean) config.configmap.get("DEBUGMOD") && !config.WasUnableToCreateOrLoadConfigFile){
            System.out.println("[HelpWantedExtended-D]: numberOfEntitiesToSpawn is: !" + numberOfEntitiesToSpawn);


        }
        if (numberOfEntitiesToSpawn <= 0) {return false;}
        if (numberOfEntitiesToSpawn > (int)config.configmap.get("PIGLIN_LIMIT")) {
            numberOfEntitiesToSpawn = (int)config.configmap.get("PIGLIN_LIMIT");
        }
        for (int i = 0; i < numberOfEntitiesToSpawn; i++) {


            PiglinEntity piglin = EntityType.PIGLIN.create(this.worldtospawnin);
            assert piglin != null;
            piglin.refreshPositionAndAngles(sign.getX(), sign.getY(), sign.getZ(), 0.0F, 0.0F);
            this.worldtospawnin.spawnEntity(piglin);
            blocksearcher.removeBoxes();




        }

        return false;
    }
    public void doSpawn(BlockPos sign) {

            VillagerEntity villager = EntityType.VILLAGER.create(this.worldtospawnin);
            assert villager != null;
            villager.refreshPositionAndAngles(sign.getX(), sign.getY(), sign.getZ(), 0.0F, 0.0F);
            this.worldtospawnin.spawnEntity(villager);

            /*PiglinEntity piglin = EntityType.PIGLIN.create(this.worldtospawnin);
            assert piglin != null;
            piglin.refreshPositionAndAngles(sign.getX(), sign.getY(), sign.getZ(), 0.0F, 0.0F);
            this.worldtospawnin.spawnEntity(piglin);*/

    }

    public void doSpawnPiglin(BlockPos sign) {

        PiglinEntity piglin = EntityType.PIGLIN.create(this.worldtospawnin);
        assert piglin != null;
        piglin.refreshPositionAndAngles(sign.getX(), sign.getY(), sign.getZ(), 0.0F, 0.0F);
        this.worldtospawnin.spawnEntity(piglin);

            /*PiglinEntity piglin = EntityType.PIGLIN.create(this.worldtospawnin);
            assert piglin != null;
            piglin.refreshPositionAndAngles(sign.getX(), sign.getY(), sign.getZ(), 0.0F, 0.0F);
            this.worldtospawnin.spawnEntity(piglin);*/

    }

}
