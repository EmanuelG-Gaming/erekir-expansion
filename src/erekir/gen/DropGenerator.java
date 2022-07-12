package erekir.gen;

import arc.Core;
import arc.struct.*;
import mindustry.type.*;
import erekir.world.blocks.environment.*;

import static mindustry.Vars.*;

/** Generates drop builds based on the current non-hidden items. */
public class DropGenerator{
   private static ItemProp last;
   private static Seq<ItemProp> generated = new Seq<ItemProp>();
   
   //funny generating code
   public static void generateDrops() {
      for (int i = 0; i < content.items().size; i++) {
         Item item = content.item(i);
         String itemName = item.localizedName;
         if (!item.isHidden()) {
            
            last = new ItemProp(item.name + "-drop"){{
               localizedName = itemName + " Drop";
               dropItem = item;
               amount = 1;
            }};
            generated.add(last);
         }
      }
   }
   
   public static void handleIcons() {
      generated.each(b -> {
         b.region = Core.atlas.find("item-" + b.dropItem.name);
      });
   }
}