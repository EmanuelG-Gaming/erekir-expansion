package erekir.world.blocks.production;

import arc.math.*;
import arc.math.geom.*;
import arc.graphics.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.draw.*;
import mindustry.world.blocks.production.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.entities.*;
import erekir.util.*;
import erekir.content.*;
import erekir.world.blocks.environment.*;
import erekir.world.blocks.environment.ItemProp.*;
import erekir.world.blocks.gather.*;

import static mindustry.Vars.*;

public class DirectionalGatherer extends GenericCrafter{
   /** Length, in tiles. */
   public int length = 10;
   
   public Effect laserEffect = ErkFx.hitSquaresColorSmall;
   public Color laserColor = Color.valueOf("ea8878");
   
   public DirectionalGatherer(String name) {
      super(name);
      
      drawer = new DrawMulti(new DrawDefault(), new DrawSideRegion());
      rotateDraw = false;
      rotate = true;
      canOverdrive = false;
      drawArrow = true;
      hasItems = true;
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
          Point2 dir = Geometry.d4(rotation);
          float dx = Geometry.d4x(rotation), dy = Geometry.d4y(rotation);
          
          for (int i = 0; i < size; i++) {
             Point2 p = sides[i];
             float px = (p.x - dir.x / 2f) * tilesize, py = (p.y - dir.y / 2f) * tilesize;
 
             Drawf.dashLine(
                Pal.placing,
                px,
                py,
                px + dx * len * tilesize,
                py + dy * len * tilesize
            );
         }
      }
      
      @Override
      public void updateTile() {
         super.updateTile();
         
         if (sides[0] == null) updateSides();
         
         if (efficiency > 0) {
            gather();
         }
         
         dump();
      }
      
      @Override
      public void gather() {
         int len = length;
         int rot = rotation;
         Point2 dir = Geometry.d4(rot);
         float dx = Geometry.d4x(rot), dy = Geometry.d4y(rot);
          
         for (int l = 0; l < size; l++) {
            Point2 p = sides[l];
            float px = (p.x - dir.x / 2f) * tilesize, py = (p.y - dir.y / 2f) * tilesize;
 
            world.raycastEachWorld(px, py, px + dx * len * tilesize, py + dy * len * tilesize, (cx, cy) -> {
                Tile tile = world.tile(cx, cy);
                if (tile != null) {
                   if (tile.build != null && tile.build instanceof DropBuild) {
                      DropBuild drop = (DropBuild) tile.build;
                      if (ErkUtil.hasButton(drop)) {
                         drop.gather(this, 1);
                         if (itemCapacity - this.items.get(((ItemProp) drop.block).dropItem) >= 1) {
      
                            laserEffect.at(drop.tileX() * tilesize, drop.tileY() * tilesize, rot * 90f, laserColor);
                            return true;
                         }
                      }
                   }
                }
                return false;
            });
         }
      }
      
      @Override
      public void onProximityUpdate() {
         updateSides();
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