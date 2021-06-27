package space.mxorbulent.fabricmods.helpwanted;

import BlockEntityType;
import java.util.Random;
import net.minecraft.class_1299;
import net.minecraft.class_1646;
import net.minecraft.class_1750;
import net.minecraft.class_1937;
import net.minecraft.class_2237;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2464;
import net.minecraft.class_2586;
import net.minecraft.class_2591;
import net.minecraft.class_2680;
import net.minecraft.class_2741;
import net.minecraft.class_2769;
import net.minecraft.class_3614;
import net.minecraft.class_5558;
import net.minecraft.class_5714;
import net.minecraft.class_2689.class_2690;
import net.minecraft.class_4970.class_2251;
import org.jetbrains.annotations.Nullable;

public class VillagerPost extends BlockWithEntity {
   private static final String id = "villagerpost";
   public static final VillagerPost THIS_BLOCK;
   public static BlockEntityType VILLAGERPOST_ENTITY;

   public static void init() {
      class_2378.method_10230((class_2378)class_2378.field_11146, new class_2960("helpwanted", "villagerpost"), (Object)VillagerPost.THIS_BLOCK);
      class_2378.method_10230((class_2378)class_2378.field_11142, new class_2960("helpwanted", "villagerpost"), (Object)new class_1747((class_2248)VillagerPost.THIS_BLOCK, new class_1792.class_1793().method_7892(class_1761.field_7932)));
      VillagerPost.VILLAGERPOST_ENTITY = (class_2591<VillagerPost.VillagerPostEntity>)FabricBlockEntityTypeBuilder.create(VillagerPost.VillagerPostEntity::new, new class_2248[] { (class_2248)VillagerPost.THIS_BLOCK }).build((Type)null);
      class_2378.method_10230(class_2378.field_11137, new class_2960("helpwanted", "villagerpost"), (Object)VillagerPost.VILLAGERPOST_ENTITY);
   }

   protected VillagerPost(class_2251 settings) {
      super(settings);
      this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(class_2741.field_12481, class_2350.field_11043));
   }

   protected void method_9515(class_2690 stateManager) {
      stateManager.method_11667(new class_2769[]{class_2741.field_12481});
   }

   public class_2464 method_9604(class_2680 state) {
      return class_2464.field_11458;
   }

   public class_2586 method_10123(class_2338 arg0, class_2680 arg1) {
      return new VillagerPost.VillagerPostEntity(VILLAGERPOST_ENTITY, arg0, arg1);
   }

   public class_2680 method_9605(class_1750 ctx) {
      return (class_2680)this.method_9564().method_11657(class_2741.field_12481, ctx.method_8042());
   }

   @Nullable
   public class_5558 method_31645(class_1937 param1, class_2680 param2, class_2591 param3) {
      // $FF: Couldn't be decompiled
   }

   @Nullable
   public class_5714 method_32896(class_1937 world, class_2586 blockEntity) {
      return super.method_32896(world, blockEntity);
   }

   static {
      THIS_BLOCK = new VillagerPost(class_2251.method_9637(class_3614.field_15932).method_9629(4.0F, 1.0F).method_22488().method_9634());
   }

   public static class VillagerPostEntity extends class_2586 {
      int delay = 0;
      boolean wasDay = true;
      static final int[] NL = new int[]{-1, -1, -1, 0, -1, 1, 0, -1, 0, 1, 1, -1, 1, 0, 1, 1};

      public VillagerPostEntity(class_2338 pos, class_2680 state) {
         super(VillagerPost.VILLAGERPOST_ENTITY, pos, state);
      }

      public VillagerPostEntity(class_2591 type, class_2338 pos, class_2680 state) {
         super(type, pos, state);
      }

      public static void ticker(class_1937 world, class_2338 blockPos, class_2680 blockState, VillagerPost.VillagerPostEntity sign) {
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
}
