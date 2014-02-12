package assets.handheldpiston;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
		if (i == Blocks.obsidian || i.func_149712_f(world, j, k, l) == -1F) {
			return false;
		}else if (i.func_149688_o().getMaterialMobility() > 0) {
			return false;
		} else {
			return !(i.hasTileEntity(world.getBlockMetadata(j,k,l)));
		}
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10) {
		Block i1 = world.func_147439_a(i, j, k);
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
		Block j2 = world.func_147439_a(i2, k1, l1);
		if (canPushBlock(world, i1, i, k, l) && (j2 == Blocks.air || j2.func_149688_o() == Material.field_151586_h || j2.func_149688_o() == Material.field_151587_i || j2 == Blocks.fire)) {
			if (!world.isRemote) {
				EntityMovingPushBlock entitymovingpushblock = new EntityMovingPushBlock(world, (float) i + 0.5F, (float) j + 0.5F, (float) k + 0.5F, i1,(short) l, j1, 0.1D * dir);
				world.spawnEntityInWorld(entitymovingpushblock);
			}
			world.func_147468_f(i, j, k);
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
