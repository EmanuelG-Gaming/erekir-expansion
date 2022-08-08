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
      int dx = dw + x, dy = dh + y;
      
      //add a 3x3 vent
      for (int x = dx - 1; x <= dx + 1; x++) {
         for (int y = dy - 1; y <= dy + 1; y++) {
            Tile tile = Vars.world.tile(x, y);
            tile.setFloor(ventFloor);
            tile.setFloor(vent);
         }
      }
      
      Vars.world.tile(dx, dy).setBlock(Blocks.turbineCondenser, Team.sharded, 0);
   }
}