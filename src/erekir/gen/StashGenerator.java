package erekir.gen;

import arc.struct.*;
import arc.func.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.graphics.g3d.*;
import mindustry.maps.generators.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import erekir.world.blocks.environment.*;
import erekir.util.*;
import erekir.room.*;

import static mindustry.Vars.*;

public class StashGenerator extends BlankPlanetGenerator{
    public ItemProp[] drops = {
       get(0), get(1), get(4),
       get(5), get(6), get(7),
       get(DropGenerator.generated.size - 4), get(DropGenerator.generated.size - 3)
    };
    
    public Seq<BaseRoom> rooms = Seq.with(
       new BaseRoom(25, 20, 5, 5),
       new BaseRoom(135, 50, 5, 5),
       new VentRoom(80, 130, 8, 8)
    );
    
    public int pw = 30, ph = 30;
    public Planet orbiting = Planets.erekir;
    
    @Nullable Rand rand;
    int seed;
   
    ItemProp get(int id) {
        return DropGenerator.generated.get(id);
    }

    @Override
    public void generate() {
        seed = state.rules.sector.planet.id;
        int dx = width / 2, dy = height / 2;
        rand = new Rand(seed);

        Floor background = Blocks.empty.asFloor();
        
        //background
        tiles.eachTile(t -> t.setFloor(background));
        
        //platform
        for (int w = dx - pw; w <= dx + pw; w++) {
           for (int h = dy - ph; h <= dy + ph; h++) {
              tiles.getn(w, h).setFloor(Blocks.metalFloor.asFloor());
           }
        }
        
        //drops
        pass((x, y) -> {
           if (floor != background) {
              Vec2 v = new Vec2(x, y);
              if (rand.chance(0.1) && !v.within(dx, dy, 7f * tilesize)) {
                 Tile tile = world.tile(x, y);
                 block = drops[Mathf.floor(Mathf.randomSeed(tile.pos() + seed, 0f, drops.length))];
              }
           }
        });
        
        //rooms
        generateRooms(rooms, room -> room.generate());
         
        tiles.getn(dx - width / 4, dy - height / 4).setOverlay(Blocks.spawn);

        Schematics.placeLaunchLoadout(dx, dy);

        state.rules.planetBackground = new PlanetParams(){{
            planet = orbiting;
            zoom = 1f;
            camPos = new Vec3(3f, 7.94731f, 1.04936f);
        }};

        state.rules.dragMultiplier = 0.7f;
        state.rules.borderDarkness = false;
        state.rules.waves = false;

        //state.rules.showSpawns = true;
        //state.rules.spawns = Waves.generate(0.5f, rand, false, true, false);
    }
    
    public void generateRooms(Seq<BaseRoom> hotel, Cons<BaseRoom> room) {
       for (BaseRoom r : hotel) {
          //a chance for a room to be connected to the parent
          if (rand.chance(0.4)) {
             replaceLine(width / 2, height / 2, r.x, r.y);
          }
          
          room.get(r);
       }
    }
    
    public void replaceLine(int x1, int y1, int x2, int y2) {
       Geometry.iterateLine(0, x1, y1, x2, y2, 1, (x, y) -> {
          replaceRadius(Blocks.metalFloor3.asFloor(), x, y, 3);
       });
    }
    
    /** A slightly modified version of @link erase(). */
    public void replaceRadius(Floor floorType, int cx, int cy, int rad) {
       for (int x = -rad; x <= rad; x++) {
          for (int y = -rad; y <= rad; y++) {
             int wx = cx + x, wy = cy + y;
             if (Structs.inBounds(wx, wy, width, height) && Mathf.within(x, y, rad)) {
                Tile other = tiles.getn(wx, wy);
                if (other.floor() == Blocks.empty.asFloor()) {
                   other.setFloor(floorType);
                }
             }
          }
       }
    }
    
    @Override
    public Schematic getDefaultLoadout() {
        return Loadouts.basicBastion;
    }

    @Override
    public int getSectorSize(Sector sector) {
        return 150;
    }
}