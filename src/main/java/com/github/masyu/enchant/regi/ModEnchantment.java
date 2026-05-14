package com.github.masyu.enchant.regi;

import com.github.masyu.enchant.Enchant;
import com.github.masyu.enchant.enchantment.Unbreakable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantment {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Enchant.MODID);

    public static final RegistryObject<Enchantment> UNBREAKABLE = ENCHANTMENTS.register("unbreakable", Unbreakable::new);

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);

    }
}
