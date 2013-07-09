package assets.handheldpiston;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy{

	@Override
	public void register(){
		RenderingRegistry.registerEntityRenderingHandler(EntityMovingPushBlock.class, new RenderMovingPushBlock());
	}
}
