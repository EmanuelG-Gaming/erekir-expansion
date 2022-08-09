package erekir.room;

import mindustry.content.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.game.*;
import mindustry.Vars;

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
      int dw = width / 2, dh = height / 2;

      //add a 3x3 vent
      for (int w = dw + x - 1; w <= dw + x + 1; w++) {
         for (int h = dh + y - 1; h <= dh + y + 1; h++) {
            Tile tile = Vars.world.tile(w, h);
            tile.setFloor(ventFloor);
            tile.setFloor(vent);
         }
      }
      
      Vars.world.tile(x + dw, y + dh).setBlock(Blocks.turbineCondenser, Team.sharded, 0);
   }
}