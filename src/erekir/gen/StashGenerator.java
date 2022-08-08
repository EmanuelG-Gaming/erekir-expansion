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
       new BaseRoom(120, 50, 5, 5),
       new VentRoom(80, 100, 8, 8)
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
              if (rand.chance(0.1)) {
                 Tile tile = world.tile(x, y);
                 block = drops[Mathf.floor(Mathf.randomSeed(tile.pos() + seed, 0f, drops.length))];
              }
           }
        });
        
        //cleanup
        ErkUtil.dropsWithin(Team.derelict, dx, dy, 7f * tilesize, b -> {
           Tile t = world.tile((int) b.x / tilesize, (int) b.y / tilesize);
           t.setBlock(Blocks.air);
        });
        
        //rooms
        generateRooms(room -> room.generate());
         
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
    
    public void generateRooms(Cons<BaseRoom> room) {
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
        return 150;
    }
}