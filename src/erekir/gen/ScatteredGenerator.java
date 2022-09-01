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
    
    private static BaseRoom result;
    private static float cdist;
    
    @Override
    public void generate() {
        seed = state.rules.sector.planet.id;
        rand = new Rand(seed + baseSeed);
        
        Floor background = Blocks.empty.asFloor();
        
        rooms.clear();
        
        tiles.eachTile(t -> {
           if (rand.chance(0.0004)) {
              rooms.add(new BaseRoom(t.x, t.y, 3, 3));
           }
        });
        
        //background
        tiles.eachTile(t -> t.setFloor(background));
        
        //stash/room structure
        consRooms(room -> room.generate());
        
        BaseRoom start = rooms.random(rand);
        BaseRoom far = getFurthestRoom(start);
        
        Schematics.placeLaunchLoadout(start.x, start.y);
        tiles.getn(far.x, far.y).setOverlay(Blocks.spawn);
           
        
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
    
    public BaseRoom getFurthestRoom(BaseRoom from) {
       result = null;
       cdist = 0;
       
       consRooms(room -> {
          float dst = Mathf.dst2(from.x, from.y, room.x, room.y);
          if ((result == null || dst > cdist)) {
             cdist = dst;
             result = room;
          } 
       });
       return result;
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