package erekir.graphics;

import arc.util.*;
import mindustry.graphics.CacheLayer;
import erekir.graphics.shader.*;

import static mindustry.Vars.*;

/** @author xStaBUx, with slight modifications. */
public class ErekirShaders {
    public static @Nullable ErekirSurfShader angryArkycite;
    public static @Nullable ErekirCircleShader circle;
    
    public static CacheLayer.ShaderLayer arkyLayer, circleLayer;
    protected static boolean loaded;

    public static void init() {
        angryArkycite = new ErekirSurfShader("angryArkycite");
        circle = new ErekirCircleShader("circle");
    }

    public static void load() {
        if (!headless) {
            angryArkycite = new ErekirSurfShader("angryArkycite");
            circle = new ErekirCircleShader("circle");
            loaded = true;
        }
        arkyLayer = new CacheLayer.ShaderLayer(angryArkycite);
        circleLayer = new CacheLayer.ShaderLayer(circle);
        CacheLayer.add(arkyLayer, circleLayer);
    }
    
    public static void dispose() {
        if (!headless && loaded) {
           angryArkycite.dispose();
           circle.dispose();
        }
    }
}