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
      for (int x = dw - 1; x <= dw + 1; x++) {
         for (int y = dh - 1; y <= dh + 1; y++) {
            Tile tile = Vars.world.tile(x, y);
            tile.setFloor(ventFloor);
            tile.setFloor(vent);
         }
      }
      
      Vars.world.tile(dw - 2, dh - 2).setBlock(Blocks.turbineCondenser, Team.sharded, 0);
   }
}