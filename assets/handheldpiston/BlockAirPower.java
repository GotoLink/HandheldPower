package assets.handheldpiston;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class BlockAirPower extends Block {
	private int powerRange;

	public BlockAirPower(int power) {
		super(Material.circuits);
        setTickRandomly(true);
		if (power >= 0 && power <= 15) {
			this.powerRange = power;
		}
	}

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side){
        return false;
    }

	@Override
	public void breakBlock(World world, int i, int j, int k, Block par5, int par6) {
		world.notifyBlocksOfNeighborChange(i, j, k, Push.airPower);
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		return this.powerRange;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		return this.powerRange;
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		Push.newAirPower(world, i, j, k);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
	}

	@Override
	public int tickRate(World world) {
		return 1;
	}

    //Delete the block after some time
	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		world.setBlockToAir(i, j, k);
	}

    //Make sure thing is invisible

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_){
        return null;
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_){
        return false;
    }

    @Override
    public int getRenderType(){
        return -1;
    }
}
