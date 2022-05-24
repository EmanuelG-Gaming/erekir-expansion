package erekir.world.blocks.defense;

import arc.math.geom.*;
import arc.util.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.content.Blocks;
import mindustry.world.*; 

import static mindustry.Vars.*;

/**Testing class*/
public class BlockUpkeeper extends Block{
   public int lanes = 1;
   public int range = 5;
   
   public BlockUpkeeper(String name) {
      super(name);
      update = true;
      rotate = true;
      solid = true;
      drawArrow = false;
      regionRotated1 = 1;
   }
   
   @Override
   public void setStats() {
      super.setStats();
   }
   
   @Override 
   public void drawPlace(int x, int y, int rotation, boolean valid) {
      for (int i = 0; i < lanes; i++) {
         nearbySide(x, y, rotation, i, Tmp.p1);
         
         int j = 0;
         boolean found = false;
         for (; j < range; j++) {
            int rx = Tmp.p1.x + Geometry.d4x(rotation) * j, ry = Tmp.p1.y + Geometry.d4y(rotation) * j;
            Tile other = world.tile(rx, ry);
            if (other != null && other.solid()) {
               if (other.block() == Blocks.copperWall) {
                  found = true;
               }
            }
         }
         int len = Math.min(j, range - 1);
         Drawf.dashLine(found ? Pal.spacing : Pal.remove,
            Tmp.p1.x * tilesize,
            Tmp.p1.y * tilesize,
            (Tmp.p1.x + Geometry.d4x(rotation) * len) * tilesize,
            (Tmp.p1.y + Geometry.d4y(rotation) * len) * tilesize
         );
      }
   }
   
   public class BlockUpkeeperBuild extends Building{
      
   }
}