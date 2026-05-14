package com.github.masyu.enchant.mixin;

import com.github.masyu.enchant.regi.ModEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    /**
     * @author you
     * @reason Increase enchant max level
     */
    @Overwrite
    public int getMaxLevel() {

        Enchantment self = (Enchantment)(Object)this;

        if (self == ModEnchantment.UNBREAKABLE.get()) {
            return 1;
        }

        return 10;
    }

    /**
     * エンチャテーブル高レベル化
     */
    @Overwrite
    public int getMaxCost(int level) {

        Enchantment self = (Enchantment)(Object)this;

        return self.getMinCost(level) + 50;
    }
}