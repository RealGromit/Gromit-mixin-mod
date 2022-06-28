package com.gromit.gromitmod.mixin;

import com.gromit.gromitmod.module.fps.Tnt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityTNTPrimed.class)
public abstract class EntityTntPrimedMixin extends Entity {

    @Shadow public int fuse;
    @Shadow protected abstract void explode();

    private final Tnt tntModule = Tnt.getInstance();

    public EntityTntPrimedMixin(World p_i1582_1_) {
        super(p_i1582_1_);
    }

    @Inject(method = "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/entity/EntityLivingBase;)V", at = @At("RETURN"))
    public void onInit(World world, double x, double y, double z, EntityLivingBase entityLivingBase, CallbackInfo ci) {
        EntityTNTPrimed tnt = ((EntityTNTPrimed) (Object) this);
        tnt.motionX = 0;
        tnt.motionZ = 0;
    }

    /**
     * @author Gromit
     */
    @Overwrite
    public void onUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        motionY -= 0.03999999910593033;
        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.9800000190734863;
        motionY *= 0.9800000190734863;
        motionZ *= 0.9800000190734863;
        if (onGround) {
            motionX *= 0.699999988079071;
            motionZ *= 0.699999988079071;
            motionY *= -0.5;
        }

        if (fuse-- <= 0) {
            setDead();
            if (!worldObj.isRemote) {
                explode();
            }
        } else {
            handleWaterMovement();
            if (tntModule.tntSmokeParticle.isState()) worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX, posY + 0.5, posZ, 0.0, 0.0, 0.0, new int[0]);
        }

    }
}
