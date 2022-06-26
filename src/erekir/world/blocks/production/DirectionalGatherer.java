package erekir.world.blocks.production;

import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.draw.*;
import mindustry.world.blocks.production.*;
import mindustry.graphics.*;
import mindustry.type.*;
import erekir.util.*;
import erekir.world.blocks.environment.ItemProp.*;
import erekir.world.blocks.gather.*;

import static mindustry.Vars.*;

public class DirectionalGatherer extends GenericCrafter{
   /** Length, in tiles. */
   public int length = 10;
   
   public DirectionalGatherer(String name) {
      super(name);
      
      drawer = new DrawMulti(new DrawDefault(), new DrawSideRegion());
      rotateDraw = false;
      rotate = true;
      canOverdrive = false;
      drawArrow = true;
   }
   
   @Override
   public void load() {
      super.load();

      drawer.load(this);
   }
    
   public class DirectionalGathererBuild extends GenericCrafterBuild implements Gathering{
      public Point2[] sides = new Point2[size];
      
      @Override
      public void draw() {
          drawer.draw(this);
      }
      
      @Override
      public void drawSelect() {
          super.drawSelect();
          int len = length - 1;
          float dx = Geometry.d4x(rotation), dy = Geometry.d4y(rotation);
          
          for (int i = 0; i < size; i++) {
             //stealing
             int px = (int) x - (size - 1) / 2, py = (int) y - (size - 1) / 2;
             switch (rotation) {
                case 0:
                   Tmp.p1.set(px + size, py + i);
                   break;
                   
                case 1:
                   Tmp.p1.set(px + i, py + size);
                   break;
                   
                case 2:
                   Tmp.p1.set(px - 1, py + i);
                   break;
                   
                case 3: 
                   Tmp.p1.set(px + i, py - 1);
                   break;
             }
             
             Drawf.dashLine(
                Pal.placing,
                Tmp.p1.x * tilesize,
                Tmp.p1.y * tilesize,
                (Tmp.p1.x + dx * len) * tilesize,
                (Tmp.p1.y + dy * len) * tilesize
            );
         }
      }
      
      @Override
      public void updateTile() {
         super.updateTile();
         
         if (sides[0] == null) updateSides();
         
         if (progress >= 1f) {
            gather();
         }
      }
      
      @Override
      public void gather() {
         int len = length;
         for (int l = 0; l < size; l++) {
            Point2 p = sides[l];
            float tx = p.x + Geometry.d4x(rotation) * tilesize,
                  ty = p.y + Geometry.d4y(rotation) * tilesize;
         
            world.raycastEachWorld(tx, ty, tx + len * tilesize, ty + len * tilesize, (cx, cy) -> {
                Building build = world.tile(cx, cy).build;
                if (build != null && build instanceof DropBuild) {
                   DropBuild drop = (DropBuild) build;
                   if (ErkUtil.hasButton(drop)) {
                      drop.gather(this, 1);
                      return true;
                   }
                }
                return false;
            });
         }
      }
      
      public void updateSides() {
         for (int i = 0; i < size; i++) {
            if (sides[i] == null) sides[i] = new Point2();
            nearbySide(tileX(), tileY(), rotation, i, sides[i]);
         }
      }
      
      @Override
      public int acceptStack(Item item, int amount, Teamc source) {
         return Math.min(itemCapacity - items.total(), amount);
      }
   }
}