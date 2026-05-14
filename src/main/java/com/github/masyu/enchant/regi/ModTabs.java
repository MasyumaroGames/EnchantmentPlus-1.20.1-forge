package com.github.masyu.enchant.regi;

import com.github.masyu.enchant.Enchant;
import com.github.masyu.enchant.tab.EnchantMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTabs {

    public static final DeferredRegister<CreativeModeTab> MOD_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Enchant.MODID);

    public static final RegistryObject<CreativeModeTab> ENCHANT_MAIN = MOD_TABS.register("enchant_main",
            ()->{return CreativeModeTab.builder()
                    .icon(()->new ItemStack(Items.ENCHANTED_BOOK))
                    .title(Component.translatable("itemGroup.enchant_main"))
                    .displayItems((param,output)->{
                        for(ItemStack item:EnchantMain.items){
                            output.accept(item);
                        }
                    })
                    .build();
            });

}
