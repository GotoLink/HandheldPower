package assets.handheldpiston;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPowerer extends Item {
	public ItemPowerer() {
		super();
		maxStackSize = 1;
		setMaxDamage(1000);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10) {
		if (l == 0) {
			j--;
		}
		if (l == 1) {
			j++;
		}
		if (l == 2) {
			k--;
		}
		if (l == 3) {
			k++;
		}
		if (l == 4) {
			i--;
		}
		if (l == 5) {
			i++;
		}
		if (world.isAirBlock(i, j, k)) {
			world.setBlock(i, j, k, Push.airPower);
		}
		itemstack.damageItem(1, entityplayer);
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("handheldpiston:RedstoneRemote");
	}
}
