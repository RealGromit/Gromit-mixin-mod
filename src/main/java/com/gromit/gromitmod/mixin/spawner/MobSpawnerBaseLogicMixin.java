package com.gromit.gromitmod.mixin.spawner;

import com.gromit.gromitmod.module.fps.Spawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MobSpawnerBaseLogic.class)
public abstract class MobSpawnerBaseLogicMixin {

    @Shadow protected abstract boolean isActivated();
    @Shadow public abstract BlockPos getSpawnerPosition();
    @Shadow public abstract World getSpawnerWorld();
    @Shadow private int spawnDelay;
    @Shadow private double prevMobRotation;
    @Shadow private double mobRotation;
    @Shadow protected abstract void resetTimer();
    @Shadow private int spawnCount;
    @Shadow protected abstract String getEntityNameToSpawn();
    @Shadow private int maxNearbyEntities;
    @Shadow protected abstract Entity spawnNewEntity(Entity p_spawnNewEntity_1_, boolean p_spawnNewEntity_2_);
    @Shadow private int spawnRange;

    private final Spawner spawnerModule = Spawner.getInstance();

    /**
     * @author Gromit
     */
    @Overwrite
    public void updateSpawner() {
        if (this.isActivated()) {
            BlockPos lvt_1_1_ = this.getSpawnerPosition();
            double lvt_6_2_;
            if (this.getSpawnerWorld().isRemote) {
                double lvt_2_1_ = ((float) lvt_1_1_.getX() + this.getSpawnerWorld().rand.nextFloat());
                double lvt_4_1_ = ((float) lvt_1_1_.getY() + this.getSpawnerWorld().rand.nextFloat());
                lvt_6_2_ = ((float) lvt_1_1_.getZ() + this.getSpawnerWorld().rand.nextFloat());
                if (!spawnerModule.stateCheckbox.isState() || !spawnerModule.spawnerFireDisable.isState()) {
                    this.getSpawnerWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, lvt_2_1_, lvt_4_1_, lvt_6_2_, 0.0, 0.0, 0.0, new int[0]);
                    this.getSpawnerWorld().spawnParticle(EnumParticleTypes.FLAME, lvt_2_1_, lvt_4_1_, lvt_6_2_, 0.0, 0.0, 0.0, new int[0]);
                }
                if (this.spawnDelay > 0) {
                    --this.spawnDelay;
                }

                this.prevMobRotation = this.mobRotation;
                this.mobRotation = (this.mobRotation + (double) (1000.0F / ((float) this.spawnDelay + 200.0F))) % 360.0;
            } else {
                if (this.spawnDelay == -1) {
                    this.resetTimer();
                }

                if (this.spawnDelay > 0) {
                    --this.spawnDelay;
                    return;
                }

                boolean lvt_2_2_ = false;
                int lvt_3_1_ = 0;

                while (true) {
                    if (lvt_3_1_ >= this.spawnCount) {
                        if (lvt_2_2_) {
                            this.resetTimer();
                        }
                        break;
                    }

                    Entity lvt_4_2_ = EntityList.createEntityByName(this.getEntityNameToSpawn(), this.getSpawnerWorld());
                    if (lvt_4_2_ == null) {
                        return;
                    }

                    int lvt_5_1_ = this.getSpawnerWorld().getEntitiesWithinAABB(lvt_4_2_.getClass(), (new AxisAlignedBB((double) lvt_1_1_.getX(), (double) lvt_1_1_.getY(), (double) lvt_1_1_.getZ(), (double) (lvt_1_1_.getX() + 1), (double) (lvt_1_1_.getY() + 1), (double) (lvt_1_1_.getZ() + 1))).expand((double) this.spawnRange, (double) this.spawnRange, (double) this.spawnRange)).size();
                    if (lvt_5_1_ >= this.maxNearbyEntities) {
                        this.resetTimer();
                        return;
                    }

                    lvt_6_2_ = (double) lvt_1_1_.getX() + (this.getSpawnerWorld().rand.nextDouble() - this.getSpawnerWorld().rand.nextDouble()) * (double) this.spawnRange + 0.5;
                    double lvt_8_1_ = (double) (lvt_1_1_.getY() + this.getSpawnerWorld().rand.nextInt(3) - 1);
                    double lvt_10_1_ = (double) lvt_1_1_.getZ() + (this.getSpawnerWorld().rand.nextDouble() - this.getSpawnerWorld().rand.nextDouble()) * (double) this.spawnRange + 0.5;
                    EntityLiving lvt_12_1_ = lvt_4_2_ instanceof EntityLiving ? (EntityLiving) lvt_4_2_ : null;
                    lvt_4_2_.setLocationAndAngles(lvt_6_2_, lvt_8_1_, lvt_10_1_, this.getSpawnerWorld().rand.nextFloat() * 360.0F, 0.0F);
                    if (lvt_12_1_ == null || lvt_12_1_.getCanSpawnHere() && lvt_12_1_.isNotColliding()) {
                        this.spawnNewEntity(lvt_4_2_, true);
                        this.getSpawnerWorld().playAuxSFX(2004, lvt_1_1_, 0);
                        if (lvt_12_1_ != null) {
                            lvt_12_1_.spawnExplosionParticle();
                        }

                        lvt_2_2_ = true;
                    }

                    ++lvt_3_1_;
                }
            }

        }
    }
}
