package erekir.room;

import mindustry.content.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.game.*;
import mindustry.Vars;

/** A room with a vent in the middle. */
public class VentRoom extends BaseRoom{
   public Floor ventFloor = Blocks.rhyolite.asFloor();
   public Floor vent = Blocks.rhyoliteVent.asFloor();
   
   public VentRoom(int x, int y, int w, int h) {
      super(x, y, w, h);
   }
   
   public VentRoom(int x, int y, int w, int h, Floor vent, Floor ventFloor) {
      super(x, y, w, h);
      this.vent = vent;
      this.ventFloor = ventFloor;
   }
   
   @Override
   public void generate() {
      super.generate();
      int dx = x, dy = y;

      //add a 3x3 vent
      for (int w = dx - 1; w <= dx + 1; w++) {
         for (int h = dy - 1; h <= dy + 1; h++) {
            Tile tile = Vars.world.tile(w, h);
            if (tile != null) {
               tile.setFloor(ventFloor);
               tile.setFloor(vent);
            }
         }
      }
      
      Tile tile2 = Vars.world.tile(dx, dy);
      if (tile2 != null) {
         tile2.setBlock(Blocks.turbineCondenser, Team.sharded, 0);
      }
   }
   
   @Override
   public String bundleName() {
      return "room.erekir-expansion-ventRoom";
   }
}