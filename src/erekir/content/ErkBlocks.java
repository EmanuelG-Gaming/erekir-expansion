package erekir.content;

import mindustry.type.*;
import mindustry.content.*;
import mindustry.content.Items;
import mindustry.world.*;
import mindustry.world.meta.*;
import erekir.ctype.*;
import erekir.world.blocks.defense.BlockUpkeeper;

import static mindustry.type.ItemStack.with;

public class ErkBlocks implements AltContentList{
    public static Block
    
    //Defense
    berylUpkeeper
     
    ;
    
    @Override
    public void load() {
       
      berylUpkeeper = new BlockUpkeeper("berylUpkeeper"){{
          size = 2;
          lanes = 4;
          range = 12;
          requirements(Category.defense, BuildVisibility.berylliumOnly, with(Items.beryllium, 100, Items.graphite, 65, Items.tungsten, 40));
      }};
    }
}