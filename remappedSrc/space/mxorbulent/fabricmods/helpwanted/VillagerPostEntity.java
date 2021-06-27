package space.mxorbulent.fabricmods.helpwanted;

import java.util.Random;
import net.minecraft.class_1299;
import net.minecraft.class_1646;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2591;
import net.minecraft.class_2680;

public class VillagerPostEntity extends class_2586 {
   int delay = 0;
   boolean wasDay = true;
   static final int[] NL = new int[]{-1, -1, -1, 0, -1, 1, 0, -1, 0, 1, 1, -1, 1, 0, 1, 1};

   public VillagerPost$VillagerPostEntity(class_2338 pos, class_2680 state) {
      super(VillagerPost.VILLAGERPOST_ENTITY, pos, state);
   }

   public VillagerPost$VillagerPostEntity(class_2591 type, class_2338 pos, class_2680 state) {
      super(type, pos, state);
   }

   public static void ticker(class_1937 world, class_2338 blockPos, class_2680 blockState, VillagerPost$VillagerPostEntity sign) {
      sign.tick();
   }

   public void tick() {
      if (!this.field_11863.field_9236) {
         if (this.delay++ > 20) {
            this.delay = 0;
            boolean daychange = false;
            if (!this.wasDay && this.field_11863.method_8530()) {
               daychange = true;
            }

            this.wasDay = this.field_11863.method_8530();
            if (daychange) {
               for(int i = 0; i < 8; ++i) {
                  class_2338 bp = this.field_11867.method_10069(NL[i * 2 + 0], 0, NL[i * 2 + 1]);
                  class_2680 bn = this.field_11863.method_8320(bp);
                  class_2680 bu = this.field_11863.method_8320(bp.method_10069(0, 1, 0));
                  if (bu.method_26204() == class_2246.field_10124) {
                     boolean lucky = false;
                     if (bn.method_26204() == class_2246.field_10124) {
                        Random rnd = new Random();
                        if (rnd.nextInt(128) == 0) {
                           lucky = true;
                        }
                     }

                     if (lucky || bn.method_26204() == class_2246.field_10205 || bn.method_26204() == class_2246.field_10234 || bn.method_26204() == class_2246.field_10201) {
                        this.field_11863.method_8501(bp, class_2246.field_10124.method_9564());
                        class_1646 v = (class_1646)class_1299.field_6077.method_5883(this.field_11863);
                        v.method_5808((double)bp.method_10263(), (double)bp.method_10264(), (double)bp.method_10260(), 0.0F, 0.0F);
                        this.field_11863.method_8649(v);
                        return;
                     }
                  }
               }
            }
         }

      }
   }
}

