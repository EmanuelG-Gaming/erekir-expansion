package erekir.gen;

import arc.Core;
import arc.struct.*;
import mindustry.type.*;
import erekir.world.blocks.environment.*;

import static mindustry.Vars.*;

/** Generates drop builds based on the current non-hidden items. */
public class DropGenerator{
   private static ItemProp last;
   private static int[content.items().size] amounts = [
      4, 4, 3,
      5, 5, 3,
      1, 3, 4,
      3, 1, 1,
      2, 3, 2,
      4, 3, 2,
      1
   ];
   
   /** Contains only drops with shown vanilla items! */
   public static Seq<ItemProp> generated = new Seq<ItemProp>();
   
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
      setToDrop();
   }

   public static void setToDrop() {
      generated.each(b -> {
         setAmount(b, amounts[b.id]);
      });
   }
   
   public static void handleIcons() {
      generated.each(b -> {
         b.region = b.dropItem.fullIcon
      });
   }
   
   private static void setAmount(ItemProp drop, int amount) {
      drop.amount = amount;
   }
}