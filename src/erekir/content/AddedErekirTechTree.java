package erekir.content;

import arc.struct.*;
import mindustry.type.*;
import mindustry.game.Objectives.*;
import mindustry.ctype.*;
import mindustry.content.TechTree;

import static mindustry.content.Liquids.*;
import static mindustry.content.Blocks.*;
import static mindustry.content.TechTree.*;
import static mindustry.content.SectorPresets.*;
import static erekir.content.ErkSectorPresets.*;

public class AddedErekirTechTree{
   private static TechNode context = null;
   
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
       
       vanillaNode(onset, () -> {
           node(alternateApproach, Seq.with(new SectorComplete(onset)));
       });
       
       vanillaNode(arkycite, () -> {
           node(ErkLiquids.angryArkycite, Seq.with(new Produce(ErkLiquids.angryArkycite)), () -> {});
       });
       
       vanillaNode(plasmaBore, () -> {
           node(ErkBlocks.dGatherer, Seq.with(new SectorComplete(alternativeApproach)), () -> {
               node(ErkBlocks.dCollector);
           });
       });
       
       vanillaNode(breach, () -> {
           node(ErkBlocks.fissure);
       });
       
       vanillaNode(tungstenWallLarge, () -> {
          node(ErkBlocks.berylUpkeeper);
       });
   }
   
   private static void vanillaNode(UnlockableContent parent, Runnable children) {
       context = TechTree.all.find(t -> t.content == parent);
       children.run();
   }
   
   // Ion moment
   private static void node(UnlockableContent content, ItemStack[] requirements, Seq<Objective> objectives, Runnable children) {
       TechNode node = new TechNode(context, content, requirements);
       if (objectives != null) node.objectives = objectives;

       TechNode prev = context;
       context = node;
       children.run();
       context = prev;
   }
   
   private static void node(UnlockableContent content, ItemStack[] requirements, Runnable children) {
       node(content, requirements, null, children);
   }

   private static void node(UnlockableContent content, Seq<Objective> objectives, Runnable children) {
       node(content, content.researchRequirements(), objectives, children);
   }

   private static void node(UnlockableContent content, Seq<Objective> objectives) {
       node(content, objectives, () -> {});
   }

   private static void node(UnlockableContent content, Runnable children) {
       node(content, content.researchRequirements(), children);
   }

   private static void node(UnlockableContent block) {
       node(block, () -> {});
   }
}