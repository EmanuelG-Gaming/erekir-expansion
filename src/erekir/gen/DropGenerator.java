package erekir.gen;

import arc.struct.*;
import mindustry.type.*;
import erekir.world.blocks.environment.*;

import static mindustry.Vars.*;

/** Generates drop builds based on the current non-hidden items. */
public class DropGenerator{
   private final int[] values = new int[content.items().size]; 
   private static ItemProp last;
   private Seq<ItemProp> generated = new Seq<ItemProp>();
   
   //funny generating code
   public void generateDrops() {
      for (int i = 0; i < values.length; i++) {
         if (values[i] != 0) {
            Item item = content.item(i);
            if (item.isHidden()) return;
            
            String itemName = item.localizedName;
            last = new ItemProp(itemName + "Drop"){{
               localizedName = itemName + " Drop";
               dropItem = item;
               amount = 1;
            }};
            generated.add(last);
         }
      }
   }
   
   public void handleIcons() {
      generated.each(b -> {
         b.region = b.dropItem.fullIcon;
      });
   }
}