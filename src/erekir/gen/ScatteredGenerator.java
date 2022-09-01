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
import erekir.room.*;

import static mindustry.Vars.*;

public class ScatteredGenerator extends BlankPlanetGenerator{
    public Seq<BaseRoom> rooms = new Seq<BaseRoom>();
    
    @Nullable Rand rand;
    int seed;
    int baseSeed = 5;
    
    @Override
    public void generate() {
        seed = state.rules.sector.planet.id;
        rand = new Rand(seed + baseSeed);
        
        Floor background = Blocks.empty.asFloor();
        
        rooms.clear();
        
        for (int x = 0; x < width; x++) {
           for (int y = 0; y < height; y++) {
               if (rand.chance(0.05)) {
                  rooms.add(new BaseRoom(x, y, 4, 4));
               }
           }
        }
        
        //background
        tiles.eachTile(t -> t.setFloor(background));
        
        //stash/room structure
        consRooms(room -> room::generate);
        
        int rx = rooms.random(rand).x, ry = rooms.random(rand).y;
        Schematics.placeLaunchLoadout(rx, ry);
        
        //search for the furthest platform to place the spawn
        consRooms(room -> {
           float cdist = 0;
           float dst = Mathf.dst2(rx, ry, room.x, room.y);
           if (dst > cdist) {
              cDist = dst;
              tiles.getn(room.x, room.y).setOverlay(Blocks.spawn);
           } 
        });
        
        state.rules.planetBackground = new PlanetParams(){{
            planet = Planets.erekir;
            zoom = 0.5f;
            camPos = new Vec3(4.95746f, 1.09471f, 2f);
        }};

        state.rules.dragMultiplier = 0.7f;
        state.rules.borderDarkness = false;
        state.rules.waves = true;
        state.rules.showSpawns = true;
        state.rules.spawns = Waves.generate(0.5f, rand, false, true, false);
    }
    
    public void consRooms(Cons<BaseRoom> room) {
       for (BaseRoom r : rooms) {
          room.get(r);
       }
    }
    
    @Override
    public Schematic getDefaultLoadout() {
        return Loadouts.basicBastion;
    }

    @Override
    public int getSectorSize(Sector sector) {
        return 500;
    }
}