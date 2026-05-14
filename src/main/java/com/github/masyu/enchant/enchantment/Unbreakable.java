package com.github.masyu.enchant.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class Unbreakable extends Enchantment {
    public Unbreakable() {
        super(
                Rarity.VERY_RARE,
                EnchantmentCategory.BREAKABLE,
                EquipmentSlot.values()
        );
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isTradeable() {
        return true;
    }

    @Override
    public boolean isDiscoverable() {
        return true;
    }

    @Override
    public int getMinCost(int level) {
        return 30;
    }

    @Override
    public int getMaxCost(int level) {
        return 30;
    }
}
