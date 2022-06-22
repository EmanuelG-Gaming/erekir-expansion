package erekir.graphics;

import arc.util.*;
import mindustry.graphics.CacheLayer;
import erekir.graphics.shader.*;

import static mindustry.Vars.*;

/** @author xStaBUx, with slight modifications. */
public class ErekirShaders {
    public static @Nullable ErekirSurfShader angryArkycite;
    
    public static CacheLayer.ShaderLayer arkyLayer;
    protected static boolean loaded;

    public static void init() {
        angryArkycite = new ErekirSurfShader("angryArkycite");
    }

    public static void load() {
        if (!headless) {
            angryArkycite = new ErekirSurfShader("angryArkycite");
            loaded = true;
        }
        arkyLayer = new CacheLayer.ShaderLayer(angryArkycite);
        CacheLayer.add(arkyLayer);
    }
    
    public static void dispose() {
        if (!headless && loaded) {
           angryArkycite.dispose();
        }
    }
}