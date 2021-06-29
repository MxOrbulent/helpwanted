package space.mxorbulent.fabricmods.helpwantedextended.mixin;

import com.google.gson.JsonElement;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.mxorbulent.fabricmods.helpwantedextended.HelpWantedMod;

import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

    @Inject(method = "apply", at = @At("HEAD"))
    public void interceptApply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo info) {
       System.out.println("CHECK ME BOI!");
        if (HelpWantedMod.VILLAGER_POST_RECIPE != null) {
            System.out.println("!CHECK ME BOI!");
            map.put(new Identifier("helpwantedextended", "villagerpost"), HelpWantedMod.VILLAGER_POST_RECIPE);
        }

        if (HelpWantedMod.PIGLIN_POST_RECIPE != null) {
            if((boolean) HelpWantedMod.configManager.configmap.get("DEBUGMOD") && !HelpWantedMod.configManager.WasUnableToCreateOrLoadConfigFile){
                System.out.println("[HelpWantedExtended-D]: About to use map.put on line 31 RecipeManagerMixin.");
            }
            map.put(new Identifier("helpwantedextended", "piglinpost"), HelpWantedMod.PIGLIN_POST_RECIPE);
        }
    }

}
