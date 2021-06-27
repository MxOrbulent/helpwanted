package space.mxorbulent.fabricmods.helpwanted;

import net.fabricmc.api.ModInitializer;

public class HelpWantedMod implements ModInitializer {
   public static final String MOD_ID = "helpwanted";

   public void onInitialize() {
      VillagerPost.init();
   }
}
