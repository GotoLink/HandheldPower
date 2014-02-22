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
public class RenderMovingPushBlock extends Render {
	public RenderMovingPushBlock() {
		shadowSize = 0.5F;
	}

	public void customRender(Block block, int i, World world, double posX, double posY, double posZ) {
        //set render bounds
		field_147909_c.setRenderBoundsFromBlock(block);
        //use rendering from falling block
		field_147909_c.renderBlockSandFalling(block, world, MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ), i);
	}

	public void customRenderBlockStairs(Block block, double posX, double posY, double posZ, int l) {
		if (l == 0) {
			field_147909_c.overrideBlockBounds(posX - (int) posX, posY - (int) posY, posZ - (int) posZ, posX - (int) posX + 0.5, posY - (int) posY + 0.5, posZ - (int) posZ + 1);
			field_147909_c.renderStandardBlock(block, (int) posX, (int) posY, (int) posZ);
			field_147909_c.overrideBlockBounds(posX - (int) posX + 0.5, posY - (int) posY, posZ - (int) posZ, posX - (int) posX + 1, posY - (int) posY + 1, posZ - (int) posZ + 1);
			field_147909_c.renderStandardBlock(block, (int) posX, (int) posY, (int) posZ);
		} else if (l == 1) {
			field_147909_c.overrideBlockBounds(posX - (int) posX, posY - (int) posY, posZ - (int) posZ, posX - (int) posX + 0.5, posY - (int) posY + 1, posZ - (int) posZ + 1);
			field_147909_c.renderStandardBlock(block, (int) posX, (int) posY, (int) posZ);
			field_147909_c.overrideBlockBounds(posX - (int) posX, posY - (int) posY, posZ - (int) posZ, posX - (int) posX + 1, posY - (int) posY + 0.5, posZ - (int) posZ + 1);
			field_147909_c.renderStandardBlock(block, (int) posX, (int) posY, (int) posZ);
		} else if (l == 2) {
			field_147909_c.overrideBlockBounds(posX - (int) posX, posY - (int) posY, posZ - (int) posZ, posX - (int) posX + 1, posY - (int) posY + 0.5, posZ - (int) posZ + 0.5);
			field_147909_c.renderStandardBlock(block, (int) posX, (int) posY, (int) posZ);
			field_147909_c.overrideBlockBounds(posX - (int) posX, posY - (int) posY, posZ - (int) posZ + 0.5, posX - (int) posX + 1, posY - (int) posY + 1, posZ - (int) posZ + 1);
			field_147909_c.renderStandardBlock(block, (int) posX, (int) posY, (int) posZ);
		} else if (l == 3) {
			field_147909_c.overrideBlockBounds(posX - (int) posX, posY - (int) posY, posZ - (int) posZ, posX - (int) posX + 1, posY - (int) posY + 1, posZ - (int) posZ + 1);
			field_147909_c.renderStandardBlock(block, (int) posX, (int) posY, (int) posZ);
			field_147909_c.overrideBlockBounds(posX - (int) posX, posY - (int) posY, posZ - (int) posZ + 0.5, posX - (int) posX + 1, posY - (int) posY + 0.5, posZ - (int) posZ + 1);
			field_147909_c.renderStandardBlock(block, (int) posX, (int) posY, (int) posZ);
		}
		field_147909_c.overrideBlockBounds(posX - (int) posX, posY - (int) posY, posZ - (int) posZ, posX - (int) posX + 1, posY - (int) posY + 1, posZ - (int) posZ + 1);
        field_147909_c.unlockBlockBounds();
    }

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		doRenderMovingPushBlock((EntityMovingPushBlock) entity, d, d1, d2);
	}

	public void doRenderMovingPushBlock(EntityMovingPushBlock entitymovingpushblock, double d, double d1, double d2) {
		Block block = entitymovingpushblock.blockID;
		if (block != null) {
			int metaData = entitymovingpushblock.metadata;
			GL11.glPushMatrix();
			GL11.glTranslatef((float) d, (float) d1, (float) d2);
            bindEntityTexture(entitymovingpushblock);
			GL11.glDisable(GL11.GL_LIGHTING);
			if (block instanceof BlockStairs) {
                this.field_147909_c.blockAccess = entitymovingpushblock.worldObj;
				field_147909_c.setOverrideBlockTexture(block.getIcon(0, metaData));
				customRenderBlockStairs(block, entitymovingpushblock.posX, entitymovingpushblock.posY, entitymovingpushblock.posZ, metaData);
				field_147909_c.clearOverrideBlockTexture();
                this.field_147909_c.blockAccess = null;
			} else {
				customRender(block, metaData, entitymovingpushblock.worldObj, entitymovingpushblock.posX, entitymovingpushblock.posY, entitymovingpushblock.posZ);
			}
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationBlocksTexture;
	}
}
