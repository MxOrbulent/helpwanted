package space.mxorbulent.fabricmods.helpwanted.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.BaseSettings;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
                //this.configmap.put("ALLOW_CRAFTING_SIGN",true); TODO implement
                this.configmap.put("LIMIT_NUMBER_OF_REQRUITABLE_VILLAGERS_AROUND_SIGN",true);
                this.configmap.put("LIMIT_NUMBER_OF_VISITING_PIGLINS_AROUND_SIGN",false); //TODO implement
                //this.configmap.put("SCALE_EXPENSIVITY",false); TODO implement
                this.configmap.put("VILLAGER_LIMIT",4);
                this.configmap.put("PIGLIN_LIMIT",4);
                this.configmap.put("SEARCH_RADIUS_AROUND_SIGN",32); //Max is 100, anything larger is uneccesary.
                this.configmap.put("CHANCE_OF_ARRIVAL",50); //100 would be 50% chance, 101 would be 50.5% chance and so on.
                //because it runs 8 times, the boundary is 1600. 200 would be 10% chance. 100 would be 5% chance.
                //50 would be 2.5% chance.
                this.configmap.put("DIAMOND_EXTRA_VILLAGER_CHANCE",20); //10% chance of getting a extra villager.
                this.configmap.put("EMERALD_BLOCK","false");
                this.configmap.put("DEBUGCONFIG",true); //Prints the config values at the start.
                this.configmap.put("DEBUGMOD",true); //Prints to console whenever we roll some random or spawn a entity.
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
