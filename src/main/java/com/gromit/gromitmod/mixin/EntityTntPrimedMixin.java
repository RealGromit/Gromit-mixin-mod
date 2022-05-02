package com.gromit.gromitmod.mixin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityTNTPrimed.class)
public class EntityTntPrimedMixin {

    @Inject(method = "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/entity/EntityLivingBase;)V", at = @At("RETURN"))
    public void onInit(World world, double x, double y, double z, EntityLivingBase entityLivingBase, CallbackInfo ci) {
        EntityTNTPrimed tnt = ((EntityTNTPrimed) (Object) this);
        tnt.motionX = 0;
        tnt.motionZ = 0;
    }
}
