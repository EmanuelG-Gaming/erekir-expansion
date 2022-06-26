package erekir.world.blocks.production;

import arc.math.*;
import arc.math.geom.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.draw.*;
import mindustry.world.blocks.production.*;
import mindustry.graphics.*;
import erekir.util.*;
import erekir.world.blocks.environment.ItemProp.*;
import erekir.world.blocks.gather.*;

import static mindustry.Vars.*;

public class DirectionalGatherer extends GenericCrafter{
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
      
      @Override
      public void draw() {
          drawer.draw(this);
          int len = length - 1;
          float dx = Geometry.d4x(rotation), dy = Geometry.d4y(rotation);
          Drawf.dashLine(
             Pal.placing,
             dx * tilesize,
             dx * tilesize,
             (dx + dx * len) * tilesize,
             (dy + dy * len) * tilesize
         );
      }
      
      @Override
      public void gather() {
          if (progress >= 1f) {
             int len = length;
             world.raycastEachWorld(x, y, x + Geometry.d4x(rotation) * len, x + Geometry.d4y(rotation) * len, (cx, cy) -> {
                Building build = world.tile(cx * tilesize, cy * tilesize).build;
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
   }
}