package erekir;

import arc.Core;
import arc.math.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.Events;
import arc.util.*;
import mindustry.content.*;
import mindustry.world.*;
import mindustry.world.blocks.units.*;
import mindustry.world.blocks.environment.Floor;
import mindustry.type.*;
import mindustry.mod.*;
import mindustry.game.EventType.*;
import mindustry.graphics.*;
import mindustry.gen.*;
import erekir.util.*;
import erekir.content.*;
import erekir.ctype.*;
import erekir.graphics.*;
import erekir.util.*;
import erekir.ui.*;
import erekir.ui.button.*;
import erekir.world.blocks.gather.*;
import erekir.world.blocks.environment.*;

import static mindustry.type.ItemStack.with;
import static mindustry.Vars.*;

/** Mod's main, also holds some utility methods. */
public class ErekirExpansion extends Mod{

    public ErekirExpansion() {
        Log.info("Loaded Erekir buoyancy");
        
        Events.on(FileTreeInitEvent.class, e -> Core.app.post(() -> {
           ErkSounds.load();
           ErekirShaders.load();
        }));
      
        Events.on(ContentInitEvent.class, e -> {
           addToFabricator(
              Blocks.mechFabricator,
              new UnitFactory.UnitPlan(ErkUnitTypes.gem, (float) 40 * Time.toSeconds, with(Items.beryllium, 100, Items.silicon, 50))
           );
           addToFabricator(
              Blocks.shipFabricator,
              new UnitFactory.UnitPlan(ErkUnitTypes.aggregate, (float) 30 * Time.toSeconds, with(Items.beryllium, 85, Items.silicon, 35, Items.graphite, 20))
           );
           addToFabricator(
              Blocks.shipFabricator,
              new UnitFactory.UnitPlan(ErkUnitTypes.spread, (float) 25 * Time.toSeconds, with(Items.tungsten, 20, Items.silicon, 45, Items.graphite, 30))
           );
            
           addToReconstructor(Blocks.mechRefabricator, ErkUnitTypes.gem, ErkUnitTypes.geode);
           addToReconstructor(Blocks.shipRefabricator, ErkUnitTypes.aggregate, ErkUnitTypes.agglomerate);
           addToReconstructor(Blocks.shipRefabricator, ErkUnitTypes.spread, ErkUnitTypes.apart);
           addToReconstructor(Blocks.primeRefabricator, ErkUnitTypes.agglomerate, ErkUnitTypes.accumulate);
           addToReconstructor(Blocks.primeRefabricator, ErkUnitTypes.apart, ErkUnitTypes.shredder);
        });
       
        Events.on(ClientLoadEvent.class, e -> {
            ErekirSettings.load();
        });
        
        Events.on(WorldLoadEvent.class, e -> {
           if (headless) return;
           
           ErkUtil.allDrops(b -> b.addButton());
        });
        
        Events.on(DisposeEvent.class, e -> {
           ErekirShaders.dispose();
        });
        
        Events.run(Trigger.draw, () -> {
           for (Unit unit : Groups.unit) {
             
              Floor floor = unit.tileOn() == null ? Blocks.air.asFloor() : unit.tileOn().floor();
              if (floor.isLiquid && floor == ErkBlocks.angryArkyciteFloor) {
                 if (!unit.isFlying() && unit.hovering == false) {
                    float z = Draw.z(); 
                  
                    Draw.z(Layer.debris);
                    Draw.color(Tmp.c1.set(floor.mapColor).mul(1.5f));
                    Lines.stroke(4f);
                    Lines.circle(unit.x, unit.y, unit.type.hitSize * 1.25f * (1f - unit.drownTime));
                    Draw.reset();
                 
                    Draw.z(z);
                 }
              }
           }
        });
    }
    
    private final AltContentList[] erekirContent = {
       new ErkLiquids(),
       new ErkBlocks(),
       new ErkUnitTypes()
    };
    
    @Override
    public void loadContent() {
        // load everything from the array
        for (AltContentList list : erekirContent) list.load();
        ErekirVars.load();
        ErkTechTree.load();
    }
    
    public void addToFabricator(Block bloc, UnitFactory.UnitPlan plan) {
        if (!(bloc instanceof UnitFactory)) return;
        
        UnitFactory factory = (UnitFactory) bloc;
        factory.plans.add(plan);
        
        factory.configurable = true;
        factory.init();
    }
    
    public void addToReconstructor(Block bloc, UnitType unit, UnitType upgrade) {
        if (!(bloc instanceof Reconstructor)) return;
        
        Reconstructor recon = (Reconstructor) bloc;
        recon.addUpgrade(unit, upgrade);
    }
}
