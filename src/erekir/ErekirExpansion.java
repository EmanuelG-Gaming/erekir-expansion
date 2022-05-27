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
           ((UnitFactory) Blocks.mechFabricator).plans.add(
               new UnitFactory.UnitPlan(ErkUnitTypes.gem, (float) 40 * Time.toSeconds, with(Items.beryllium, 300, Items.silicon, 50))
           );
           ((UnitFactory) Blocks.shipFabricator).plans.add(
               new UnitFactory.UnitPlan(ErkUnitTypes.aggregate, (float) 30 * Time.toSeconds, with(Items.beryllium, 100, Items.silicon, 35, Items.graphite, 20))
           );
           
           // silly
           Blocks.mechFabricator.configurable = true;
           Blocks.shipFabricator.configurable = true;
           Blocks.mechFabricator.init();
           Blocks.shipFabricator.init();
           ((Reconstructor) Blocks.mechRefabricator).addUpgrade(ErkUnitTypes.gem, ErkUnitTypes.geode);
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
    }

}
