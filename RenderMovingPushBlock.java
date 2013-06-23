package mods.handheldpiston;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class RenderMovingPushBlock extends Render
{
    public RenderMovingPushBlock()
    {
    	super();
        shadowSize = 0.5F;
    }

    public void doRenderMovingPushBlock(EntityMovingPushBlock entitymovingpushblock, double d, double d1, double d2)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        loadTexture("/terrain.png");
        Block block = Block.blocksList[entitymovingpushblock.blockID];
        int metaData = entitymovingpushblock.metadata;
        World world = entitymovingpushblock.worldObj;
        GL11.glDisable(2896 /*GL_LIGHTING*/);
        if (Block.blocksList[block.blockID] instanceof BlockStairs)
        {
            customRenderBlockStairs(block, MathHelper.floor_double(entitymovingpushblock.posX), MathHelper.floor_double(entitymovingpushblock.posY), MathHelper.floor_double(entitymovingpushblock.posZ), metaData);
        }
        else
        {
            customRender(block, metaData, world, MathHelper.floor_double(entitymovingpushblock.posX), MathHelper.floor_double(entitymovingpushblock.posY), MathHelper.floor_double(entitymovingpushblock.posZ));
        }
        GL11.glEnable(2896 /*GL_LIGHTING*/);
        GL11.glPopMatrix();
    }
@Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
        doRenderMovingPushBlock((EntityMovingPushBlock)entity, d, d1, d2);
    }

    public void customRender(Block block, int i, World world, int j, int k, int l)
    {
        float f = 0.5F;
        float f1 = 1.0F;
        float f2 = 0.8F;
        float f3 = 0.6F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, j, k, l));
        float f4 = 1.0F;
        float f5 = 1.0F;
        if (f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f * f5, f * f5, f * f5);
        renderBlocks.renderFaceYNeg(block, -0.5D, -0.5D, -0.5D, block.getIcon(0, i));
        f5 = 1.0F;
        if (f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f1 * f5, f1 * f5, f1 * f5);
        renderBlocks.renderFaceYPos(block, -0.5D, -0.5D, -0.5D, block.getIcon(1, i));
        f5 = 1.0F;
        if (f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f2 * f5, f2 * f5, f2 * f5);
        renderBlocks.renderFaceXPos(block, -0.5D, -0.5D, -0.5D, block.getIcon(2, i));
        f5 = 1.0F;
        if (f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f2 * f5, f2 * f5, f2 * f5);
        renderBlocks.renderFaceXNeg(block, -0.5D, -0.5D, -0.5D, block.getIcon(3, i));
        f5 = 1.0F;
        if (f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f3 * f5, f3 * f5, f3 * f5);
        renderBlocks.renderFaceZNeg(block, -0.5D, -0.5D, -0.5D, block.getIcon(4, i));
        f5 = 1.0F;
        if (f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f3 * f5, f3 * f5, f3 * f5);
        renderBlocks.renderFaceZPos(block, -0.5D, -0.5D, -0.5D, block.getIcon(5, i));
        tessellator.draw();
    }

    public boolean customRenderBlockStairs(Block block, int i, int j, int k, int l)
    {
        boolean flag = false;
        if (l == 0)
        {
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F);
            renderBlocks.renderStandardBlock(block, i, j, k);
            block.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            renderBlocks.renderStandardBlock(block, i, j, k);
            flag = true;
        }
        else if (l == 1)
        {
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
            renderBlocks.renderStandardBlock(block, i, j, k);
            block.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            renderBlocks.renderStandardBlock(block, i, j, k);
            flag = true;
        }
        else if (l == 2)
        {
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
            renderBlocks.renderStandardBlock(block, i, j, k);
            block.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
            renderBlocks.renderStandardBlock(block, i, j, k);
            flag = true;
        }
        else if (l == 3)
        {
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
            renderBlocks.renderStandardBlock(block, i, j, k);
            block.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
            renderBlocks.renderStandardBlock(block, i, j, k);
            flag = true;
        }
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        return flag;
    }
}
