package erekir;

import arc.Core;
import arc.Events;
import arc.util.Time;
import arc.util.Log;
import mindustry.content.*;
import mindustry.world.blocks.units.*;
import mindustry.mod.*;
import mindustry.game.EventType.*;
import erekir.content.*;
import erekir.ctype.*;

import static mindustry.type.ItemStack.with;

public class ErekirExpansion extends Mod{

    public ErekirExpansion() {
        Log.info("Loaded Erekir buoyancy");
        
        Events.on(FileTreeInitEvent.class, e -> Core.app.post(() -> ErkSounds.load()));
      
        Events.on(ContentInitEvent.class, e -> {
           addToFabricator(
              ((UnitFactory) Blocks.mechFabricator),
              new UnitFactory.UnitPlan(ErkUnitTypes.gem, (float) 40 * Time.toSeconds, with(Items.beryllium, 100, Items.silicon, 50))
           );
           addToFabricator(
              ((UnitFactory) Blocks.shipFabricator),
              new UnitFactory.UnitPlan(ErkUnitTypes.aggregate, (float) 30 * Time.toSeconds, with(Items.beryllium, 85, Items.silicon, 35, Items.graphite, 20))
           );
           
           ((Reconstructor) Blocks.mechRefabricator).addUpgrade(ErkUnitTypes.gem, ErkUnitTypes.geode);
           ((Reconstructor) Blocks.shipRefabricator).addUpgrade(ErkUnitTypes.aggregate, ErkUnitTypes.agglomerate);
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
    
    public void addToFabricator(UnitFactory factory, UnitFactory.UnitPlan plan) {
        factory.plans.add(plan);
        
        factory.configurable = true;
        factory.init();
    }
}
