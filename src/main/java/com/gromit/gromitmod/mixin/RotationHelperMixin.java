package com.gromit.gromitmod.mixin;

import com.github.lunatrius.core.util.MBlockPos;
import com.github.lunatrius.schematica.client.util.RotationHelper;
import com.gromit.gromitmod.gui.button.SchematicButton;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(RotationHelper.class)
public class RotationHelperMixin {

    /**
     * @author Gromit
     */
    @Overwrite(remap = false)
    public BlockPos rotatePos(BlockPos pos, EnumFacing axis, Vec3i dimensions, MBlockPos rotated) throws RotationHelper.RotationException {
        SchematicButton.currentSchematicButton.rotate(axis);
        switch (axis) {
            case DOWN:
                return rotated.set(pos.getZ(), pos.getY(), dimensions.getZ() - 1 - pos.getX());
            case UP:
                return rotated.set(dimensions.getX() - 1 - pos.getZ(), pos.getY(), pos.getX());
            case NORTH:
                return rotated.set(dimensions.getX() - 1 - pos.getY(), pos.getX(), pos.getZ());
            case SOUTH:
                return rotated.set(pos.getY(), dimensions.getY() - 1 - pos.getX(), pos.getZ());
            case WEST:
                return rotated.set(pos.getX(), dimensions.getY() - 1 - pos.getZ(), pos.getY());
            case EAST:
                return rotated.set(pos.getX(), pos.getZ(), dimensions.getZ() - 1 - pos.getY());
            default:
                throw new RotationHelper.RotationException("'%s' is not a valid axis!", new Object[]{axis.getName()});
        }
    }
}
