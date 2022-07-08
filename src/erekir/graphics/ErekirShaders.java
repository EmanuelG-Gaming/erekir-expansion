package erekir.graphics;

import arc.util.*;
import mindustry.graphics.CacheLayer;
import erekir.graphics.shader.*;

import static mindustry.Vars.*;

/** @author xStaBUx, with slight modifications. */
public class ErekirShaders {
    public static @Nullable ErekirSurfShader angryArkycite, pooledNeoplasm;
    
    public static CacheLayer.ShaderLayer arkyLayer, neoplasmLayer;
    protected static boolean loaded;

    public static void init() {
        angryArkycite = new ErekirSurfShader("angryArkycite");
        pooledNeoplasm = new ErekirSurfShader("circle");
    }

    public static void load() {
        if (!headless) {
            angryArkycite = new ErekirSurfShader("angryArkycite");
            pooledNeoplasm = new ErekirSurfShader("circle");
            loaded = true;
        }
        arkyLayer = new CacheLayer.ShaderLayer(angryArkycite);
        neoplasmLayer = new CacheLayer.ShaderLayer(pooledNeoplasm);
        CacheLayer.add(arkyLayer, neoplasmLayer);
    }
    
    public static void dispose() {
        if (!headless && loaded) {
           angryArkycite.dispose();
           pooledNeoplasm.dispose();
        }
    }
}