package assets.handheldpiston;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;

import static cpw.mods.fml.relauncher.Side.CLIENT;

@Mod(modid = "handheldpiston", name = "Handheld Piston Mod", version="$version")
public final class Push {
	public static int range = 2, maxPowerTime = 40;
	public static Item pusher, stickyPusher, powerer;
	public static Block airPower;

	@EventHandler
	public void configLoad(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		range = config.getInt("Power", "redstone remote", range, 1, 15, "The power the remote brings to the block.");
		maxPowerTime = config.get("redstone remote", "MaxPoweringDelay", maxPowerTime, "The maximum amount of time the block is powered.").setMinValue(1).getInt();
		if (config.hasChanged())
			config.save();
        pusher = new ItemPusher(false).setUnlocalizedName("Pusher");
        stickyPusher = new ItemPusher(true).setUnlocalizedName("StickyPusher");
        powerer = new ItemPowerer().setMaxStackSize(1).setMaxDamage(1000).setUnlocalizedName("Powerer").setTextureName("handheldpiston:RedstoneRemote").setCreativeTab(CreativeTabs.tabRedstone);
        airPower = new BlockAirPower().setBlockName("AirPower").setHardness(-1F);
        GameRegistry.registerBlock(airPower, "PoweredBlock");
        GameRegistry.registerItem(pusher, "HandheldPiston");
        GameRegistry.registerItem(stickyPusher, "StickyHandheldPiston");
        GameRegistry.registerItem(powerer, "RedstoneRemote");
        if(event.getSourceFile().getName().endsWith(".jar") && event.getSide().isClient()){
            try {
                Class.forName("mods.mud.ModUpdateDetector").getDeclaredMethod("registerMod", ModContainer.class, String.class, String.class).invoke(null,
                        FMLCommonHandler.instance().findContainerFor(this),
                        "https://raw.github.com/GotoLink/HandheldPower/master/update.xml",
                        "https://raw.github.com/GotoLink/HandheldPower/master/changelog.md"
                );
            } catch (Throwable e) {
            }
        }
	}

	@EventHandler
	public void load(FMLInitializationEvent e) {
		GameRegistry.addRecipe(new ItemStack(pusher, 1, 0), " ! ", " @ ", " # ", '!', Blocks.planks, '@', Items.iron_ingot, '#', Blocks.cobblestone);
		GameRegistry.addRecipe(new ItemStack(stickyPusher, 1, 0), "!", "@", '!', Items.slime_ball, '@', Push.pusher);
		GameRegistry.addRecipe(new ItemStack(powerer, 1, 0), " ! ", "@#@", " @ ", '!', Items.diamond, '@', Items.iron_ingot, '#', Items.redstone);
		if (e.getSide().isClient()) {
			addRenderers();
		}
		EntityRegistry.registerModEntity(EntityMovingPushBlock.class, "moving block", 1, this, 80, 3, false);
	}

	@SideOnly(CLIENT)
	private void addRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityMovingPushBlock.class, new RenderMovingPushBlock());
	}

    @EventHandler
    public void remap(FMLMissingMappingsEvent event){
        for(FMLMissingMappingsEvent.MissingMapping missingMapping:event.get()){
            switch(missingMapping.type){
                case ITEM:
                    missingMapping.remap(GameData.getItemRegistry().getObject(missingMapping.name.replace(" ", "")));
                    break;
                case BLOCK:
                    missingMapping.remap(GameData.getBlockRegistry().getObject(missingMapping.name.replace(" ", "")));
                    break;
            }
        }
    }
}
