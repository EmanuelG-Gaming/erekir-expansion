package erekir.graphics;

import mindustry.graphics.CacheLayer.*;
import erekir.graphics.shader.*;

import static mindustry.Vars.*;

/** @author xStaBUx */
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
        }
        arkyLayer = new CacheLayer.ShaderLayer(angryArkycite);
        CacheLayer.add(arkyLayer);
    }
}