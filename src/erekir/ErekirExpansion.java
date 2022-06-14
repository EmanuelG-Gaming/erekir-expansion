package erekir;

import arc.Core;
import arc.Events;
import arc.util.Log;
import arc.util.Time;
import mindustry.content.*;
import mindustry.world.*;
import mindustry.world.blocks.units.*;
import mindustry.mod.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import erekir.util.*;
import erekir.content.*;
import erekir.world.blocks.environment.ItemProp;
import erekir.ctype.*;
import erekir.ui.button.Pickup;

import static mindustry.type.ItemStack.with;
import static mindustry.Vars.*;

public class ErekirExpansion extends Mod{

    public ErekirExpansion() {
        Log.info("Loaded Erekir buoyancy");
        
        Events.on(FileTreeInitEvent.class, e -> Core.app.post(() -> ErkSounds.load()));
      
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
            
           ((Reconstructor) Blocks.mechRefabricator).addUpgrade(ErkUnitTypes.gem, ErkUnitTypes.geode);
           ((Reconstructor) Blocks.shipRefabricator).addUpgrade(ErkUnitTypes.aggregate, ErkUnitTypes.agglomerate);
        });
       
        Events.on(WorldLoadEvent.class, e -> {
           if (headless) return;
           
           ErkUtil.allDrops(e -> e.addButton());
        });
    }
    
    private final AltContentList[] erekirContent = {
       new ErkBlocks(),
       new ErkUnitTypes()
    };
    
    @Override
    public void loadContent() {
        // load everything from the array
        for (AltContentList list : erekirContent) list.load();
        ErkTechTree.load();
    }
    
    public void addToFabricator(Block bloc, UnitFactory.UnitPlan plan) {
        UnitFactory factory = (UnitFactory) bloc;
        factory.plans.add(plan);
        
        factory.configurable = true;
        factory.init();
    }
}
