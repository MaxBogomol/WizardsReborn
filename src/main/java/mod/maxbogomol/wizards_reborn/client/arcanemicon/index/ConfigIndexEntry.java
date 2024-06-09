package mod.maxbogomol.wizards_reborn.client.arcanemicon.index;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtils;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigIndexEntry {
    public ForgeConfigSpec.ConfigValue<Boolean> booleanConfig;
    public ForgeConfigSpec.ConfigValue<Integer> integerConfig;
    public Knowledge knowledge;
    public String modId;
    public ForgeConfigSpec spec;

    public ConfigIndexEntry() {
        this.modId = WizardsReborn.MOD_ID;
        this.spec = ClientConfig.SPEC;
    }

    public ConfigIndexEntry(Knowledge knowledge) {
        this.modId = WizardsReborn.MOD_ID;
        this.spec = ClientConfig.SPEC;
        this.knowledge = knowledge;
    }

    public ConfigIndexEntry(String modId) {
        this.modId = modId;
    }

    public ConfigIndexEntry(String modId, Knowledge knowledge) {
        this.modId = modId;
        this.knowledge = knowledge;
    }

    public ConfigIndexEntry setBooleanConfig(ForgeConfigSpec.ConfigValue<Boolean> config) {
        booleanConfig = config;
        return this;
    }

    public ConfigIndexEntry setIntegerConfig(ForgeConfigSpec.ConfigValue<Integer> config) {
        integerConfig = config;
        return this;
    }

    public ConfigIndexEntry setSpecConfig(ForgeConfigSpec spec) {
        this.spec = spec;
        return this;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isBoolean() {
        return booleanConfig != null;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isInteger() {
        return integerConfig != null;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isUnlocked() {
        if (knowledge == null) {
            return true;
        } else {
            return (KnowledgeUtils.isKnowledge(Minecraft.getInstance().player, knowledge));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public boolean hasKnowledge() {
        return knowledge != null;
    }

    @OnlyIn(Dist.CLIENT)
    public Knowledge getKnowledge() {
        return knowledge;
    }

    @OnlyIn(Dist.CLIENT)
    public Component getKnowledgeName() {
        if (knowledge == null) {
            return Component.empty();
        }
        return knowledge.getName();
    }
}
