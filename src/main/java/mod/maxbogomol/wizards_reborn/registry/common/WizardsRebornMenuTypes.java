package mod.maxbogomol.wizards_reborn.registry.common;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.gui.screen.*;
import mod.maxbogomol.wizards_reborn.common.gui.menu.*;
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

    public static final RegistryObject<MenuType<ArcaneWorkbenchMenu>> ARCANE_WORKBENCH_CONTAINER = MENU_TYPES.register("arcane_workbench",
            () -> IForgeMenuType.create(((containerId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level level = inv.player.getCommandSenderWorld();
                return new ArcaneWorkbenchMenu(containerId, level, pos, inv, inv.player);})));

    public static final RegistryObject<MenuType<JewelerTableMenu>> JEWELER_TABLE_CONTAINER = MENU_TYPES.register("jeweler_table",
            () -> IForgeMenuType.create(((containerId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level level = inv.player.getCommandSenderWorld();
                return new JewelerTableMenu(containerId, level, pos, inv, inv.player);})));

    public static final RegistryObject<MenuType<TotemOfDisenchantMenu>> TOTEM_OF_DISENCHANT_CONTAINER = MENU_TYPES.register("totem_of_disenchant",
            () -> IForgeMenuType.create(((containerId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level level = inv.player.getCommandSenderWorld();
                return new TotemOfDisenchantMenu(containerId, level, pos, inv, inv.player);})));

    public static final RegistryObject<MenuType<AlchemyFurnaceMenu>> ALCHEMY_FURNACE_CONTAINER = MENU_TYPES.register("alchemy_furnace",
            () -> IForgeMenuType.create(((containerId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level level = inv.player.getCommandSenderWorld();
                return new AlchemyFurnaceMenu(containerId, level, pos, inv, inv.player);})));

    public static final RegistryObject<MenuType<AlchemyMachineMenu>> ALCHEMY_MACHINE_CONTAINER = MENU_TYPES.register("alchemy_machine",
            () -> IForgeMenuType.create(((containerId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level level = inv.player.getCommandSenderWorld();
                return new AlchemyMachineMenu(containerId, level, pos, inv, inv.player);})));

    public static final RegistryObject<MenuType<KegMenu>> KEG_CONTAINER = MENU_TYPES.register("keg",
            () -> IForgeMenuType.create(((containerId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level level = inv.player.getCommandSenderWorld();
                return new KegMenu(containerId, level, pos, inv, inv.player);})));

    public static final RegistryObject<MenuType<RunicPedestalMenu>> RUNIC_PEDESTAL_CONTAINER = MENU_TYPES.register("runic_pedestal",
            () -> IForgeMenuType.create(((containerId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level level = inv.player.getCommandSenderWorld();
                return new RunicPedestalMenu(containerId, level, pos, inv, inv.player);})));

    public static final RegistryObject<MenuType<ArcaneHopperMenu>> ARCANE_HOPPER_CONTAINER = MENU_TYPES.register("arcane_hopper",
            () -> IForgeMenuType.create(((containerId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level level = inv.player.getCommandSenderWorld();
                return new ArcaneHopperMenu(containerId, level, pos, inv, inv.player);})));

    public static final RegistryObject<MenuType<ItemSorterMenu>> ITEM_SORTER_CONTAINER = MENU_TYPES.register("item_sorter",
            () -> IForgeMenuType.create(((containerId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level level = inv.player.getCommandSenderWorld();
                return new ItemSorterMenu(containerId, level, pos, inv, inv.player);})));

    public static final RegistryObject<MenuType<CrystalBagMenu>> CRYSTAL_BAG_CONTAINER = MENU_TYPES.register("crystal_bag",
            () -> IForgeMenuType.create(((containerId, inv, data) -> {
                Level level = inv.player.getCommandSenderWorld();
                return new CrystalBagMenu(containerId, level, data.readItem(), inv, inv.player);})));

    public static final RegistryObject<MenuType<AlchemyBagMenu>> ALCHEMY_BAG_CONTAINER = MENU_TYPES.register("alchemy_bag",
            () -> IForgeMenuType.create(((containerId, inv, data) -> {
                Level level = inv.player.getCommandSenderWorld();
                return new AlchemyBagMenu(containerId, level, data.readItem(), inv, inv.player);})));

    public static final RegistryObject<MenuType<CargoCarpetMenu>> CARGO_CARPET_CONTAINER = MENU_TYPES.register("cargo_carpet",
            () -> IForgeMenuType.create(((containerId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level level = inv.player.getCommandSenderWorld();
                return new CargoCarpetMenu(containerId, level, pos, inv, inv.player);})));

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ARCANE_WORKBENCH_CONTAINER.get(), ArcaneWorkbenchScreen::new);
            MenuScreens.register(JEWELER_TABLE_CONTAINER.get(), JewelerTableScreen::new);
            MenuScreens.register(TOTEM_OF_DISENCHANT_CONTAINER.get(), TotemOfDisenchantScreen::new);
            MenuScreens.register(ALCHEMY_FURNACE_CONTAINER.get(), AlchemyFurnaceScreen::new);
            MenuScreens.register(ALCHEMY_MACHINE_CONTAINER.get(), AlchemyMachineScreen::new);
            MenuScreens.register(KEG_CONTAINER.get(), KegScreen::new);
            MenuScreens.register(RUNIC_PEDESTAL_CONTAINER.get(), RunicPedestalScreen::new);
            MenuScreens.register(ARCANE_HOPPER_CONTAINER.get(), ArcaneHopperScreen::new);
            MenuScreens.register(ITEM_SORTER_CONTAINER.get(), ItemSorterScreen::new);
            MenuScreens.register(CRYSTAL_BAG_CONTAINER.get(), CrystalBagScreen::new);
            MenuScreens.register(ALCHEMY_BAG_CONTAINER.get(), AlchemyBagScreen::new);
            MenuScreens.register(CARGO_CARPET_CONTAINER.get(), CargoCarpetScreen::new);
        }
    }
}
