package erekir.world.blocks.defense.turrets;

import arc.Core;
import mindustry.world.blocks.defense.turrets.*;

public class ErekirTurret extends ItemTurret{
   
   public ErekirTurret(String name) {
      super(name);
   }
   
   @Override
   public void load() {
      super.load();
      baseRegion = Core.atlas.find("erekir-expansion-erekirTurret" + "-base-" + size);
   }
}