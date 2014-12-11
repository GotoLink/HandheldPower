package assets.handheldpiston;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPusher extends Item {
	private final boolean isSticky;

	public ItemPusher(boolean sticky) {
		super();
		maxStackSize = 1;
		setMaxDamage(100);
		isSticky = sticky;
		setCreativeTab(CreativeTabs.tabRedstone);
	}

	public boolean canPushBlock(World world, Block i, int j, int k, int l) {
		if (i == Blocks.obsidian || i.getBlockHardness(world, j, k, l) == -1F) {
			return false;
		}else if (i.getMaterial().getMaterialMobility() > 0) {
			return false;
		} else {
			return !(i.hasTileEntity(world.getBlockMetadata(j,k,l)));
		}
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10) {
		Block i1 = world.getBlock(i, j, k);
		int j1 = world.getBlockMetadata(i, j, k);
		int k1 = j;
		int l1 = k;
		int i2 = i;
		int dir = isSticky ? -1 : 1;
		if (i1 != Blocks.vine) {
			if (l == 0) {
				k1 += dir;
			}
			if (l == 1) {
				k1 -= dir;
			}
			if (l == 2) {
				l1 += dir;
			}
			if (l == 3) {
				l1 -= dir;
			}
			if (l == 4) {
				i2 += dir;
			}
			if (l == 5) {
				i2 -= dir;
			}
		}
		Block j2 = world.getBlock(i2, k1, l1);
		if (canPushBlock(world, i1, i, k, l) && (j2 == Blocks.air || j2.getMaterial() == Material.water || j2.getMaterial() == Material.lava || j2 == Blocks.fire)) {
			if (!world.isRemote) {
				EntityMovingPushBlock entitymovingpushblock = new EntityMovingPushBlock(world, (float) i + 0.5F, (float) j + 0.5F, (float) k + 0.5F, i1,(short) l, j1, 0.1D * dir);
				world.spawnEntityInWorld(entitymovingpushblock);
			}
			world.setBlockToAir(i, j, k);
			itemstack.damageItem(1, entityplayer);
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("handheldpiston:" + (isSticky ? "stickyP" : "p") + "istonItem");
	}
}
