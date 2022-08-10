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
       get(0), get(1), get(3), get(4),
       get(5), get(6), get(7), get(DropGenerator.generated.size - 4),
       get(DropGenerator.generated.size - 3)
    };
    
    public Seq<BaseRoom> rooms = new Seq<BaseRoom>(); /*Seq.with(
       new BaseRoom(25, 20, 5, 5),
       new BaseRoom(135, 50, 5, 5),
       new VentRoom(80, 130, 8, 8)
    );*/
    
    public int pw = 30, ph = 30;
    public Planet orbiting = Planets.erekir;
    
    @Nullable Rand rand;
    int seed;
    int baseSeed = 5;
    
    ItemProp get(int id) {
        return DropGenerator.generated.get(id);
    }

    @Override
    public void generate() {
        seed = state.rules.sector.planet.id;
        rand = new Rand(seed + baseSeed);
        rooms.clear();
        
        int dx = width / 2, dy = height / 2;
        Floor background = Blocks.empty.asFloor();
        
        float range = 20f;
        
        //room chances
        int emptyRooms = rand.random(1, 4);
        int specialRooms = rand.random(2, 3);
        int defenseRooms = rand.random(1, 2);
        
        addRooms(dx, dy, pw + 13f + rand.random(range), emptyRooms, (x, y) -> {
           rooms.add(new BaseRoom(x, y, 5, 5));
        });
        
        addRooms(dx, dy, pw + 20f + rand.random(range), specialRooms, (x, y) -> {
           boolean chance = rand.chance(0.5);
           if (chance) {
              rooms.add(new VentRoom(x, y, 8, 8));
           }
           else {
              boolean chance2 = rand.chance(0.5);
              rooms.add(new MiningRoom(x, y, 8, 8, chance2 ? Blocks.wallOreBeryllium : Blocks.graphiticWall){{
                 belowFloor = chance2 ? Blocks.rhyolite.asFloor() : Blocks.carbonStone.asFloor();
              }});
           }
        });
        
        //add ulterior defense room branches
        addRooms(dx, dy, pw + 16f + rand.random(range), defenseRooms, (x, y) -> {
           rooms.add(new DefenseRoom(x, y, 7, 7));
        });
        
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
              if (rand.chance(0.09)) {
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
    
    public void addRooms(int x, int y, float range, int amount, Cons2<Integer, Integer> cons) {
       for (int i = 0; i < amount; i++) {
          Tmp.v1.trns(rand.random(360f), range);
          float rx = (x + Tmp.v1.x);
          float ry = (y + Tmp.v1.y);
          cons.get((int) rx, (int) ry);
       }
    }
    
    public void addRooms(BaseRoom room, float range, int amount, Cons2<Integer, Integer> cons) {
       for (int i = 0; i < amount; i++) {
          Tmp.v1.trns(rand.random(360f), range + room.width);
          float rx = (room.x + Tmp.v1.x);
          float ry = (room.y + Tmp.v1.y);
          cons.get((int) rx, (int) ry);
       }
    }
    
    public void generateRooms(Seq<BaseRoom> hotel, Cons<BaseRoom> room) {
       for (BaseRoom r : hotel) {
          if (!(r instanceof DefenseRoom)) replaceLine(width / 2, height / 2, r.x, r.y);
          room.get(r);
       }
    }
    
    public void replaceLine(int x1, int y1, int x2, int y2) {
       Geometry.iterateLine(0, x1, y1, x2, y2, 1, (x, y) -> {
          replaceRadius(Blocks.metalFloor3.asFloor(), Mathf.round(x), Mathf.round(y), 2);
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