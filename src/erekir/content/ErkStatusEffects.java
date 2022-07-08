package erekir.content;

import arc.math.*;
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
            damage = 0.90f;
            effect = new Effect(90f, e -> {
               Draw.color(ErkPal.darkArkycite);
               Draw.alpha(Interp.pow5Out.apply(e.fslope()));
               
               Fill.circle(e.x, e.y, e.foutpow() * 3f + 0.14f);
            }).layer(Layer.flyingUnit + 0.01f);
        }};
    }
}