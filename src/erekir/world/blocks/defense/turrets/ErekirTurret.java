package erekir.world.blocks.defense.turrets;

import mindustry.world.draw.*;
import mindustry.world.blocks.defense.turrets.*;

public class ErekirTurret extends ItemTurret{
   
   public ErekirTurret(String name) {
      super(name);
      drawer = new DrawTurret("erekirTurret-");
   }
}