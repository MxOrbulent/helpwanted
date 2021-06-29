package space.mxorbulent.fabricmods.helpwantedextended.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Config {
    public boolean WasUnableToCreateOrLoadConfigFile = false;


    public Map<String, Object> configmap;

    public Config(File configfile) {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);


        if (configfile.exists()) {
            try {

                this.configmap = mapper.readValue(configfile, Map.class);
                if ((boolean)this.configmap.get("DEBUGMOD") && !this.WasUnableToCreateOrLoadConfigFile) {
                    System.out.println("[HelpWantedExtended-D]: Loading the config file");
                }
            } catch (IOException e) {
                e.printStackTrace();
                this.WasUnableToCreateOrLoadConfigFile = true;
            }
        } else {
            //Since the config has never been created, we will create it ourself.
            try {
                this.configmap = new HashMap<>();
                FileUtils.touch(configfile);
                this.configmap.put("ALLOW_CRAFTING_VILLAGER_SIGN",true);
                this.configmap.put("ALLOW_CRAFTING_PIGLIN_SIGN",true);
                this.configmap.put("LIMIT_NUMBER_OF_REQRUITABLE_VILLAGERS_AROUND_SIGN",true);
                this.configmap.put("LIMIT_NUMBER_OF_VISITING_PIGLINS_AROUND_SIGN",true);
                this.configmap.put("SCALE_EXPENSIVITY",false);
                this.configmap.put("VILLAGER_LIMIT",4);
                this.configmap.put("PIGLIN_LIMIT",4);
                this.configmap.put("SEARCH_RADIUS_AROUND_SIGN",10); //Max is 100, anything larger is uneccesary.
                this.configmap.put("CHANCE_OF_ARRIVAL",150); //100 would be 50% chance, 101 would be 50.5% chance and so on.
                //because it runs 8 times, the boundary is 1600. 200 would be 10% chance. 100 would be 5% chance.
                //50 would be 2.5% chance.
                this.configmap.put("DIAMOND_EXTRA_VILLAGER_CHANCE",20); //10% chance of getting a extra villager.
                this.configmap.put("EMERALD_BLOCK","false");
                this.configmap.put("DEBUGCONFIG",true); //Prints the config values at the start.
                this.configmap.put("DEBUGMOD",true); //Prints to console whenever we roll some random or spawn a entity.
                //Piglins natural biome are crimson forests and nether wastes (they spawn at bastions too, but those
                //don't count as a biome. This option allows them to be attracted anywhere in the nether.
                //They still won't spawn in the overworld however.
                this.configmap.put("ALLOW_ATTRACTING_PIGLINS_OUTSIDE_OF_NATURAL_BIOME",false);
                //Here you can enter X and Z cordinates where you do not want the signs to have any effect.
                //For example: "BAN_ATTRACTING_CORDS1" : "100,-500:500,-2000:DIMENSIONNAME"
                //This would ban signs from working in a area of those cordinates.
                //A alternative way to write it is
                //"BAN_ATTRACTING_CORDS1" : "100,-500:200:DIMENSIONNAME"
                //This will ban everything centered on that block and 200 blocks out.
                //Like so:      0
                //              0
                //              2
                //           002C200
                //              2
                //              0
                //              0
                //4 slots are already prepared by default. 2 of them by default ban signs from working in a
                // 1000x1000 area centered around the 0,0 cordinates in both the overworld and the nether.
                //but you can manually add more up to 1000 in the config,
                // the mod will figure out how many are named correctly and
                //check the number from 1-1000. Anything above 1000 is ignored. Add a single X before
                //like this: "BAN_ATTRACTING_CORDS1" : "X100,-500:200:DIMENSIONNAME"
                //and it will be skipped.
                //This is usefull if you want to prevent players from spawning villagers to close to a attraction
                //a tutorial area, PVP arena or whatever.
                this.configmap.put("BAN_ATTRACTING_CORDS1","0,0:500:OVERWORLD");
                this.configmap.put("BAN_ATTRACTING_CORDS2","0,0:500:NETHER");
                this.configmap.put("BAN_ATTRACTING_CORDS3","X");
                this.configmap.put("BAN_ATTRACTING_CORDS4","X");

                mapper.writeValue(configfile,this.configmap);
                if ((boolean)this.configmap.get("DEBUGCONFIG") && !this.WasUnableToCreateOrLoadConfigFile) {
                    System.out.println("[HelpWantedExtended-D]: Printing location of the created config file.");
                    System.out.println(configfile.getAbsolutePath());
                }


            } catch (IOException e) {
                e.printStackTrace();
                this.WasUnableToCreateOrLoadConfigFile = true;
            }

        }



    }



}
