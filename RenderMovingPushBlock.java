package assets.handheldpiston;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class RenderMovingPushBlock extends Render
{
	private RenderBlocks renderBlock = new RenderBlocks();

    public RenderMovingPushBlock()
    {
        shadowSize = 0.5F;
    }

    public void doRenderMovingPushBlock(EntityMovingPushBlock entitymovingpushblock, double d, double d1, double d2)
    {
    	Block block = Block.blocksList[entitymovingpushblock.blockID];
        int metaData = entitymovingpushblock.metadata;
        World world = entitymovingpushblock.getWorld();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        this.func_110775_a(entitymovingpushblock);
        //loadTexture("/terrain.png");
        GL11.glDisable(GL11.GL_LIGHTING);
        this.renderBlock.blockAccess = world;
        if (block instanceof BlockStairs)
        {
            customRenderBlockStairs(block, MathHelper.floor_double(entitymovingpushblock.posX), MathHelper.floor_double(entitymovingpushblock.posY), MathHelper.floor_double(entitymovingpushblock.posZ), metaData);
        }
        else if (block!=null)
        {
            customRender(block, metaData, world, MathHelper.floor_double(entitymovingpushblock.posX), MathHelper.floor_double(entitymovingpushblock.posY), MathHelper.floor_double(entitymovingpushblock.posZ));
        }
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
        doRenderMovingPushBlock((EntityMovingPushBlock)entity, d, d1, d2);
    }

    public void customRender(Block block, int i, World world, int j, int k, int l)
    {
    	renderBlock.setRenderBoundsFromBlock(block);
    	renderBlock.renderBlockSandFalling(block, world, j, k, l, i);
    }

    public boolean customRenderBlockStairs(Block block, int i, int j, int k, int l)
    {
        boolean flag = false;
        if (l == 0)
        {
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F);
            renderBlock.renderStandardBlock(block, i, j, k);
            block.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            renderBlock.renderStandardBlock(block, i, j, k);
            flag = true;
        }
        else if (l == 1)
        {
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
            renderBlock.renderStandardBlock(block, i, j, k);
            block.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            renderBlock.renderStandardBlock(block, i, j, k);
            flag = true;
        }
        else if (l == 2)
        {
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
            renderBlock.renderStandardBlock(block, i, j, k);
            block.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
            renderBlock.renderStandardBlock(block, i, j, k);
            flag = true;
        }
        else if (l == 3)
        {
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
            renderBlock.renderStandardBlock(block, i, j, k);
            block.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
            renderBlock.renderStandardBlock(block, i, j, k);
            flag = true;
        }
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        return flag;
    }

	@Override
	protected ResourceLocation func_110775_a(Entity entity) {
		return TextureMap.field_110575_b;
	}
}
