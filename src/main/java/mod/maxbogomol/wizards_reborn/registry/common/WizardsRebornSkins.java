package mod.maxbogomol.wizards_reborn.registry.common;

import mod.maxbogomol.fluffy_fur.client.model.item.CustomModel;
import mod.maxbogomol.fluffy_fur.client.render.item.LargeItemRenderer;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.skin.Skin;
import mod.maxbogomol.wizards_reborn.api.skin.Skins;
import mod.maxbogomol.wizards_reborn.client.model.item.ItemSkinsModels;
import mod.maxbogomol.wizards_reborn.client.model.item.SkinItemOverrides;
import mod.maxbogomol.wizards_reborn.client.model.item.WandCrystalsModels;
import mod.maxbogomol.wizards_reborn.common.skin.*;
import mod.maxbogomol.wizards_reborn.integration.common.farmersdelight.FarmersDelightIntegration;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.util.Map;

public class WizardsRebornSkins {
    public static Skin TOP_HAT_SKIN = new TopHatSkin(WizardsReborn.MOD_ID+":top_hat", new Color(54, 60, 81));
    public static Skin SOUL_HUNTER_SKIN = new SoulHunterSkin(WizardsReborn.MOD_ID+":soul_hunter", new Color(225, 99, 226));
    public static Skin IMPLOSION_SKIN = new ImplosionSkin(WizardsReborn.MOD_ID+":implosion", new Color(149, 237, 255));
    public static Skin PHANTOM_INK_SKIN = new PhantomInkSkin(WizardsReborn.MOD_ID+":phantom_ink", new Color(189, 237, 255));
    public static Skin MAGNIFICENT_MAID_SKIN = new MagnificentMaidSkin(WizardsReborn.MOD_ID+":magnificent_maid", new Color(153, 168, 184));
    public static Skin SUMMER_LOVE_SKIN = new SummerLoveSkin(WizardsReborn.MOD_ID+":summer_love", new Color(243, 181, 127));

    public static void register() {
        Skins.register(TOP_HAT_SKIN);
        Skins.register(SOUL_HUNTER_SKIN);
        Skins.register(IMPLOSION_SKIN);
        Skins.register(PHANTOM_INK_SKIN);
        Skins.register(MAGNIFICENT_MAID_SKIN);
        Skins.register(SUMMER_LOVE_SKIN);

        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            registerModels();
            return new Object();
        });
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerModels() {
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":top_hat");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":soul_hunter_hood");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":soul_hunter_costume");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":soul_hunter_trousers");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":soul_hunter_boots");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":soul_hunter_scythe");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":soul_hunter_arcane_wand");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":soul_hunter_wissen_wand");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":implosion_sword");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":implosion_pickaxe");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":implosion_axe");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":implosion_shovel");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":implosion_hoe");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":implosion_scythe");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":implosion_knife");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":implosion_arcane_wand");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":implosion_wissen_wand");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":magnificent_maid_headwear");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":magnificent_maid_suit");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":magnificent_maid_stockings");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":magnificent_maid_boots");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":magnificent_maid_arcane_wand");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":magnificent_maid_wissen_wand");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":summer_love_flower");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":summer_love_dress");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":summer_love_boots");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":summer_love_arcane_wand");
        ItemSkinsModels.addSkin(WizardsReborn.MOD_ID+":summer_love_wissen_wand");
    }

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void modelRegistrySkins(ModelEvent.RegisterAdditional event) {
            for (String skin : ItemSkinsModels.getSkins()) {
                event.register(ItemSkinsModels.getModelLocationSkin(skin));
            }

            event.register(LargeItemRenderer.getModelResourceLocation(WizardsReborn.MOD_ID, "skin/implosion_scythe"));
            event.register(LargeItemRenderer.getModelResourceLocation(WizardsReborn.MOD_ID, "skin/soul_hunter_scythe"));
        }

        @SubscribeEvent
        public static void modelBakeSkins(ModelEvent.ModifyBakingResult event) {
            Map<ResourceLocation, BakedModel> map = event.getModels();

            for (String skin : ItemSkinsModels.getSkins()) {
                BakedModel model = map.get(ItemSkinsModels.getModelLocationSkin(skin));
                ItemSkinsModels.addModelSkins(skin, model);
            }

            WandCrystalsModels.addWandItem(map, new ResourceLocation(WizardsReborn.MOD_ID, "skin/soul_hunter_arcane_wand"));
            WandCrystalsModels.addWandItem(map, new ResourceLocation(WizardsReborn.MOD_ID, "skin/implosion_arcane_wand"));
            WandCrystalsModels.addWandItem(map, new ResourceLocation(WizardsReborn.MOD_ID, "skin/magnificent_maid_arcane_wand"));
            WandCrystalsModels.addWandItem(map, new ResourceLocation(WizardsReborn.MOD_ID, "skin/summer_love_arcane_wand"));

            LargeItemRenderer.bakeModel(map, WizardsReborn.MOD_ID, "skin/soul_hunter_scythe");
            LargeItemRenderer.bakeModel(map, WizardsReborn.MOD_ID, "skin/implosion_scythe");

            addSkinModel(map, WizardsRebornItems.WISSEN_WAND.getId());
            addSkinModel(map, WizardsRebornItems.ARCANE_WOOD_SWORD.getId());
            addSkinModel(map, WizardsRebornItems.INNOCENT_WOOD_SWORD.getId());
            addSkinModel(map, WizardsRebornItems.ARCANE_GOLD_SWORD.getId());
            addSkinModel(map, WizardsRebornItems.ARCANE_WOOD_PICKAXE.getId());
            addSkinModel(map, WizardsRebornItems.INNOCENT_WOOD_PICKAXE.getId());
            addSkinModel(map, WizardsRebornItems.ARCANE_GOLD_PICKAXE.getId());
            addSkinModel(map, WizardsRebornItems.ARCANE_WOOD_AXE.getId());
            addSkinModel(map, WizardsRebornItems.INNOCENT_WOOD_AXE.getId());
            addSkinModel(map, WizardsRebornItems.ARCANE_GOLD_AXE.getId());
            addSkinModel(map, WizardsRebornItems.ARCANE_WOOD_SHOVEL.getId());
            addSkinModel(map, WizardsRebornItems.INNOCENT_WOOD_SHOVEL.getId());
            addSkinModel(map, WizardsRebornItems.ARCANE_GOLD_SHOVEL.getId());
            addSkinModel(map, WizardsRebornItems.ARCANE_WOOD_HOE.getId());
            addSkinModel(map, WizardsRebornItems.INNOCENT_WOOD_HOE.getId());
            addSkinModel(map, WizardsRebornItems.ARCANE_GOLD_HOE.getId());
            LargeItemRenderer.bakeModel(map, WizardsReborn.MOD_ID, "arcane_wood_scythe", new SkinItemOverrides());
            LargeItemRenderer.bakeModel(map, WizardsReborn.MOD_ID, "innocent_wood_scythe", new SkinItemOverrides());
            LargeItemRenderer.bakeModel(map, WizardsReborn.MOD_ID, "arcane_gold_scythe", new SkinItemOverrides());
            addSkinModel(map, WizardsRebornItems.ARCANE_FORTRESS_HELMET.getId());
            addSkinModel(map, WizardsRebornItems.ARCANE_FORTRESS_CHESTPLATE.getId());
            addSkinModel(map, WizardsRebornItems.ARCANE_FORTRESS_LEGGINGS.getId());
            addSkinModel(map, WizardsRebornItems.ARCANE_FORTRESS_BOOTS.getId());
            addSkinModel(map, WizardsRebornItems.INVENTOR_WIZARD_HAT.getId());
            addSkinModel(map, WizardsRebornItems.INVENTOR_WIZARD_COSTUME.getId());
            addSkinModel(map, WizardsRebornItems.INVENTOR_WIZARD_TROUSERS.getId());
            addSkinModel(map, WizardsRebornItems.INVENTOR_WIZARD_BOOTS.getId());

            if (FarmersDelightIntegration.isLoaded()) {
                addSkinModel(map, FarmersDelightIntegration.ARCANE_GOLD_KNIFE.getId());
                addSkinModel(map, FarmersDelightIntegration.ARCANE_WOOD_KNIFE.getId());
                addSkinModel(map, FarmersDelightIntegration.INNOCENT_WOOD_KNIFE.getId());
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void addSkinModel(Map<ResourceLocation, BakedModel> map, ResourceLocation id) {
        BakedModel model = map.get(new ModelResourceLocation(id, "inventory"));
        CustomModel newModel = new CustomModel(model, new SkinItemOverrides());
        map.replace(new ModelResourceLocation(id, "inventory"), newModel);
    }
}
