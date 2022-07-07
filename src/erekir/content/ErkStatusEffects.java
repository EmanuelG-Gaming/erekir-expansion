package erekir.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.graphics.*; 
import mindustry.ctype.*;
import mindustry.type.*;
import mindustry.entities.*;
import erekir.graphics.*;
import erekir.ctype.*;

public class ErkStatusEffects implements AltContentList{
    public static StatusEffect
    
    metalloclast;
    
    @Override
    public void load() {
        metalloclast = new StatusEffect("metalloclast"){{
            color = ErkPal.darkArkycite;
            speedMultiplier = 0.85f;
            healthMultiplier = 0.70f;
            damage = 1.40f;
            effect = new Effect(90f, e -> {
               Draw.color(ErkPal.darkArkycite);

               Fill.circle(e.x, e.y, e.foutpow() * 1.5f + 0.14f);
            });
        }};
    }
}