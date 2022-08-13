package erekir.room;

import arc.math.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.Vars;
import erekir.world.blocks.environment.*;
import erekir.gen.*;

/** The room where a core can be positioned into. Usually bigger than most rooms. */
public class MainStashRoom extends BaseRoom{
   public ItemProp[] drops = {
      get(0), get(1), get(3), get(4),
      get(5), get(6), get(7), get(DropGenerator.generated.size - 4),
      get(DropGenerator.generated.size - 3)
   };
   public float dropChance = 0.09f;
   
   public MainStashRoom(int px, int py, int w, int h) {
      super(px, py, w, h);
   }
   
   @Override
   public void generate() {
      super.generate();
      
      for (int dx = x - width; dx <= x + width; dx++) {
         for (int dy = y - height; dy <= y + height; dy++) {
            if (rand.chance(dropChance)) {
               Tile tile = Vars.world.tile(dx, dy);
               if (!tile.within(x, y, 10f)) tile.setBlock(drops[Mathf.floor(Mathf.randomSeed(tile.pos() + seed, 0f, drops.length))]);
            } 
         }
      }
   }
   
   public ItemProp get(int id) {
      return DropGenerator.generated.get(id);
   }
}