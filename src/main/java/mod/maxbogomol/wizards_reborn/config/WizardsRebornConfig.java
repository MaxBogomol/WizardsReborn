package mod.maxbogomol.wizards_reborn.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;

public class WizardsRebornConfig {
    public static ForgeConfigSpec.ConfigValue<Boolean>
            ARCANEMICON_OFFERING;
    public static ForgeConfigSpec.ConfigValue<Integer>
            STANDARD_WISSEN_COLOR_R, STANDARD_WISSEN_COLOR_G, STANDARD_WISSEN_COLOR_B,
            ARCANEMICON_OFFERING_TICKS;
    public static ForgeConfigSpec.ConfigValue<Double>
            SPELL_PROJECTILE_DAMAGE, EARTH_PROJECTILE_DAMAGE, WATER_PROJECTILE_DAMAGE, AIR_PROJECTILE_DAMAGE, FIRE_PROJECTILE_DAMAGE, VOID_PROJECTILE_DAMAGE, FROST_PROJECTILE_DAMAGE, HOLY_PROJECTILE_DAMAGE, CURSE_PROJECTILE_DAMAGE, ICICLE_DAMAGE,
            SPELL_RAY_DAMAGE, EARTH_RAY_DAMAGE, WATER_RAY_DAMAGE, AIR_RAY_DAMAGE, FIRE_RAY_DAMAGE, VOID_RAY_DAMAGE, FROST_RAY_DAMAGE, HOLY_RAY_DAMAGE, CURSE_RAY_DAMAGE, NECROTIC_RAY_DAMAGE, EMBER_RAY_DAMAGE,
            SPELL_CHARGE_DAMAGE, EARTH_CHARGE_DAMAGE, WATER_CHARGE_DAMAGE, AIR_CHARGE_DAMAGE, FIRE_CHARGE_DAMAGE, VOID_CHARGE_DAMAGE, FROST_CHARGE_DAMAGE, HOLY_CHARGE_DAMAGE, CURSE_CHARGE_DAMAGE,
            SPELL_AURA_DAMAGE, EARTH_AURA_DAMAGE, WATER_AURA_DAMAGE, AIR_AURA_DAMAGE, FIRE_AURA_DAMAGE, VOID_AURA_DAMAGE, FROST_AURA_DAMAGE, HOLY_AURA_DAMAGE, CURSE_AURA_DAMAGE;

    public WizardsRebornConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Wissen").push("wissen");
        STANDARD_WISSEN_COLOR_R = builder.comment("Standard wissen color RED.").defineInRange("standardWissenColorR", 119, 0, 255);
        STANDARD_WISSEN_COLOR_G = builder.comment("Standard wissen color GREEN.").defineInRange("standardWissenColorG", 164, 0, 255);
        STANDARD_WISSEN_COLOR_B = builder.comment("Standard wissen color BLUE.").defineInRange("standardWissenColorB", 208, 0, 255);
        builder.pop();

        ARCANEMICON_OFFERING = builder.comment("Enable Arcanemicon Offering").define("arcanemiconOffering", true);
        ARCANEMICON_OFFERING_TICKS = builder.comment("Ticks with game for Arcanemicon Offering.").defineInRange("arcanemiconOfferingTicks", 144000, 0, Integer.MAX_VALUE);

        builder.comment("Spells").push("spells");

        builder.comment("Projectile").push("projectile");
        SPELL_PROJECTILE_DAMAGE = builder.comment("Damage of all Projectile spells.").define("spellProjectileDamage", 0d);
        EARTH_PROJECTILE_DAMAGE = builder.comment("Damage of Earth Projectile spell.").define("earthProjectileDamage", 0d);
        WATER_PROJECTILE_DAMAGE = builder.comment("Damage of Water Projectile spell.").define("waterProjectileDamage", 0d);
        AIR_PROJECTILE_DAMAGE = builder.comment("Damage of Air Projectile spell.").define("airProjectileDamage", 0d);
        FIRE_PROJECTILE_DAMAGE = builder.comment("Damage of Fire Projectile spell.").define("fireProjectileDamage", 0d);
        VOID_PROJECTILE_DAMAGE = builder.comment("Damage of Void Projectile spell.").define("voidProjectileDamage", 0d);
        FROST_PROJECTILE_DAMAGE = builder.comment("Damage of Frost Projectile spell.").define("frostProjectileDamage", 0d);
        HOLY_PROJECTILE_DAMAGE = builder.comment("Damage of Holy Projectile spell.").define("holyProjectileDamage", 0d);
        CURSE_PROJECTILE_DAMAGE = builder.comment("Damage of Curse Projectile spell.").define("curseProjectileDamage", 0d);
        ICICLE_DAMAGE = builder.comment("Damage of Icicle spell.").define("icicleDamage", 0d);
        builder.pop();

        builder.comment("Ray").push("ray");
        SPELL_RAY_DAMAGE = builder.comment("Damage of all Ray spells.").define("spellRayDamage", 0d);
        EARTH_RAY_DAMAGE = builder.comment("Damage of Earth Ray spell.").define("earthRayDamage", 0d);
        WATER_RAY_DAMAGE = builder.comment("Damage of Water Ray spell.").define("waterRayDamage", 0d);
        AIR_RAY_DAMAGE = builder.comment("Damage of Air Ray spell.").define("airRayDamage", 0d);
        FIRE_RAY_DAMAGE = builder.comment("Damage of Fire Ray spell.").define("fireRayDamage", 0d);
        VOID_RAY_DAMAGE = builder.comment("Damage of Void Ray spell.").define("voidRayDamage", 0d);
        FROST_RAY_DAMAGE = builder.comment("Damage of Frost Ray spell.").define("frostRayDamage", 0d);
        HOLY_RAY_DAMAGE = builder.comment("Damage of Holy Ray spell.").define("holyRayDamage", 0d);
        CURSE_RAY_DAMAGE = builder.comment("Damage of Curse Ray spell.").define("curseRayDamage", 0d);
        NECROTIC_RAY_DAMAGE = builder.comment("Damage of Necrotic Ray spell.").define("necroticRayDamage", 0d);
        EMBER_RAY_DAMAGE = builder.comment("Damage of Ember Ray spell.").define("emberRayDamage", 0d);
        builder.pop();

        builder.comment("Charge").push("charge");
        SPELL_CHARGE_DAMAGE = builder.comment("Damage of all Charge spells.").define("spellChargeDamage", 0d);
        EARTH_CHARGE_DAMAGE = builder.comment("Damage of Earth Charge spell.").define("earthChargeDamage", 0d);
        WATER_CHARGE_DAMAGE = builder.comment("Damage of Water Charge spell.").define("waterChargeDamage", 0d);
        AIR_CHARGE_DAMAGE = builder.comment("Damage of Air Charge spell.").define("airChargeDamage", 0d);
        FIRE_CHARGE_DAMAGE = builder.comment("Damage of Fire Charge spell.").define("fireChargeDamage", 0d);
        VOID_CHARGE_DAMAGE = builder.comment("Damage of Void Charge spell.").define("voidChargeDamage", 0d);
        FROST_CHARGE_DAMAGE = builder.comment("Damage of Frost Charge spell.").define("frostChargeDamage", 0d);
        HOLY_CHARGE_DAMAGE = builder.comment("Damage of Holy Charge spell.").define("holyChargeDamage", 0d);
        CURSE_CHARGE_DAMAGE = builder.comment("Damage of Curse Charge spell.").define("curseChargeDamage", 0d);
        builder.pop();

        builder.comment("Aura").push("aura");
        SPELL_AURA_DAMAGE = builder.comment("Damage of all Aura spells.").define("spellAuraDamage", 0d);
        EARTH_AURA_DAMAGE = builder.comment("Damage of Earth Aura spell.").define("earthAuraDamage", 0d);
        WATER_AURA_DAMAGE = builder.comment("Damage of Water Aura spell.").define("waterAuraDamage", 0d);
        AIR_AURA_DAMAGE = builder.comment("Damage of Air Aura spell.").define("airAuraDamage", 0d);
        FIRE_AURA_DAMAGE = builder.comment("Damage of Fire Aura spell.").define("fireAuraDamage", 0d);
        VOID_AURA_DAMAGE = builder.comment("Damage of Void Aura spell.").define("voidAuraDamage", 0d);
        FROST_AURA_DAMAGE = builder.comment("Damage of Frost Aura spell.").define("frostAuraDamage", 0d);
        HOLY_AURA_DAMAGE = builder.comment("Damage of Holy Aura spell.").define("holyAuraDamage", 0d);
        CURSE_AURA_DAMAGE = builder.comment("Damage of Curse Aura spell.").define("curseAuraDamage", 0d);
        builder.pop();

        builder.pop();
    }

    public static final WizardsRebornConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<WizardsRebornConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(WizardsRebornConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }

    public static float wissenColorR() {
        return STANDARD_WISSEN_COLOR_R.get() / 255f;
    }

    public static float wissenColorG() {
        return STANDARD_WISSEN_COLOR_G.get() / 255f;
    }

    public static float wissenColorB() {
        return STANDARD_WISSEN_COLOR_B.get() / 255f;
    }

    public static Color wissenColor() {
        return new Color(wissenColorR(), wissenColorG(), wissenColorB());
    }
}
