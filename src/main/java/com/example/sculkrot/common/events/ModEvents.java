package com.example.sculkrot.common.events;

import com.example.sculkrot.init.ModMobEffects;
import com.example.sculkrot.init.data.ModDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import team.lodestar.lodestone.helpers.DamageTypeHelper;

@EventBusSubscriber
public class ModEvents {

    @SubscribeEvent
    public static void onLivingHurt(LivingDamageEvent.Pre event) {

    }
    //LivingIncomingDamageEvent

    @SubscribeEvent
    public static void onLivingSoonHurt(LivingIncomingDamageEvent event) {
        var target = event.getEntity();
        float damage = event.getOriginalAmount();
        if (event.getSource().is(ModDamageTypes.SCULK)) return;
        if (target.hasEffect(ModMobEffects.MYCOTOXIN)) {
            var oldSource = event.getSource();
            event.setAmount(0);
            target.invulnerableTime = 0;
            target.hurt(DamageTypeHelper.create(ModDamageTypes.SCULK, oldSource.getEntity()), damage);
        }
    }
}
