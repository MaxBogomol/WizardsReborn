package mod.maxbogomol.wizards_reborn.registry.common.entity;

import mod.maxbogomol.fluffy_fur.client.render.entity.CustomBoatRenderer;
import mod.maxbogomol.fluffy_fur.common.entity.CustomBoatEntity;
import mod.maxbogomol.fluffy_fur.common.entity.CustomChestBoatEntity;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.render.entity.*;
import mod.maxbogomol.wizards_reborn.common.entity.*;
import mod.maxbogomol.wizards_reborn.integration.common.farmers_delight.WizardsRebornFarmersDelight;
import mod.maxbogomol.wizards_reborn.integration.common.farmers_delight.KnifeInnocentSparkType;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WizardsRebornEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, WizardsReborn.MOD_ID);

    public static final RegistryObject<EntityType<CustomBoatEntity>> ARCANE_WOOD_BOAT = ENTITIES.register("arcane_wood_boat", () -> EntityType.Builder.<CustomBoatEntity>of((t, l) -> (new CustomBoatEntity(t, l, WizardsRebornItems.ARCANE_WOOD_BOAT, false)), MobCategory.MISC).sized(1.375f, 0.5625f).build(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_boat").toString()));
    public static final RegistryObject<EntityType<CustomChestBoatEntity>> ARCANE_WOOD_CHEST_BOAT = ENTITIES.register("arcane_wood_chest_boat", () -> EntityType.Builder.<CustomChestBoatEntity>of((t, l) -> (new CustomChestBoatEntity(t, l, WizardsRebornItems.ARCANE_WOOD_CHEST_BOAT, false)), MobCategory.MISC).sized(1.375f, 0.5625f).build(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_chest_boat").toString()));
    public static final RegistryObject<EntityType<CustomBoatEntity>> INNOCENT_WOOD_BOAT = ENTITIES.register("innocent_wood_boat", () -> EntityType.Builder.<CustomBoatEntity>of((t, l) -> (new CustomBoatEntity(t, l, WizardsRebornItems.INNOCENT_WOOD_BOAT, false)), MobCategory.MISC).sized(1.375f, 0.5625f).build(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_boat").toString()));
    public static final RegistryObject<EntityType<CustomChestBoatEntity>> INNOCENT_WOOD_CHEST_BOAT = ENTITIES.register("innocent_wood_chest_boat", () -> EntityType.Builder.<CustomChestBoatEntity>of((t, l) -> (new CustomChestBoatEntity(t, l, WizardsRebornItems.INNOCENT_WOOD_CHEST_BOAT, false)), MobCategory.MISC).sized(1.375f, 0.5625f).build(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_chest_boat").toString()));
    public static final RegistryObject<EntityType<CustomBoatEntity>> CORK_BAMBOO_RAFT = ENTITIES.register("cork_bamboo_raft", () -> EntityType.Builder.<CustomBoatEntity>of((t, l) -> (new CustomBoatEntity(t, l, WizardsRebornItems.CORK_BAMBOO_RAFT, true)), MobCategory.MISC).sized(1.375f, 0.5625f).build(new ResourceLocation(WizardsReborn.MOD_ID, "cork_bamboo_boat").toString()));
    public static final RegistryObject<EntityType<CustomChestBoatEntity>> CORK_BAMBOO_CHEST_RAFT = ENTITIES.register("cork_bamboo_chest_raft", () -> EntityType.Builder.<CustomChestBoatEntity>of((t, l) -> (new CustomChestBoatEntity(t, l, WizardsRebornItems.CORK_BAMBOO_CHEST_RAFT, true)), MobCategory.MISC).sized(1.375f, 0.5625f).build(new ResourceLocation(WizardsReborn.MOD_ID, "cork_bamboo_chest_boat").toString()));

    public static final RegistryObject<EntityType<SpellEntity>> SPELL = ENTITIES.register("spell", () -> EntityType.Builder.<SpellEntity>of(SpellEntity::new, MobCategory.MISC).sized(0.4f, 0.4f).build(new ResourceLocation(WizardsReborn.MOD_ID, "spell_projectile").toString()));
    public static final RegistryObject<EntityType<ThrownScytheEntity>> THROWN_SCYTHE = ENTITIES.register("thrown_scythe", () -> EntityType.Builder.<ThrownScytheEntity>of(ThrownScytheEntity::new, MobCategory.MISC).sized(1.75f, 0.2f).build(new ResourceLocation(WizardsReborn.MOD_ID, "throwed_scythe").toString()));
    public static final RegistryObject<EntityType<SplitArrowEntity>> SPLIT_ARROW = ENTITIES.register("split_arrow", () -> EntityType.Builder.<SplitArrowEntity>of(SplitArrowEntity::new, MobCategory.MISC).sized(0.2f, 0.2f).build(new ResourceLocation(WizardsReborn.MOD_ID, "split_arrow").toString()));

    public static final RegistryObject<EntityType<InnocentSparkEntity>> INNOCENT_SPARK = ENTITIES.register("innocent_spark", () -> EntityType.Builder.<InnocentSparkEntity>of(InnocentSparkEntity::new, MobCategory.MISC).sized(0.4f, 0.4f).build(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_spark").toString()));

    public static final RegistryObject<EntityType<SniffaloEntity>> SNIFFALO = ENTITIES.register("sniffalo", () -> EntityType.Builder.<SniffaloEntity>of(SniffaloEntity::new, MobCategory.CREATURE).sized(1.9F, 1.75F).clientTrackingRange(10).build(new ResourceLocation(WizardsReborn.MOD_ID, "sniffalo").toString()));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerEntities(FMLCommonSetupEvent event) {
            InnocentSparkEntity.addType(new InnocentSparkEntity.SwordSparkType());
            InnocentSparkEntity.addType(new InnocentSparkEntity.PickaxeSparkType());
            InnocentSparkEntity.addType(new InnocentSparkEntity.AxeSparkType());
            InnocentSparkEntity.addType(new InnocentSparkEntity.ShovelSparkType());
            InnocentSparkEntity.addType(new InnocentSparkEntity.HoeSparkType());
            InnocentSparkEntity.addType(new InnocentSparkEntity.ScytheSparkType());
            if (WizardsRebornFarmersDelight.isLoaded()) {
                InnocentSparkEntity.addType(new KnifeInnocentSparkType());
            }
        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(SNIFFALO.get(), SniffaloEntity.createAttributes().build());
        }
    }

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            EntityRenderers.register(ARCANE_WOOD_BOAT.get(), m -> new CustomBoatRenderer(m, WizardsReborn.MOD_ID, "arcane_wood", false, false));
            EntityRenderers.register(ARCANE_WOOD_CHEST_BOAT.get(), m -> new CustomBoatRenderer(m, WizardsReborn.MOD_ID, "arcane_wood", true, false));
            EntityRenderers.register(INNOCENT_WOOD_BOAT.get(), m -> new CustomBoatRenderer(m, WizardsReborn.MOD_ID, "innocent_wood", false, false));
            EntityRenderers.register(INNOCENT_WOOD_CHEST_BOAT.get(), m -> new CustomBoatRenderer(m, WizardsReborn.MOD_ID, "innocent_wood", true, false));
            EntityRenderers.register(CORK_BAMBOO_RAFT.get(), m -> new CustomBoatRenderer(m, WizardsReborn.MOD_ID, "cork_bamboo", false, true));
            EntityRenderers.register(CORK_BAMBOO_CHEST_RAFT.get(), m -> new CustomBoatRenderer(m, WizardsReborn.MOD_ID, "cork_bamboo", true, true));
            EntityRenderers.register(SPELL.get(), SpellRenderer::new);
            EntityRenderers.register(THROWN_SCYTHE.get(), ThrownScytheRenderer::new);
            EntityRenderers.register(SPLIT_ARROW.get(), SplitArrowRenderer::new);
            EntityRenderers.register(INNOCENT_SPARK.get(), InnocentSparkRenderer::new);
            EntityRenderers.register(SNIFFALO.get(), SniffaloRenderer::new);
        }
    }
}
