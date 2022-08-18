package erekir;

import arc.struct;
import erekir.room;

public class ErkVars{
   public Seq<BaseRoom> rooms = new Seq<BaseRoom>;
   
   public void load() {
      rooms.clear();
   }
}