package com.github.masyu.enchant.mixin;

import com.github.masyu.enchant.regi.ModEnchantment;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public class AnvilMenuMixin {

    @Shadow @Final private DataSlot cost;

    /**
     * 1. 「高すぎます！」制限（コスト40）を撤廃
     */
    @ModifyConstant(method = "createResult", constant = @Constant(intValue = 40))
    private int removeTooExpensiveLimit(int original) {
        return 1000000;
    }

    /**
     * 2. エンチャントのレベル上限を10に引き上げ（合成可能にする）
     */
    @Redirect(
            method = "createResult",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;getMaxLevel()I")
    )
    private int redirectMaxLevel(Enchantment instance) {
        if (instance == Enchantments.MENDING || instance == Enchantments.SILK_TOUCH || instance == ModEnchantment.UNBREAKABLE.get()) {
            return 1;
        }
        return 10;
    }

    /**
     * 3. エンチャント同士の「競合」を無効化
     * これにより、ダメージ軽減と火炎耐性を同時に付けるなど、制限なしに全てのエンチャントを盛れます。
     */
    @Redirect(
            method = "createResult",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;isCompatibleWith(Lnet/minecraft/world/item/enchantment/Enchantment;)Z")
    )
    private boolean allowAllConflicts(Enchantment instance, Enchantment other) {
        return true; // 全ての組み合わせを許可
    }


    /**
     * 4. コストを最大30に固定 ＆ 「修復コストの累積」をリセット
     * これで、何回合成してもコストが増え続けず、ずっと30レベルで合成し続けられます。
     */
    @Inject(method = "createResult", at = @At("TAIL"))
    private void capCostAndResetPenalty(CallbackInfo ci) {
        // 表示される経験値コストを最大30に固定
        if (this.cost.get() > 10) {
            this.cost.set(10);
        }

        // 完成品の「修復コスト（内部ペナルティ）」を0にリセット
        // これがないと、次に金床を使うときにコストが跳ね上がります
        AnvilMenu menu = (AnvilMenu)(Object)this;
        ItemStack resultStack = menu.getSlot(2).getItem();
        if (!resultStack.isEmpty()) {
            resultStack.setRepairCost(0);
        }
    }
}