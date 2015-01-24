package assets.handheldpiston;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

public class RenderPushWorld implements IBlockAccess{
    private final EntityMovingPushBlock pusher;
    public RenderPushWorld(EntityMovingPushBlock entityPushing){
        this.pusher = entityPushing;
    }

    @Override
    public Block getBlock(int x, int y, int z) {
        if(pusher.isStart(x, y, z)){
            return pusher.blockID;
        }
        return pusher.worldObj.getBlock(x, y, z);
    }

    @Override
    public TileEntity getTileEntity(int x, int y, int z) {
        return pusher.worldObj.getTileEntity(x, y, z);
    }

    @Override
    public int getLightBrightnessForSkyBlocks(int x, int y, int z, int i) {
        return pusher.worldObj.getLightBrightnessForSkyBlocks(x, y, z, i);
    }

    @Override
    public int getBlockMetadata(int x, int y, int z) {
        if(pusher.isStart(x, y, z)){
            return pusher.metadata;
        }
        return pusher.worldObj.getBlockMetadata(x, y, z);
    }

    @Override
    public int isBlockProvidingPowerTo(int x, int y, int z, int i) {
        return pusher.worldObj.isBlockProvidingPowerTo(x, y, z, i);
    }

    @Override
    public boolean isAirBlock(int x, int y, int z) {
        return pusher.worldObj.isAirBlock(x, y, z);
    }

    @Override
    public BiomeGenBase getBiomeGenForCoords(int x, int z) {
        return pusher.worldObj.getBiomeGenForCoords(x, z);
    }

    @Override
    public int getHeight() {
        return pusher.worldObj.getHeight();
    }

    @Override
    public boolean extendedLevelsInChunkCache() {
        return pusher.worldObj.extendedLevelsInChunkCache();
    }

    @Override
    public boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default) {
        return pusher.worldObj.isSideSolid(x, y, z, side, _default);
    }
}
