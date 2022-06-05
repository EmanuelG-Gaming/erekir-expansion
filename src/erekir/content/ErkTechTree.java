package erekir.content;

import mindustry.ctype.*;
import mindustry.content.TechTree;

import static mindustry.content.Blocks.*;
import static mindustry.content.TechTree.*;

//TODO name
public class ErkTechTree{
   static TechTree.TechNode context = null;
   
   public static void load() {
       node(mechFabricator, () -> {
           node(ErkUnitTypes.gem);
       });
       node(shipFabricator, () -> {
           node(ErkUnitTypes.aggregate);
       });
       
       node(mechRefabricator, () -> {
           node(ErkUnitTypes.geode);
       });
       node(shipRefabricator, () -> {
           node(ErkUnitTypes.agglomerate);
       });
   }
}