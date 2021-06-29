package space.mxorbulent.fabricmods.helpwantedextended;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import space.mxorbulent.fabricmods.helpwantedextended.config.Config;

import java.util.Iterator;

public class BlockSearcher {
    public int number_of_emerald_blocks = 0;
    public int number_of_gold_blocks = 0;
    public int number_of_diamond_blocks = 0;
    public int total_number_of_blocks = 0;
    public BlockPos positionofsign;
    public World worldtosearch;
    public int diamond_extra_villager_chance;
    public boolean spawnExtraVillager = false;
    boolean debugmod;
    boolean rollLuckInstead = false;
    Config config;
    Box box;

    public BlockSearcher(BlockPos pos, World world, int diamond_extra_chance, boolean debugmod, Config config) {
        this.positionofsign = new BlockPos(pos);
        this.worldtosearch = world;
        this.diamond_extra_villager_chance = diamond_extra_chance;
        this.debugmod = debugmod;
        this.config = config;
    }


    public int getNumber_of_emerald_blocks() {
        return number_of_emerald_blocks;
    }

    public void setNumber_of_emerald_blocks(int number_of_emerald_blocks) {
        this.number_of_emerald_blocks = number_of_emerald_blocks;
    }

    public int getNumber_of_gold_blocks() {
        return number_of_gold_blocks;
    }

    public void setNumber_of_gold_blocks(int number_of_gold_blocks) {
        this.number_of_gold_blocks = number_of_gold_blocks;
    }

    public int getNumber_of_diamond_blocks() {
        return number_of_diamond_blocks;
    }

    public void setNumber_of_diamond_blocks(int number_of_diamond_blocks) {
        this.number_of_diamond_blocks = number_of_diamond_blocks;
    }

    public boolean isSpawnExtraVillager() {
        return spawnExtraVillager;
    }

    public void setSpawnExtraVillager(boolean spawnExtraVillager) {
        this.spawnExtraVillager = spawnExtraVillager;
    }


    public void scanBlocks() {

      //box = new Box(this.positionofsign);
        int signPosX = this.positionofsign.getX();
        int signPosY = this.positionofsign.getY();
        int signPosZ = this.positionofsign.getZ();

        //box = box.expand(1,0,1);
        //Make us a copy of box that we can use later in the spawner to clear up blocks afterwards.
        int x1 = signPosX - 1;
        int z1 = signPosZ - 1;
        int x2 = signPosX + 1;
        int z2 = signPosZ + 1;
        Iterable<BlockPos> blockPosIterable = BlockPos.iterate(x1, signPosY, z1, x2, signPosY + 2, z2);
        for (BlockPos currentPositionIteration : blockPosIterable) {
            BlockState state = worldtosearch.getBlockState(currentPositionIteration);
            if (state.getBlock() == Blocks.GOLD_BLOCK) {
                System.out.println("FOUND THE GOLD!!!!");
            }

            if (!(state.getBlock() == worldtosearch.getBlockState(positionofsign).getBlock())) {


                if (state.getBlock() == Blocks.EMERALD_BLOCK) {
                    if (config.configmap.get("EMERALD_BLOCK").toString().equals("true")) {
                        this.number_of_emerald_blocks += 1;
                        this.total_number_of_blocks += 1;
                    }

                } else if (state.getBlock() == Blocks.GOLD_BLOCK) {
                    this.number_of_gold_blocks += 1;
                    this.total_number_of_blocks += 1;
                } else if (state.getBlock() == Blocks.DIAMOND_BLOCK) {
                    this.number_of_diamond_blocks += 1;
                    this.total_number_of_blocks += 1;
                } else if (state.getBlock() == Blocks.AIR && this.debugmod) {
                    System.out.println("[HelpWantedExtended-D]: Block cords: X:" + currentPositionIteration.getX() + "Y:" +
                            currentPositionIteration.getY() + "Z:" + currentPositionIteration.getZ() + ",State:" +
                            state.getBlock().toString());
                }
            } else {
                if (this.debugmod) {

                    System.out.println("[HelpWantedExtended-D]: We have no need of checking the sign location itself," +
                            " skipping.");
                    System.out.println("[HelpWantedExtended-D]: Signblock was! Block cords: X:" + currentPositionIteration.getX() + "Y:" +
                            currentPositionIteration.getY() + "Z:" + currentPositionIteration.getZ() + ",State:" +
                            state.getBlock().toString());
                    System.out.println("[HelpWantedExtended-D]: signPosX is:" + signPosX + " signPosY is:" + signPosZ);
                }
            }
        }
        if (this.total_number_of_blocks == 0) {
            this.setRollLuckInstead(true);
        }
    }

    public boolean removeBoxes() {
        int counter = 0;
       // box = new Box(this.positionofsign);
        int signPosX = this.positionofsign.getX();
        int signPosY = this.positionofsign.getY();
        int signPosZ = this.positionofsign.getZ();
        //box = box.expand(1,0,1);
        //Make us a copy of box that we can use later in the spawner to clear up blocks afterwards.
        int x1 = signPosX - 1;
        int z1 = signPosZ - 1;
        int x2 = signPosX + 1;
        int z2 = signPosZ + 1;
        Iterable<BlockPos> blockPosIterable = BlockPos.iterate(x1, signPosY, z1, x2, signPosY + 2, z2);
        Iterator<BlockPos> blockPosIterator = blockPosIterable.iterator();
        while (blockPosIterator.hasNext()) {
            BlockPos currentPositionIteration = blockPosIterator.next();
            int x = currentPositionIteration.getX();
            int z = currentPositionIteration.getZ();
            BlockState state = worldtosearch.getBlockState(currentPositionIteration);
            if (!(state.getBlock() == worldtosearch.getBlockState(positionofsign).getBlock())) {



                if (state.getBlock() == Blocks.DIAMOND_BLOCK) {
                    worldtosearch.setBlockState(currentPositionIteration,Blocks.AIR.getDefaultState());
                    if((boolean) config.configmap.get("DEBUGMOD") && !config.WasUnableToCreateOrLoadConfigFile){
                        System.out.println("[HelpWantedExtended-D]: Deleted block! Block cords: X:"+currentPositionIteration.getX()+"Y:"+
                                currentPositionIteration.getY()+"Z:"+currentPositionIteration.getZ()+",State:"+
                                state.getBlock().toString());


                    }
                    return true;

                } else if (state.getBlock() == Blocks.GOLD_BLOCK) {
                    worldtosearch.setBlockState(currentPositionIteration,Blocks.AIR.getDefaultState());
                    if((boolean) config.configmap.get("DEBUGMOD") && !config.WasUnableToCreateOrLoadConfigFile){
                        System.out.println("[HelpWantedExtended-D]: Deleted block! Block cords: X:"+currentPositionIteration.getX()+"Y:"+
                                currentPositionIteration.getY()+"Z:"+currentPositionIteration.getZ()+",State:"+
                                state.getBlock().toString());


                    }
                    return true;
                } else if (state.getBlock() == Blocks.EMERALD_BLOCK) {

                    if (config.configmap.get("EMERALD_BLOCK").toString().equals("true")) {
                        worldtosearch.setBlockState(currentPositionIteration,Blocks.AIR.getDefaultState());
                        if((boolean) config.configmap.get("DEBUGMOD") && !config.WasUnableToCreateOrLoadConfigFile){
                            System.out.println("[HelpWantedExtended-D]: Deleted block! Block cords: X:"+currentPositionIteration.getX()+"Y:"+
                                    currentPositionIteration.getY()+"Z:"+currentPositionIteration.getZ()+",State:"+
                                    state.getBlock().toString());


                        }
                        return true;
                    }
                }
            } else {
                if (this.debugmod) {
                    System.out.println("[HelpWantedExtended-D]: We have no need of checking the sign location itself," +
                            " skipping.");
                    System.out.println("[HelpWantedExtended-D]: Signblock was! Block cords: X:"+currentPositionIteration.getX()+"Y:"+
                            currentPositionIteration.getY()+"Z:"+currentPositionIteration.getZ()+",State:"+
                            state.getBlock().toString());
                }
                return false;
            }
        }
    return false;
    }

    public Integer scanForEmeraldBlock() {


       // box = new Box(this.positionofsign);
        int signPosX = this.positionofsign.getX();
        int signPosY = this.positionofsign.getY();
        int signPosZ = this.positionofsign.getZ();
       // box = box.expand(1,0,1);
        //Make us a copy of box that we can use later in the spawner to clear up blocks afterwards.
        int x1 = signPosX - 1;
        int z1 = signPosZ - 1;
        int x2 = signPosX + 1;
        int z2 = signPosZ + 1;
        Iterable<BlockPos> blockPosIterable = BlockPos.iterate(x1, signPosY, z1, x2, signPosY + 2, z2);
        Iterator<BlockPos> blockPosIterator = blockPosIterable.iterator();
        while (blockPosIterator.hasNext()) {

            BlockPos currentPositionIteration = blockPosIterator.next();
            BlockState state = worldtosearch.getBlockState(currentPositionIteration);
            int x = currentPositionIteration.getX();
            int z = currentPositionIteration.getZ();
            if (!(state.getBlock() == worldtosearch.getBlockState(positionofsign).getBlock())) {



                if (state.getBlock() == Blocks.EMERALD_BLOCK) {
                    if (config.configmap.get("EMERALD_BLOCK").toString().equals("true")) {
                       return 1;
                    }

                }
            } else {
                if (this.debugmod) {
                    System.out.println("[HelpWantedExtended-D]: We have no need of checking the sign location itself," +
                            " skipping.");
                    System.out.println("[HelpWantedExtended-D]: Signblock was! Block cords: X:"+currentPositionIteration.getX()+"Y:"+
                            currentPositionIteration.getY()+"Z:"+currentPositionIteration.getZ()+",State:"+
                            state.getBlock().toString());
                }
            }
        }
        return 0;
    }

    public boolean isRollLuckInstead() {
        return rollLuckInstead;
    }

    public void setRollLuckInstead(boolean rollLuckInstead) {
        this.rollLuckInstead = rollLuckInstead;
    }
}
