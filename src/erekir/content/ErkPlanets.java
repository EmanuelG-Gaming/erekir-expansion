package erekir.content;

import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import erekir.gen.*;

import static mindustry.content.Planets.*;

public class ErkPlanets{
    public static Planet testStash;

    public static void load() {
       testStash = makeStash("testStash", erekir, 0.5f);
    }

    private static Planet makeStash(String name, Planet parent, float scale) {
       return new Planet(name, parent, 0.12f){{
          hasAtmosphere = false;
          updateLighting = false;
          sectors.add(new Sector(this, Ptile.empty));
          camRadius = 0.68f * scale;
          minZoom = 0.6f;
          drawOrbit = false;
          accessible = true;
          clipRadius = 2f;
          defaultEnv = Env.space;
          orbitSpacing = 1.1f;
          
          generator = new StashGenerator();
      }};
   }
}