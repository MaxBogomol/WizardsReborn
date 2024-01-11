<recipetype:wizards_reborn:arcanum_dust_transmutation>.addRecipe("zs_test_1", <item:minecraft:netherite_ingot>, <item:minecraft:diorite>, false);
<recipetype:wizards_reborn:arcanum_dust_transmutation>.addRecipe("zs_test_2", <item:minecraft:diamond_block>, <item:minecraft:dirt>);
<recipetype:wizards_reborn:arcanum_dust_transmutation>.addRecipe("zs_test_3", <item:minecraft:grass_block>, <tag:items:forge:gems>, true);
<recipetype:wizards_reborn:arcanum_dust_transmutation>.addRecipe("zs_test_4", <item:minecraft:grass_block>, <tag:items:forge:gems>, false, <item:minecraft:grass_block>);

<recipetype:wizards_reborn:wissen_altar>.addRecipe("zs_test_5", <item:minecraft:diamond>, 500);
<recipetype:wizards_reborn:wissen_altar>.addRecipe("zs_test_6", <item:minecraft:dirt>, 50);
<recipetype:wizards_reborn:wissen_altar>.addRecipe("zs_test_7", <item:minecraft:diorite>, 150);

<recipetype:wizards_reborn:wissen_crystallizer>.addRecipe("zs_test_8", <item:minecraft:diamond>, 150, <item:minecraft:stick>, <item:minecraft:dirt>, <item:minecraft:dirt>, <item:minecraft:dirt>, <item:minecraft:dirt>);
<recipetype:wizards_reborn:wissen_crystallizer>.addRecipe("zs_test_9", <item:minecraft:diamond>*5, 150, <item:minecraft:stick>, <item:minecraft:dirt>, <item:minecraft:dirt>, <item:minecraft:dirt>, <item:minecraft:dirt>, <item:minecraft:dirt>, <item:minecraft:dirt>, <item:minecraft:dirt>, <item:minecraft:dirt>);
<recipetype:wizards_reborn:wissen_crystallizer>.addRecipe("zs_test_10", <item:minecraft:diamond>*10, 1500, <item:minecraft:stick>, <item:minecraft:dirt>, <item:minecraft:dirt>, <item:minecraft:dirt>, <item:minecraft:dirt>, <item:minecraft:dirt>, <item:minecraft:dirt>, <item:minecraft:dirt>, <item:minecraft:dirt>, <item:minecraft:dirt>, <item:minecraft:dirt>);

<recipetype:wizards_reborn:arcane_workbench>.addRecipe("zs_test_11", <item:minecraft:diamond>*2, 2000,
<item:minecraft:dirt>, <item:minecraft:dirt>, <item:minecraft:dirt>,
<item:minecraft:dirt>, <item:minecraft:dirt>, <item:minecraft:dirt>,
<item:minecraft:dirt>, <item:minecraft:dirt>, <item:minecraft:dirt>,
<item:minecraft:stick>, <item:minecraft:stick>, <item:minecraft:stick>, <item:minecraft:stick>);

<recipetype:wizards_reborn:mortar>.addRecipe("zs_test_12", <item:minecraft:diamond_block>, <item:minecraft:dirt>);

<recipetype:wizards_reborn:wissen_crystallizer>.remove(<item:wizards_reborn:white_arcane_lumos>);

<recipetype:wizards_reborn:jeweler_table>.addRecipe("zs_test_13", <item:minecraft:diamond_block>, 100, <item:minecraft:dirt>, <item:minecraft:stick>);
<recipetype:wizards_reborn:jeweler_table>.addRecipe("zs_test_14", <item:minecraft:wooden_sword>, 100, true, <item:minecraft:diamond_sword>, <item:minecraft:stick>);

<recipetype:wizards_reborn:censer>.addRecipe("zs_test_15", <item:minecraft:diamond_block>, [<mobEffect:minecraft:strength>], [10], [0]);

//<recipetype:wizards_reborn:censer>.remove(<item:minecraft:diamond_block>);

<recipetype:wizards_reborn:alchemy_machine>.addRecipe("zs_test_16", <item:minecraft:diamond_block>, null, null, 100, 100, [<item:minecraft:dirt>], [<fluid:minecraft:water> * 50], null);
<recipetype:wizards_reborn:alchemy_machine>.remove(<item:minecraft:diamond_block>);
<recipetype:wizards_reborn:alchemy_machine>.remove(<fluid:minecraft:lava> * 750);
<recipetype:wizards_reborn:alchemy_machine>.addRecipe("zs_test_17", <item:minecraft:diamond_block>, <fluid:minecraft:lava> * 345, null, 100, 100, [<item:minecraft:dirt>, <item:minecraft:dirt>], [<fluid:minecraft:water> * 50], null);
<recipetype:wizards_reborn:alchemy_machine>.addRecipe("zs_test_18", <item:minecraft:diamond_block>, null, null, 100, 100, [<item:minecraft:diamond>, <item:minecraft:diamond>, <tag:items:wizards_reborn:alchemy_potions>], [], "wizards_reborn:water");
<recipetype:wizards_reborn:alchemy_machine>.addRecipe("zs_test_19", null, null, "wizards_reborn:mor_brew", 100, 100, [<item:minecraft:diamond>, <item:minecraft:dirt>, <tag:items:wizards_reborn:alchemy_potions>], [], "wizards_reborn:mundane_brew");

<recipetype:wizards_reborn:arcane_iterator>.addRecipe("zs_test_20", <item:minecraft:diamond_block>, null, null, 10, 1, 1, false, [<item:minecraft:dirt>]);
<recipetype:wizards_reborn:arcane_iterator>.addRecipe("zs_test_21", <item:minecraft:diamond_sword>, <enchantment:minecraft:sharpness>, null, 100, 1, 1, false, [<item:minecraft:diamond_block>, <item:minecraft:diamond_block>, <item:minecraft:diamond_block>]);
<recipetype:wizards_reborn:arcane_iterator>.addRecipe("zs_test_22", <item:wizards_reborn:arcane_gold_scythe>, null, "wizards_reborn:magic_blade", 100, 1, 1, false, [<item:minecraft:diamond_block>, <item:minecraft:diamond_block>, <item:minecraft:diamond_block>, <item:minecraft:diamond_block>]);
//<recipetype:wizards_reborn:arcane_iterator>.remove("wizards_reborn:magic_blade");
//<recipetype:wizards_reborn:arcane_iterator>.remove(<enchantment:minecraft:sharpness>);

<recipetype:wizards_reborn:arcane_workbench>.addRecipe("zs_test_23", <item:minecraft:diamond_sword>, 2000, 0, <item:minecraft:wooden_sword>);
<recipetype:wizards_reborn:wissen_crystallizer>.addRecipe("zs_test_24", <item:minecraft:diamond_sword>, 100, false, true, <item:minecraft:wooden_sword>, <item:minecraft:diamond>);