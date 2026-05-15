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
     * 1. 「高すぎます！」制限を解除
     * コストが40を超えても合成画面を閉じないようにします。
     */
    @ModifyConstant(method = "createResult", constant = @Constant(intValue = 40))
    private int removeTooExpensiveLimit(int original) {
        return 1000000;
    }

    /**
     * 2. レベル上限を「10」に設定
     * 合成時、この値（10）が最大値として参照されます。
     * 9+9は10になりますが、10+10は11になろうとして10に制限されます。
     * 結果が変わらない（10のまま）ため、バニラのロジックで「×」が表示されます。
     */
    @Redirect(
            method = "createResult",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;getMaxLevel()I")
    )
    private int redirectMaxLevel(Enchantment instance) {
        if (instance == Enchantments.MENDING || instance == Enchantments.SILK_TOUCH || instance == Enchantments.AQUA_AFFINITY || instance == Enchantments.BINDING_CURSE || instance == Enchantments.INFINITY_ARROWS || instance == Enchantments.VANISHING_CURSE || instance == ModEnchantment.UNBREAKABLE.get()) {
            return 1;
        }
        if (instance == Enchantments.KNOCKBACK || instance == Enchantments.PUNCH_ARROWS) {
            return 5;
        }
        return 10;
    }

    /**
     * 3. エンチャントの競合（ダメージ軽減と火炎耐性など）を無視
     * これにより、1つの装備に全てのエンチャントを詰め込めるようになります。
     */
    @Redirect(
            method = "createResult",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;isCompatibleWith(Lnet/minecraft/world/item/enchantment/Enchantment;)Z")
    )
    private boolean allowAllConflicts(Enchantment instance, Enchantment other) {
        return true;
    }

    /**
     * 4. コストを最大30に固定 ＆ 修復コストの累積（ペナルティ）をリセット
     * どんなにエンチャントを重ねても、常に最大30レベルで合成でき、
     * 「6個制限」の実質的な原因であるコスト増加ペナルティを毎回0にします。
     */
    @Inject(method = "createResult", at = @At("TAIL"))
    private void finalizeAnvilResult(CallbackInfo ci) {
        // コストを30までに制限
        if (this.cost.get() > 30) {
            this.cost.set(30);
        }

        // 完成品の内部ペナルティを0に書き換える
        // これにより「何回合成してもコストが重くならない」＝「無限にエンチャントできる」ようになります
        AnvilMenu menu = (AnvilMenu)(Object)this;
        ItemStack resultStack = menu.getSlot(2).getItem();
        if (!resultStack.isEmpty()) {
            resultStack.setRepairCost(0);
        }
    }
}