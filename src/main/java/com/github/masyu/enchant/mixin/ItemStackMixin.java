package com.github.masyu.enchant.mixin;

import com.github.masyu.enchant.regi.ModEnchantment;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "hurtAndBreak", at = @At("HEAD"), cancellable = true)
    private void preventBreaking(
            int amount,
            net.minecraft.world.entity.LivingEntity entity,
            java.util.function.Consumer<?> consumer,
            CallbackInfo ci
    ) {

        ItemStack stack = (ItemStack)(Object)this;

        if (EnchantmentHelper.getItemEnchantmentLevel(
                ModEnchantment.UNBREAKABLE.get(),
                stack
        ) > 0) {

            ci.cancel();
        }
    }
}