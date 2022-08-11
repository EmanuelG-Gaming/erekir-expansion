package erekir.room;

import arc.util.*;
import arc.math.*;
import mindustry.content.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.Vars;

/** An empty room/platform. */
public class BaseRoom extends Room{
   public Floor groundFloor = Blocks.metalFloor.asFloor();
   public short id = -1; 
   
   @Nullable Rand rand;
   int seed;

   public BaseRoom(int px, int py, int w, int h) {
      super(px, py, w, h);
      id++;
   }
   
   public BaseRoom(int x, int y, int w, int h, Floor ground) {
      super(x, y, w, h);
      groundFloor = ground;
      id++;
   }
   
   public void generate() {
      seed = Vars.state.rules.sector.planet.id;
      rand = new Rand(seed + id + 5);
      
      for (int w = x - width; w <= x + width; w++) {
         for (int h = y - height; h <= y + height; h++) {
            Tile tile = Vars.world.tile(w, h);
            if (tile != null) tile.setFloor(groundFloor);
         }
      }
   }
}