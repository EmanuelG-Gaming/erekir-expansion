package erekir.content;

import mindustry.ctype.*;
import mindustry.content.TechTree;

import static mindustry.content.Blocks.*;
import static mindustry.content.TechTree.*;

//TODO name
public class ErkTechTree{
   private static TechTree.TechNode context = null;
   
   public static void load() {
       vanillaNode(mechFabricator, () -> {
           node(ErkUnitTypes.gem);
       });
       vanillaNode(shipFabricator, () -> {
           node(ErkUnitTypes.aggregate, () -> {
               node(ErkUnitTypes.spread);
           });
       });
       
       vanillaNode(mechRefabricator, () -> {
           node(ErkUnitTypes.geode);
       });
       vanillaNode(shipRefabricator, () -> {
           node(ErkUnitTypes.agglomerate, () -> {
               node(ErkUnitTypes.apart);
           });
       });
       
       vanillaNode(primeRefabricator, () -> {
           node(ErkUnitTypes.accumulate, () -> {
               node(ErkUnitTypes.shredder);
           });
       });
       
       vanillaNode(plasmaBore, () -> {
           node(ErkBlocks.dGatherer, () -> {
               node(ErkBlocks.dCollector);
           });
       });
   }
   
   private static void vanillaNode(UnlockableContent parent, Runnable children) {
       context = TechTree.all.find(t -> t.content == parent);
       children.run();
   }
}