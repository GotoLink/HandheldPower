package mods.handheldpiston;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPusher extends Item
{
	private final boolean isSticky;

    public ItemPusher(int i, boolean sticky)
    {
        super(i);
        maxStackSize = 1;
        setMaxDamage(100);
        isSticky=sticky;
        setCreativeTab(CreativeTabs.tabRedstone);
    }

    public boolean canPushBlock(World world, int i, int j, int k, int l)
    {
        if (i == Block.obsidian.blockID || Block.blocksList[i].getBlockHardness(world, i, j, k) == -1F)
        {
            return false;
        }
        else if (Block.blocksList[i].getMobilityFlag() == 2 || Block.blocksList[i].getMobilityFlag() == 1)
        {
            return false;
        }
        else
        {
            return !(Block.blocksList[i] instanceof BlockContainer);
        }
    }
    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10)
    {
        int i1 = world.getBlockId(i, j, k);
        int j1 = world.getBlockMetadata(i, j, k);
        int k1 = j;
        int l1 = k;
        int i2 = i;
        int dir = isSticky ? -1:1;
        if (i1 != Block.vine.blockID)
        {
            if (l == 0)
            {
                k1+=dir;
            }
            if (l == 1)
            {
                k1-=dir;
            }
            if (l == 2)
            {
                l1+=dir;
            }
            if (l == 3)
            {
                l1-=dir;
            }
            if (l == 4)
            {
                i2+=dir;
            }
            if (l == 5)
            {
                i2-=dir;
            }
        }
        int j2 = world.getBlockId(i2, k1, l1);
        if (canPushBlock(world, i1, i, k, l) && (j2 == 0 || j2 == 8 || j2 == 9 || j2 == 10 || j2 == 11 || j2 == 51))
        {
        	if(!world.isRemote)
        	{
        		EntityMovingPushBlock entitymovingpushblock = new EntityMovingPushBlock(world, (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, i1, l, j1, 0.10000000000000001D*dir);         
            	world.spawnEntityInWorld(entitymovingpushblock);
        	}
            world.setBlockToAir(i, j, k);
            itemstack.damageItem(1, entityplayer);
        }
        return true;
    }
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("handheldpiston:/"+(isSticky?"stickyP":"p")+"istonItem");
    }
}
