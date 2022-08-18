package erekir.room;

import arc.Core;
import arc.struct.*;
import arc.util.*;
import arc.math.*;
import mindustry.content.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.Vars;
import erekir.ErkVars;

/** An empty room/platform. */
public class BaseRoom extends Room{
   public Floor groundFloor = Blocks.metalFloor.asFloor();
   public Seq<BaseRoom> nodes = new Seq<>();
   
   public static final Seq<BaseRoom> all = new Seq<>();
   public final int id; 
   
   @Nullable Rand rand;
   int seed;

   public BaseRoom(int px, int py, int w, int h) {
      super(px, py, w, h);
      this.id = all.size;
      all.add(this);
   }
   
   public BaseRoom(int x, int y, int w, int h, Floor ground) {
      super(x, y, w, h);
      this.id = all.size;
      this.groundFloor = ground;
      all.add(this);
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
      ErkVars.rooms.add(this);
   }
   
   public BaseRoom addNode(BaseRoom room) {
      nodes.add(room);
      return room;
   }
   
   public String bundleName() {
      return "room.erekir-expansion-emptyRoom";
   }
   
   public String localized() {
      //filter out objects based on their class being this class
      Class<T> typez = (Class<T>) this.class;
      Seq<BaseRoom> select = all.select(r -> typez.isInstance(r));
      return Core.bundle.format(bundleName(), "#" + (select.size - 1));
   }
}