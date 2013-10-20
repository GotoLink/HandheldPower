package assets.handheldpiston;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAirPower extends Block {
	private int powerRange;

	public BlockAirPower(int i, int power) {
		super(i, Material.air);
		stepSound = soundPowderFootstep;
		setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		setTickRandomly(true);
		if (power >= 0 && power <= 15) {
			this.powerRange = power;
		}
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, int par5, int par6) {
		world.notifyBlocksOfNeighborChange(i, j, k, Push.airPower.blockID);
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
		return false;
	}

	@Override
	public boolean isCollidable() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
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

	/*
	 * public static boolean isPowerProviderOrWire(IBlockAccess iblockaccess,
	 * int i, int j, int k, int l) { return true; }
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
	}

	@Override
	public int tickRate(World world) {
		return 1;
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		world.setBlockToAir(i, j, k);
	}
}
