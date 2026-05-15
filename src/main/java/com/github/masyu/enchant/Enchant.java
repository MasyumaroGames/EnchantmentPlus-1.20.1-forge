package com.github.masyu.enchant;

import com.github.masyu.enchant.regi.ModEnchantment;
import com.github.masyu.enchant.regi.ModTabs;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("enchant")
public class Enchant {

    public static final String MODID = "enchant";

    public Enchant(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEnchantment.ENCHANTMENTS.register(bus);
        ModTabs.MOD_TABS.register(bus);
    }

}