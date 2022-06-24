package erekir.content;

import arc.graphics.*;
import mindustry.type.*;
import erekir.ctype.*;
import erekir.graphics.*;

public class ErkLiquids implements AltContentList{
   public static Liquid angryArkycite;
   
   @Override
   public void load() {
      angryArkycite = new Liquid("powerfulArkycite", ErkPal.darkArkycite){{
         flammability = 0.7f;
         temperature = 0.4f;
         viscosity = 0.65f;
      }};
   }
}