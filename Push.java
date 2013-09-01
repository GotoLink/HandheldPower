package assets.handheldpiston;

import java.util.EnumSet;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
@Mod(modid = "handheldpiston", name="Handheld Piston Mod", version="0.1")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class Push implements ITickHandler
{
	@SidedProxy(clientSide="assets.handheldpiston.ClientProxy",serverSide="assets.handheldpiston.CommonProxy")
	public static CommonProxy proxy;
	
    public static int handHeldPistonID = 2030,stickyHandHeldPistonID = 2031,redstoneRemoteID = 2032;
	public static int range = 2;
	public static int maxPowerTime = 40;
	
    public static int redstoneRemoteBlockID = 175;
    public static Item pusher,stickyPusher,powerer;
    public static Block airPower;
    //public AirPowering power;
    public static int i1,j1,k1;
    public static boolean flag = false;
    public static int powerTime = 0;
    @EventHandler
    public void configLoad(FMLPreInitializationEvent event)
    {
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	config.load();
    	handHeldPistonID = config.getItem("HandheldPistonID", handHeldPistonID).getInt();
    	stickyHandHeldPistonID = config.getItem("StickyHandheldPistonID", stickyHandHeldPistonID).getInt();
    	redstoneRemoteID = config.getItem("RedstoneRemoteID", redstoneRemoteID).getInt();
    	range = config.get("redstone remote", "Power", range, "The power the remote brings to the block.").getInt();
    	maxPowerTime = config.get("redstone remote", "MaxPoweringDelay", maxPowerTime, "The maximum amount of time the block is powered.").getInt();
    	if(config.hasChanged())
    		config.save();
    }
    
    @EventHandler
    public void load(FMLInitializationEvent e)
    {
        pusher = new ItemPusher(handHeldPistonID,false).setUnlocalizedName("Pusher");
        stickyPusher = new ItemPusher(stickyHandHeldPistonID,true).setUnlocalizedName("StickyPusher");
        powerer = new ItemPowerer(redstoneRemoteID).setUnlocalizedName("Powerer").setCreativeTab(CreativeTabs.tabRedstone);
        airPower = new BlockAirPower(redstoneRemoteBlockID,range).setUnlocalizedName("AirPower").setHardness(-1F);
        LanguageRegistry.instance().addName(pusher, "Handheld Piston");
        LanguageRegistry.instance().addName(stickyPusher, "Sticky Handheld Piston");
        LanguageRegistry.instance().addName(powerer, "Redstone Remote");
        GameRegistry.addRecipe(new ItemStack(pusher, 1, 0), new Object[]
                {
                    " ! ", " @ ", " # ", '!', Block.planks, '@', Item.ingotIron, '#', Block.cobblestone
                });
        GameRegistry.addRecipe(new ItemStack(stickyPusher, 1, 0), new Object[]
                {
                    "!", "@", Character.valueOf('!'), Item.slimeBall, '@', pusher
                });
        GameRegistry.addRecipe(new ItemStack(powerer, 1, 0), new Object[]
                {
                    " ! ", "@#@", " @ ", '!', Item.diamond, '@', Item.ingotIron, '#', Item.redstone
                });
        TickRegistry.registerTickHandler(this, Side.SERVER);
        EntityRegistry.registerModEntity(EntityMovingPushBlock.class, "moving block", 1, this, 20, 1, true);
        proxy.register();
    }

    public static void newAirPower(World world1, int i, int j, int k)
    {
        if (flag)
        {
            world1.setBlockToAir(i1, j1, k1);
            flag = false;
        }
        i1 = i;
        j1 = j;
        k1 = k;
        flag = true;
        powerTime = 0;
    }

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if (tickData[0] instanceof World && flag)
        {
            if (powerTime >= maxPowerTime)
            {
            	((World)tickData[0]).setBlockToAir(i1, j1, k1);
                ((World)tickData[0]).notifyBlocksOfNeighborChange(i1, j1, k1, 0);
                powerTime = 0;
                flag = false;
            }
            else
            {
                powerTime++;
            }
        }
	}
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {}
	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.WORLD);
	}
	@Override
	public String getLabel() {
		return null;
	}
}
