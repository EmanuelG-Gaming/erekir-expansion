package erekir.gen;

import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import arc.util.noise.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.graphics.g3d.*;
import mindustry.maps.generators.*;
import mindustry.type.*;
import mindustry.world.blocks.environment.*;
import erekir.world.blocks.environment.*;

import static mindustry.Vars.*;

public class StashGenerator extends BlankPlanetGenerator{
    public ItemProp[] drops = {
       get(0), get(1),
       get(4), get(6),
       get(DropGenerator.generated.size - 3), get(DropGenerator.generated.size - 2)
    };
    
    public int pw = 35, ph = 35;
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
        for (int w = 0; w < pw; w++) {
           for (int h = 0; h < ph; h++) {
              tiles.getn(dx / 2 + w, dy / 2 + h).setFloor(Blocks.metalFloor.asFloor());
           }
        }
        
        //drops
        pass((x, y) -> {
           if (floor != background) {
              if (rand.chance(0.1)) {
                 block = drops[Mathf.floor(Mathf.randomSeed(seed, 0f, drops.length))];
              }
           }
        });
        
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

    @Override
    public Schematic getDefaultLoadout() {
        return Loadouts.basicBastion;
    }

    @Override
    public int getSectorSize(Sector sector) {
        return 100;
    }
}