package erekir;

import arc.struct;
import arc.Events;
import mindustry.game.EventType.*;
import erekir.room;

public class ErkVars{
   public Seq<BaseRoom> rooms = new Seq<>();
   
   public void load() {
      Events.on(WorldLoadEvent.class, e -> {
         rooms.clear();
      });
   }
}