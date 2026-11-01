package com.zenovka.passablefoliage;

import java.util.Map;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.TransformerExclusions({"com.zenovka.passablefoliage"})
public class FoliageCorePlugin implements IFMLLoadingPlugin {
    @Override public String[] getASMTransformerClass() { return new String[] {"com.zenovka.passablefoliage.FoliageTransformer"}; }
    @Override public String getModContainerClass() { return null; }
    @Override public String getSetupClass() { return null; }
    @Override public void injectData(Map<String, Object> data) {}
    @Override public String getAccessTransformerClass() { return null; }
}
