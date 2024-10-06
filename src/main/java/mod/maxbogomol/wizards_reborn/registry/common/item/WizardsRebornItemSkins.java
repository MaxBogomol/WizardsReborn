package mod.maxbogomol.wizards_reborn.registry.common.item;

import mod.maxbogomol.fluffy_fur.client.model.item.BowSkinItemOverrides;
import mod.maxbogomol.fluffy_fur.client.model.item.ItemSkinItemOverrides;
import mod.maxbogomol.fluffy_fur.client.model.item.ItemSkinModels;
import mod.maxbogomol.fluffy_fur.client.render.item.LargeItemRenderer;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkinHandler;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurModels;
import mod.maxbogomol.fluffy_fur.registry.common.item.FluffyFurItemSkins;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.model.item.WandCrystalsModels;
import mod.maxbogomol.wizards_reborn.common.itemskin.*;
import mod.maxbogomol.wizards_reborn.integration.common.farmersdelight.FarmersDelightIntegration;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.util.Map;

public class WizardsRebornItemSkins {
    public static ItemSkin TOP_HAT = new TopHatSkin(WizardsReborn.MOD_ID+":top_hat", new Color(54, 60, 81));
    public static ItemSkin SOUL_HUNTER = new SoulHunterSkin(WizardsReborn.MOD_ID+":soul_hunter", new Color(225, 99, 226));
    public static ItemSkin IMPLOSION = new ImplosionSkin(WizardsReborn.MOD_ID+":implosion", new Color(149, 237, 255));
    public static ItemSkin PHANTOM_INK = new PhantomInkSkin(WizardsReborn.MOD_ID+":phantom_ink", new Color(189, 237, 255));
    public static ItemSkin MAGNIFICENT_MAID = new MagnificentMaidSkin(WizardsReborn.MOD_ID+":magnificent_maid", new Color(153, 168, 184));
    public static ItemSkin SUMMER_LOVE = new SummerLoveSkin(WizardsReborn.MOD_ID+":summer_love", new Color(243, 181, 127));

    public static void register() {
        ItemSkinHandler.register(TOP_HAT);
        ItemSkinHandler.register(SOUL_HUNTER);
        ItemSkinHandler.register(IMPLOSION);
        ItemSkinHandler.register(PHANTOM_INK);
        ItemSkinHandler.register(MAGNIFICENT_MAID);
        ItemSkinHandler.register(SUMMER_LOVE);

        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            registerModels();
            return new Object();
        });
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerModels() {
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":top_hat");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":soul_hunter_hood");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":soul_hunter_costume");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":soul_hunter_trousers");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":soul_hunter_boots");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":soul_hunter_scythe");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":soul_hunter_arcane_wand");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":soul_hunter_wissen_wand");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":implosion_sword");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":implosion_pickaxe");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":implosion_axe");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":implosion_shovel");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":implosion_hoe");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":implosion_scythe");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":implosion_knife");
        ItemSkinModels.addBowSkin(WizardsReborn.MOD_ID+":implosion_bow");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":implosion_arcane_wand");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":implosion_wissen_wand");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":magnificent_maid_headwear");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":magnificent_maid_suit");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":magnificent_maid_stockings");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":magnificent_maid_boots");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":magnificent_maid_arcane_wand");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":magnificent_maid_wissen_wand");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":summer_love_flower");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":summer_love_dress");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":summer_love_boots");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":summer_love_arcane_wand");
        ItemSkinModels.addSkin(WizardsReborn.MOD_ID+":summer_love_wissen_wand");
    }

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void modelRegistrySkins(ModelEvent.RegisterAdditional event) {
            event.register(LargeItemRenderer.getModelResourceLocation(WizardsReborn.MOD_ID, "skin/soul_hunter_scythe"));
            event.register(LargeItemRenderer.getModelResourceLocation(WizardsReborn.MOD_ID, "skin/implosion_scythe"));
        }

        @SubscribeEvent
        public static void modelBakeSkins(ModelEvent.ModifyBakingResult event) {
            Map<ResourceLocation, BakedModel> map = event.getModels();

            WandCrystalsModels.addWandItem(map, new ResourceLocation(WizardsReborn.MOD_ID, "skin/soul_hunter_arcane_wand"));
            WandCrystalsModels.addWandItem(map, new ResourceLocation(WizardsReborn.MOD_ID, "skin/implosion_arcane_wand"));
            WandCrystalsModels.addWandItem(map, new ResourceLocation(WizardsReborn.MOD_ID, "skin/magnificent_maid_arcane_wand"));
            WandCrystalsModels.addWandItem(map, new ResourceLocation(WizardsReborn.MOD_ID, "skin/summer_love_arcane_wand"));

            FluffyFurItemSkins.addLargeModel(map, WizardsReborn.MOD_ID, "soul_hunter_scythe");
            FluffyFurItemSkins.addLargeModel(map, WizardsReborn.MOD_ID, "implosion_scythe");

            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.WISSEN_WAND.getId());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.ARCANE_WOOD_SWORD.getId());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.INNOCENT_WOOD_SWORD.getId());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.ARCANE_GOLD_SWORD.getId());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.ARCANE_WOOD_PICKAXE.getId());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.INNOCENT_WOOD_PICKAXE.getId());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.ARCANE_GOLD_PICKAXE.getId());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.ARCANE_WOOD_AXE.getId());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.INNOCENT_WOOD_AXE.getId());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.ARCANE_GOLD_AXE.getId());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.ARCANE_WOOD_SHOVEL.getId());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.INNOCENT_WOOD_SHOVEL.getId());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.ARCANE_GOLD_SHOVEL.getId());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.ARCANE_WOOD_HOE.getId());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.INNOCENT_WOOD_HOE.getId());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.ARCANE_GOLD_HOE.getId());
            LargeItemRenderer.bakeModel(map, WizardsReborn.MOD_ID, "arcane_wood_scythe", new ItemSkinItemOverrides());
            LargeItemRenderer.bakeModel(map, WizardsReborn.MOD_ID, "innocent_wood_scythe", new ItemSkinItemOverrides());
            LargeItemRenderer.bakeModel(map, WizardsReborn.MOD_ID, "arcane_gold_scythe", new ItemSkinItemOverrides());
            FluffyFurModels.addBowItemModel(map, WizardsRebornItems.ARCANE_WOOD_BOW.getId(), new BowSkinItemOverrides());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.ARCANE_FORTRESS_HELMET.getId());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.ARCANE_FORTRESS_CHESTPLATE.getId());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.ARCANE_FORTRESS_LEGGINGS.getId());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.ARCANE_FORTRESS_BOOTS.getId());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.INVENTOR_WIZARD_HAT.getId());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.INVENTOR_WIZARD_COSTUME.getId());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.INVENTOR_WIZARD_TROUSERS.getId());
            FluffyFurItemSkins.addSkinModel(map, WizardsRebornItems.INVENTOR_WIZARD_BOOTS.getId());

            if (FarmersDelightIntegration.isLoaded()) {
                FluffyFurItemSkins.addSkinModel(map, FarmersDelightIntegration.ARCANE_GOLD_KNIFE.getId());
                FluffyFurItemSkins.addSkinModel(map, FarmersDelightIntegration.ARCANE_WOOD_KNIFE.getId());
                FluffyFurItemSkins.addSkinModel(map, FarmersDelightIntegration.INNOCENT_WOOD_KNIFE.getId());
            }
        }
    }
}
