package com.gromit.gromitmod.mixin;

import com.github.lunatrius.core.util.BlockPosHelper;
import com.github.lunatrius.core.util.MBlockPos;
import com.github.lunatrius.schematica.api.ISchematic;
import com.github.lunatrius.schematica.client.util.FlipHelper;
import com.github.lunatrius.schematica.world.storage.Schematic;
import com.gromit.gromitmod.gui.button.SchematicButton;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Iterator;
import java.util.List;

@Mixin(FlipHelper.class)
public abstract class FlipHelperMixin {

    @Shadow protected abstract IBlockState flipBlock(IBlockState blockState, EnumFacing axis, boolean forced) throws FlipHelper.FlipException;
    @Shadow protected abstract BlockPos flipPos(BlockPos pos, EnumFacing axis, Vec3i dimensions, MBlockPos flipped) throws FlipHelper.FlipException;

    /**
     * @author Gromit
     */
    @Overwrite(remap = false)
    public Schematic flip(ISchematic schematic, EnumFacing axis, boolean forced) throws FlipHelper.FlipException {
        Vec3i dimensionsFlipped = new Vec3i(schematic.getWidth(), schematic.getHeight(), schematic.getLength());
        Schematic schematicFlipped = new Schematic(schematic.getIcon(), dimensionsFlipped.getX(), dimensionsFlipped.getY(), dimensionsFlipped.getZ());
        MBlockPos tmp = new MBlockPos();
        Iterator i$ = BlockPosHelper.getAllInBox(0, 0, 0, schematic.getWidth() - 1, schematic.getHeight() - 1, schematic.getLength() - 1).iterator();

        SchematicButton.currentSchematicButton.flip(axis);
        while(i$.hasNext()) {
            MBlockPos pos = (MBlockPos)i$.next();
            IBlockState blockState = schematic.getBlockState(pos);
            IBlockState blockStateFlipped = flipBlock(blockState, axis, forced);
            schematicFlipped.setBlockState(flipPos(pos, axis, dimensionsFlipped, tmp), blockStateFlipped);
        }

        List<TileEntity> tileEntities = schematic.getTileEntities();
        i$ = tileEntities.iterator();

        while(i$.hasNext()) {
            TileEntity tileEntity = (TileEntity)i$.next();
            BlockPos pos = tileEntity.getPos();
            tileEntity.setPos(new BlockPos(flipPos(pos, axis, dimensionsFlipped, tmp)));
            schematicFlipped.setTileEntity(tileEntity.getPos(), tileEntity);
        }

        return schematicFlipped;
    }
}
