package com.github.masyu.enchant.tab;

import com.github.masyu.enchant.regi.ModEnchantment;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

public class EnchantMain {

    public static final ItemStack[] items = {

            EnchantedBookItem.createForEnchantment(
                    new EnchantmentInstance(
                            ModEnchantment.UNBREAKABLE.get(),
                            1
                    )
            )
    };
}
