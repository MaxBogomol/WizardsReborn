package mod.maxbogomol.wizards_reborn.registry.common;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.gui.container.*;
import mod.maxbogomol.wizards_reborn.client.gui.screen.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WizardsRebornMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, WizardsReborn.MOD_ID);

    public static final RegistryObject<MenuType<ArcaneWorkbenchContainer>> ARCANE_WORKBENCH_CONTAINER = MENU_TYPES.register("arcane_workbench",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level level = inv.player.getCommandSenderWorld();
                return new ArcaneWorkbenchContainer(windowId, level, pos, inv, inv.player);})));

    public static final RegistryObject<MenuType<JewelerTableContainer>> JEWELER_TABLE_CONTAINER = MENU_TYPES.register("jeweler_table",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level level = inv.player.getCommandSenderWorld();
                return new JewelerTableContainer(windowId, level, pos, inv, inv.player);})));

    public static final RegistryObject<MenuType<AlchemyFurnaceContainer>> ALCHEMY_FURNACE_CONTAINER = MENU_TYPES.register("alchemy_furnace",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level level = inv.player.getCommandSenderWorld();
                return new AlchemyFurnaceContainer(windowId, level, pos, inv, inv.player);})));

    public static final RegistryObject<MenuType<AlchemyMachineContainer>> ALCHEMY_MACHINE_CONTAINER = MENU_TYPES.register("alchemy_machine",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level level = inv.player.getCommandSenderWorld();
                return new AlchemyMachineContainer(windowId, level, pos, inv, inv.player);})));

    public static final RegistryObject<MenuType<ArcaneHopperContainer>> ARCANE_HOPPER_CONTAINER = MENU_TYPES.register("arcane_hopper",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level level = inv.player.getCommandSenderWorld();
                return new ArcaneHopperContainer(windowId, level, pos, inv, inv.player);})));

    public static final RegistryObject<MenuType<ItemSorterContainer>> ITEM_SORTER_CONTAINER = MENU_TYPES.register("item_sorter",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level level = inv.player.getCommandSenderWorld();
                return new ItemSorterContainer(windowId, level, pos, inv, inv.player);})));

    public static final RegistryObject<MenuType<TotemOfDisenchantContainer>> TOTEM_OF_DISENCHANT_CONTAINER = MENU_TYPES.register("totem_of_disenchant",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level level = inv.player.getCommandSenderWorld();
                return new TotemOfDisenchantContainer(windowId, level, pos, inv, inv.player);})));

    public static final RegistryObject<MenuType<RunicPedestalContainer>> RUNIC_PEDESTAL_CONTAINER = MENU_TYPES.register("runic_pedestal",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level level = inv.player.getCommandSenderWorld();
                return new RunicPedestalContainer(windowId, level, pos, inv, inv.player);})));

    public static final RegistryObject<MenuType<CrystalBagContainer>> CRYSTAL_BAG_CONTAINER = MENU_TYPES.register("crystal_bag",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                Level level = inv.player.getCommandSenderWorld();
                return new CrystalBagContainer(windowId, level, data.readItem(), inv, inv.player);})));

    public static final RegistryObject<MenuType<AlchemyBagContainer>> ALCHEMY_BAG_CONTAINER = MENU_TYPES.register("alchemy_bag",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                Level level = inv.player.getCommandSenderWorld();
                return new AlchemyBagContainer(windowId, level, data.readItem(), inv, inv.player);})));

    public static final RegistryObject<MenuType<CargoCarpetContainer>> CARGO_CARPET_CONTAINER = MENU_TYPES.register("cargo_carpet",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level level = inv.player.getCommandSenderWorld();
                return new CargoCarpetContainer(windowId, level, pos, inv, inv.player);})));

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ARCANE_WORKBENCH_CONTAINER.get(), ArcaneWorkbenchScreen::new);
            MenuScreens.register(JEWELER_TABLE_CONTAINER.get(), JewelerTableScreen::new);
            MenuScreens.register(ALCHEMY_FURNACE_CONTAINER.get(), AlchemyFurnaceScreen::new);
            MenuScreens.register(ALCHEMY_MACHINE_CONTAINER.get(), AlchemyMachineScreen::new);
            MenuScreens.register(ARCANE_HOPPER_CONTAINER.get(), ArcaneHopperScreen::new);
            MenuScreens.register(ITEM_SORTER_CONTAINER.get(), ItemSorterScreen::new);
            MenuScreens.register(TOTEM_OF_DISENCHANT_CONTAINER.get(), TotemOfDisenchantScreen::new);
            MenuScreens.register(RUNIC_PEDESTAL_CONTAINER.get(), RunicPedestalScreen::new);
            MenuScreens.register(CRYSTAL_BAG_CONTAINER.get(), CrystalBagScreen::new);
            MenuScreens.register(ALCHEMY_BAG_CONTAINER.get(), AlchemyBagScreen::new);
            MenuScreens.register(CARGO_CARPET_CONTAINER.get(), CargoCarpetScreen::new);
        }
    }
}
