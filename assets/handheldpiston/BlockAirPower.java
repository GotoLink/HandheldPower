package assets.handheldpiston;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockAirPower extends Block {
	private int powerRange;

	public BlockAirPower(int power) {
		super(Material.field_151581_o);
        func_149672_a(field_149769_e);
        func_149675_a(true);
		if (power >= 0 && power <= 15) {
			this.powerRange = power;
		}
	}

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side){
        return false;
    }

	@Override
	public void func_149749_a(World world, int i, int j, int k, Block par5, int par6) {
		world.func_147459_d(i, j, k, Push.airPower);
	}

	@Override
	public boolean func_149744_f() {
		return true;
	}

	@Override
	public int func_149748_c(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		return this.powerRange;
	}

	@Override
	public int func_149709_b(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		return this.powerRange;
	}

	@Override
	public void func_149726_b(World world, int i, int j, int k) {
		Push.newAirPower(world, i, j, k);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149651_a(IIconRegister par1IconRegister) {
	}

	@Override
	public int func_149738_a(World world) {
		return 1;
	}

    //Delete the block after some time
	@Override
	public void func_149674_a(World world, int i, int j, int k, Random random) {
		world.func_147468_f(i, j, k);
	}

    //Make sure thing is invisible

    @Override
    public AxisAlignedBB func_149668_a(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_){
        return null;
    }

    @Override
    public boolean func_149686_d(){
        return false;
    }

    @Override
    public boolean func_149662_c(){
        return false;
    }

    @Override
    public boolean func_149678_a(int p_149678_1_, boolean p_149678_2_){
        return false;
    }

    @Override
    public int func_149645_b(){
        return -1;
    }
}
