package assets.handheldpiston;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPowerer extends Item
{
    public ItemPowerer(int i)
    {
        super(i);
        maxStackSize = 1;
        setMaxDamage(1000);
    }
    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10)
    {
        if (l == 0)
        {
            j--;
        }
        if (l == 1)
        {
            j++;
        }
        if (l == 2)
        {
            k--;
        }
        if (l == 3)
        {
            k++;
        }
        if (l == 4)
        {
            i--;
        }
        if (l == 5)
        {
            i++;
        }
        if (world.getBlockId(i, j, k) == 0)
        {
            world.setBlock(i, j, k, Push.airPower.blockID);
        }
        itemstack.damageItem(1, entityplayer);
        return true;
    }
    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("handheldpiston:RedstoneRemote");
    }
}
