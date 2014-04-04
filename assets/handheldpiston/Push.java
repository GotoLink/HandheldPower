package assets.handheldpiston;

import static cpw.mods.fml.relauncher.Side.CLIENT;

import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = "handheldpiston", name = "Handheld Piston Mod", useMetadata = true)
public class Push {
	public static int range = 2;
	public static int maxPowerTime = 40;
	public static Item pusher, stickyPusher, powerer;
	public static Block airPower;
	public static int i1, j1, k1;
	public static boolean flag = false;
	public static int powerTime = 0;

	@EventHandler
	public void configLoad(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		range = config.get("redstone remote", "Power", range, "The power the remote brings to the block.").getInt();
		maxPowerTime = config.get("redstone remote", "MaxPoweringDelay", maxPowerTime, "The maximum amount of time the block is powered.").getInt();
		if (config.hasChanged())
			config.save();
        pusher = new ItemPusher(false).setUnlocalizedName("Pusher");
        stickyPusher = new ItemPusher(true).setUnlocalizedName("StickyPusher");
        powerer = new ItemPowerer().setUnlocalizedName("Powerer").setCreativeTab(CreativeTabs.tabRedstone);
        airPower = new BlockAirPower(range).setBlockName("AirPower").setHardness(-1F);
        GameRegistry.registerBlock(airPower, "Powered Block");
        GameRegistry.registerItem(pusher, "Handheld Piston");
        GameRegistry.registerItem(stickyPusher, "Sticky Handheld Piston");
        GameRegistry.registerItem(powerer, "Redstone Remote");
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
		FMLCommonHandler.instance().bus().register(this);
		EntityRegistry.registerModEntity(EntityMovingPushBlock.class, "moving block", 1, this, 20, 1, true);
	}

	@SubscribeEvent
	public void tickStart(TickEvent.WorldTickEvent event) {
		if (event.phase== TickEvent.Phase.START && event.side.isServer() && flag) {
			if (powerTime >= maxPowerTime) {
				event.world.setBlockToAir(i1, j1, k1);
				event.world.notifyBlockChange(i1, j1, k1, Blocks.air);
				powerTime = 0;
				flag = false;
			} else {
				powerTime++;
			}
		}
	}

	public static void newAirPower(World world1, int i, int j, int k) {
		if (flag) {
			world1.setBlockToAir(i1, j1, k1);
			flag = false;
		}
		i1 = i;
		j1 = j;
		k1 = k;
		flag = true;
		powerTime = 0;
	}

	@SideOnly(CLIENT)
	private static void addRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityMovingPushBlock.class, new RenderMovingPushBlock());
	}
}
