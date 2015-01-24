package assets.handheldpiston;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public final class RenderMovingPushBlock extends Render {
	private RenderPushWorld oldRenderHelper;
	private EntityMovingPushBlock oldEntity;
	public RenderMovingPushBlock() {
		shadowSize = 0.5F;
		field_147909_c.flipTexture = false;
		field_147909_c.field_152631_f = false;
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		doRenderMovingPushBlock((EntityMovingPushBlock) entity, d, d1, d2, f1);
	}

	public void doRenderMovingPushBlock(EntityMovingPushBlock entitymovingpushblock, double d, double d1, double d2, float frame) {
		Block block = entitymovingpushblock.blockID;
		if (block != null) {
			bindEntityTexture(entitymovingpushblock);
			RenderHelper.disableStandardItemLighting();
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_CULL_FACE);
			if (Minecraft.isAmbientOcclusionEnabled())
			{
				GL11.glShadeModel(GL11.GL_SMOOTH);
			}
			else
			{
				GL11.glShadeModel(GL11.GL_FLAT);
			}

			Tessellator.instance.startDrawingQuads();
			double[] move = entitymovingpushblock.getMove();
			Tessellator.instance.setTranslation(d - entitymovingpushblock.startX + move[0], d1 - entitymovingpushblock.startY + move[1], d2 - entitymovingpushblock.startZ + move[2]);
			Tessellator.instance.setColorOpaque_F(1.0F, 1.0F, 1.0F);
			setRenderHelper(entitymovingpushblock);
			this.field_147909_c.renderBlockAllFaces(block, entitymovingpushblock.startX, entitymovingpushblock.startY, entitymovingpushblock.startZ);
			this.field_147909_c.blockAccess = null;
			Tessellator.instance.setTranslation(0.0D, 0.0D, 0.0D);
			Tessellator.instance.draw();
			RenderHelper.enableStandardItemLighting();
		}
	}

	private void setRenderHelper(EntityMovingPushBlock entitymovingpushblock){
		if(oldRenderHelper!=null && entitymovingpushblock.equals(oldEntity)){
			this.field_147909_c.blockAccess = oldRenderHelper;
		}else {
			this.oldRenderHelper = new RenderPushWorld(entitymovingpushblock);
			this.oldEntity = entitymovingpushblock;
			this.field_147909_c.blockAccess = this.oldRenderHelper;
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationBlocksTexture;
	}
}
