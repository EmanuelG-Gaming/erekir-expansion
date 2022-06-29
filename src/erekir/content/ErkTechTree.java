package erekir.content;

import mindustry.ctype.*;
import mindustry.content.TechTree;

import static mindustry.content.Blocks.*;
import static mindustry.content.TechTree.*;

//TODO name
public class ErkTechTree{
   public static void load() {
       node(mechFabricator, () -> {
           node(ErkUnitTypes.gem);
       });
       node(shipFabricator, () -> {
           node(ErkUnitTypes.aggregate, () -> {
               node(ErkUnitTypes.spread);
           });
       });
       
       node(mechRefabricator, () -> {
           node(ErkUnitTypes.geode);
       });
       node(shipRefabricator, () -> {
           node(ErkUnitTypes.agglomerate, () -> {
               node(ErkUnitTypes.apart);
           });
       });
       
       node(primeRefabricator, () -> {
           node(ErkUnitTypes.accumulate, () -> {
               node(ErkUnitTypes.shredder);
           });
       });
   }
}