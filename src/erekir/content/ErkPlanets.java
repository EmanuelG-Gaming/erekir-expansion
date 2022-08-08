package erekir.content;

import mindustry.type.*;
import mindustry.graphics.g3d.PlanetGrid.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import erekir.ctype.*;
import erekir.gen.*;

import static mindustry.content.Planets.*;

public class ErkPlanets implements AltContentList{
    public Planet testStash;
   
    @Override
    public void load() {
       testStash = makeStash("testStash", erekir, 0.5f);
    }

    private Planet makeStash(String name, Planet parent, float scale) {
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