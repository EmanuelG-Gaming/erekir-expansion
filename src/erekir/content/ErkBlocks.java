package erekir.content;

import mindustry.type.*;
import mindustry.content.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import erekir.ctype.*;
import erekir.world.blocks.environment.ItemProp;
import erekir.world.blocks.defense.BlockUpkeeper;

import static mindustry.type.ItemStack.with;

public class ErkBlocks implements AltContentList{
    public static Block
    
    //Environment
    berylDrop, tungDrop, graphiteDrop, thorDrop,
    
    //Defense
    berylUpkeeper
     
    ;
    
    @Override
    public void load() {
      berylDrop = new ItemProp("berylDrop"){{
          dropItem = Items.beryllium;
          amount = 4;
      }};
      
      tungDrop = new ItemProp("tungDrop"){{
          dropItem = Items.tungsten;
          amount = 2;
      }};
      
      graphiteDrop = new ItemProp("graphiteDrop"){{
          dropItem = Items.graphite;
          amount = 3;
      }};
      
      thorDrop = new ItemProp("thoriumDrop"){{
          dropItem = Items.thorium;
          amount = 1;
      }};
      
      berylUpkeeper = new BlockUpkeeper("berylUpkeeper"){{
          size = 2;
          lanes = 4;
          range = 12;
          requirements(Category.defense, BuildVisibility.berylliumOnly, with(Items.beryllium, 100, Items.graphite, 65, Items.tungsten, 40));
      }};
    }
}