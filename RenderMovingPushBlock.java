package assets.handheldpiston;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class RenderMovingPushBlock extends Render
{
	public RenderMovingPushBlock()
    {
        shadowSize = 0.5F;
    }

    public void doRenderMovingPushBlock(EntityMovingPushBlock entitymovingpushblock, double d, double d1, double d2)
    {
    	Block block = Block.blocksList[entitymovingpushblock.blockID];
    	if(block!=null)
    	{
	        int metaData = entitymovingpushblock.metadata;
	        World world = entitymovingpushblock.getWorld();
	        GL11.glPushMatrix();
	        GL11.glTranslatef((float)d, (float)d1, (float)d2);
	        //this.func_110775_a(entitymovingpushblock);
	        //loadTexture("/terrain.png");
	        GL11.glDisable(GL11.GL_LIGHTING);
	        this.renderBlocks.blockAccess = world;
	    
	        if (block instanceof BlockStairs)
	        {
	        	renderBlocks.setOverrideBlockTexture(block.getIcon(0, metaData));
	            customRenderBlockStairs(block, entitymovingpushblock.posX, entitymovingpushblock.posY, entitymovingpushblock.posZ, metaData);
	            renderBlocks.clearOverrideBlockTexture();
	        }
	        else if (block!=null)
	        {
	            customRender(block, metaData, world, entitymovingpushblock.posX, entitymovingpushblock.posY, entitymovingpushblock.posZ);
	        }
	        
	        GL11.glEnable(GL11.GL_LIGHTING);
	        GL11.glPopMatrix();
    	}
    }
    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
        doRenderMovingPushBlock((EntityMovingPushBlock)entity, d, d1, d2);
    }

    public void customRender(Block block, int i, World world, double posX, double posY, double posZ)
    {
    	renderBlocks.setRenderBounds(posX-(int)posX,posY-(int)posY,posZ-(int)posZ,posX-(int)posX+1,posY-(int)posY+1,posZ-(int)posZ+1);
    	renderBlocks.renderBlockSandFalling(block, world, (int)posX, (int)posY, (int)posZ, i);
    }

    public void customRenderBlockStairs(Block block, double posX, double posY, double posZ, int l)
    {
        if (l == 0)
        {
        	renderBlocks.setRenderBounds(posX-(int)posX,posY-(int)posY,posZ-(int)posZ,posX-(int)posX+0.5,posY-(int)posY+0.5,posZ-(int)posZ+1);
            renderBlocks.renderStandardBlock(block, (int)posX, (int)posY, (int)posZ);
            renderBlocks.setRenderBounds(posX-(int)posX+0.5,posY-(int)posY,posZ-(int)posZ,posX-(int)posX+1,posY-(int)posY+1,posZ-(int)posZ+1);
        	renderBlocks.renderStandardBlock(block, (int)posX, (int)posY, (int)posZ);
        }
        else if (l == 1)
        {
        	renderBlocks.setRenderBounds(posX-(int)posX,posY-(int)posY,posZ-(int)posZ,posX-(int)posX+0.5,posY-(int)posY+1,posZ-(int)posZ+1);
        	renderBlocks.renderStandardBlock(block, (int)posX, (int)posY, (int)posZ);
        	renderBlocks.setRenderBounds(posX-(int)posX,posY-(int)posY,posZ-(int)posZ,posX-(int)posX+1,posY-(int)posY+0.5,posZ-(int)posZ+1);
        	renderBlocks.renderStandardBlock(block, (int)posX, (int)posY, (int)posZ);
        }
        else if (l == 2)
        {
        	renderBlocks.setRenderBounds(posX-(int)posX,posY-(int)posY,posZ-(int)posZ,posX-(int)posX+1,posY-(int)posY+0.5,posZ-(int)posZ+0.5);
        	renderBlocks.renderStandardBlock(block, (int)posX, (int)posY, (int)posZ);
        	renderBlocks.setRenderBounds(posX-(int)posX,posY-(int)posY,posZ-(int)posZ+0.5,posX-(int)posX+1,posY-(int)posY+1,posZ-(int)posZ+1);
        	renderBlocks.renderStandardBlock(block, (int)posX, (int)posY, (int)posZ);
        }
        else if (l == 3)
        {
        	renderBlocks.setRenderBounds(posX-(int)posX,posY-(int)posY,posZ-(int)posZ,posX-(int)posX+1,posY-(int)posY+1,posZ-(int)posZ+1);
        	renderBlocks.renderStandardBlock(block, (int)posX, (int)posY, (int)posZ);
        	renderBlocks.setRenderBounds(posX-(int)posX,posY-(int)posY,posZ-(int)posZ+0.5,posX-(int)posX+1,posY-(int)posY+0.5,posZ-(int)posZ+1);
        	renderBlocks.renderStandardBlock(block, (int)posX, (int)posY, (int)posZ);
        }
        renderBlocks.setRenderBounds(posX-(int)posX,posY-(int)posY,posZ-(int)posZ,posX-(int)posX+1,posY-(int)posY+1,posZ-(int)posZ+1);
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationBlocksTexture;
	}
}
