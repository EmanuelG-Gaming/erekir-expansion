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
      
      envEnabled |= Env.space;
   }
   
   @Override
   public void setStats() {
      super.setStats();
   }
   
   @Override 
   public void drawPlace(int x, int y, int rotation, boolean valid) {
      int sized = lanes;
      for (int i = 0; i < size; i++) {
        
         //stealing
         int px = x - (sized - 1) / 2, py = y - (sized - 1) / 2;
         switch (rotation) {
            case 0 -> Tmp.p1.set(px + sized, py + i);
            case 1 -> Tmp.p1.set(px + i, py + sized);
            case 2 -> Tmp.p1.set(px - 1, py + i);
            case 3 -> Tmp.p1.set(px + i, py - 1);
         }
         
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
         Drawf.dashLine(found ? Pal.placing : Pal.remove,
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