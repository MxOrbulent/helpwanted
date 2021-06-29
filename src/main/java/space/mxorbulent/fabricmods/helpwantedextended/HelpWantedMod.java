package space.mxorbulent.fabricmods.helpwantedextended;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import space.mxorbulent.fabricmods.helpwantedextended.config.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class HelpWantedMod implements ModInitializer {
   public static final String MOD_ID = "helpwantedextended";
   public static JsonObject VILLAGER_POST_RECIPE = null;
   public static JsonObject PIGLIN_POST_RECIPE = null;
   public static Config configManager;
   public void onInitialize() {
      configManager = new Config(new File("config/helpwantedextended/config.json"));
      if (!configManager.WasUnableToCreateOrLoadConfigFile) {
         if ((boolean) configManager.configmap.get("DEBUGCONFIG")) {
            System.out.println("[HelpWantedExtended-D]: Printing the config values, you should see different values " +
                    "if you change stuff in the config after stopping and starting.");
            System.out.println("----------------------------------------");
            for (Map.Entry<String, Object> entry : configManager.configmap.entrySet()) {
               System.out.println(entry.getKey() + ":" + entry.getValue().toString());
            }
            System.out.println("----------------------------------------");
            System.out.println("|                DONE                  |");
            System.out.println("----------------------------------------");
         }
      }

      //Ty Fabric wiki!
      //Create Villager Post recipe. Recipes are always created but if recipe is turned to false, they are never entered into the actual mixin.
      if((boolean) configManager.configmap.get("DEBUGMOD") && !configManager.WasUnableToCreateOrLoadConfigFile){
         System.out.println("[HelpWantedExtended-D]: Creating VILLAGER_POST_RECIPE line 40.");
      }
      if ((boolean)configManager.configmap.get("ALLOW_CRAFTING_VILLAGER_SIGN")) {
         VILLAGER_POST_RECIPE = createShapedRecipeJson(
                 Lists.newArrayList(
                         '#',
                         'R',
                         'S'
                 ), //The keys we are using for the input items/tags.
                 Lists.newArrayList(new Identifier("minecraft:planks"), new Identifier("red_dye"), new Identifier("stick")), //The items/tags we are using as input.
                 Lists.newArrayList("tag", "item", "item"), //Whether the input we provided is a tag or an item.
                 Lists.newArrayList(
                         "###",
                         "#R#",
                         " S "
                 ), //The crafting pattern.
                 new Identifier("helpwantedextended:villagerpost") //The crafting output
         );
      }

      //Create Piglin Post recipe.
      if((boolean) configManager.configmap.get("DEBUGMOD") && !configManager.WasUnableToCreateOrLoadConfigFile){
         System.out.println("[HelpWantedExtended-D]: Creating PIGLIN_POST_RECIPE line 59.");
      }
      if ((boolean)configManager.configmap.get("ALLOW_CRAFTING_VILLAGER_SIGN")) {
         PIGLIN_POST_RECIPE = createShapedRecipeJson(
                 Lists.newArrayList(
                         '#',
                         'G',
                         'S'
                 ), //The keys we are using for the input items/tags.
                 Lists.newArrayList(new Identifier("crimson_stem"), new Identifier("gold_ingot"), new Identifier("stick")), //The items/tags we are using as input.
                 Lists.newArrayList("item", "item", "item"), //Whether the input we provided is a tag or an item.
                 Lists.newArrayList(
                         "###",
                         "#G#",
                         " S "
                 ), //The crafting pattern.
                 new Identifier("helpwantedextended:piglinpost") //The crafting output
         );
      }


      ObjectMapper mapppy = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

      System.out.println(this.VILLAGER_POST_RECIPE.toString());

      VillagerPost.init();
      PiglinPost.init();


   }


   public static JsonObject createShapedRecipeJson(ArrayList<Character> keys, ArrayList<Identifier> items, ArrayList<String> type, ArrayList<String> pattern, Identifier output) {
      //Creating a new json object, where we will store our recipe.
      JsonObject json = new JsonObject();
      //The "type" of the recipe we are creating. In this case, a shaped recipe.
      json.addProperty("type", "minecraft:crafting_shaped");
      //This creates:
      //"type": "minecraft:crafting_shaped"

      //We create a new Json Element, and add our crafting pattern to it.
      JsonArray jsonArray = new JsonArray();
      jsonArray.add(pattern.get(0));
      jsonArray.add(pattern.get(1));
      jsonArray.add(pattern.get(2));
      //Then we add the pattern to our json object.
      json.add("pattern", jsonArray);
      //This creates:
      //"pattern": [
      //  "###",
      //  " | ",
      //  " | "
      //]

      //Next we need to define what the keys in the pattern are. For this we need different JsonObjects per key definition, and one main JsonObject that will contain all of the defined keys.
      JsonObject individualKey; //Individual key
      JsonObject keyList = new JsonObject(); //The main key object, containing all the keys

      for (int i = 0; i < keys.size(); ++i) {
         individualKey = new JsonObject();
         individualKey.addProperty(type.get(i), items.get(i).toString()); //This will create a key in the form "type": "input", where type is either "item" or "tag", and input is our input item.
         keyList.add(keys.get(i) + "", individualKey); //Then we add this key to the main key object.
         //This will add:
         //"#": { "tag": "c:copper_ingots" }
         //and after that
         //"|": { "item": "minecraft:sticks" }
         //and so on.
      }

      json.add("key", keyList);
      //And so we get:
      //"key": {
      //  "#": {
      //    "tag": "c:copper_ingots"
      //  },
      //  "|": {
      //    "item": "minecraft:stick"
      //  }
      //},

      //Finally, we define our result object
      JsonObject result = new JsonObject();
      result.addProperty("item", output.toString());
      result.addProperty("count", 1);
      json.add("result", result);
      //This creates:
      //"result": {
      //  "item": "modid:copper_pickaxe",
      //  "count": 1
      //}

      return json;
   }
}
