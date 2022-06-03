package erekir.content;

import mindustry.ctype.*;
import mindustry.content.TechTree;

import static mindustry.content.Blocks.*;
import static mindustry.content.TechTree.*;

//TODO name
public class ErkTechTree{
   static TechTree.TechNode context = null;
   
   public static void load() {
       customNodeBranch(mechFabricator, () -> {
           node(ErkUnitTypes.gem);
       });
       customNodeBranch(shipFabricator, () -> {
           node(ErkUnitTypes.aggregate);
       });
       
       customNodeBranch(mechRefabricator, () -> {
           node(ErkUnitTypes.geode);
       });
       customNodeBranch(shipRefabricator, () -> {
           node(ErkUnitTypes.agglomerate);
       });
   }
   
   // Where's the modded branch?!!? 1 -Antumbra
   //idk, i stolen it from bm
   private static void customNodeBranch(UnlockableContent parent, Runnable children) {
       context = TechTree.all.find(t -> t.content == parent);
       children.run();
    }
}