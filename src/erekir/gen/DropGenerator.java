package erekir.gen;

import arc.Core;
import arc.math.*;
import arc.struct.*;
import mindustry.type.*;
import erekir.world.blocks.environment.*;

import static mindustry.Vars.*;

/** Generates drop builds based on the current non-hidden items. */
public class DropGenerator{
   private static ItemProp last;

   /** Contains only drops with shown vanilla items! */
   public static Seq<ItemProp> generated = new Seq<ItemProp>();
   
   public static Rand rnd = new Rand();
   
   //funny generating code
   public static void generateDrops() {
      for (Item item : content.items()) {
         String itemName = item.localizedName;
         if (!item.isHidden()) {
            
            last = new ItemProp(item.name + "-drop"){{
               localizedName = itemName + " Drop";
               dropItem = item;
            }};
            generated.add(last);
         }
      }
      setToDrop();
   }

   public static void setToDrop() {
      generated.each(b -> {
         int d = b.dropItem.id;
         rnd.setSeed(d + 71);
         setAmount(b, rnd.random(1, 5));
      });
   }
   
   public static void handleIcons() {
      generated.each(b -> {
         b.fullIcon = b.dropItem.fullIcon;
         b.uiIcon = b.dropItem.fullIcon;
      });
   }
   
   private static void setAmount(ItemProp drop, int amount) {
      drop.amount = amount;
   }
}