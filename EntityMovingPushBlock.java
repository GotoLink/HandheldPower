package assets.handheldpiston;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityMovingPushBlock extends Entity
{
    public int blockID;
    public int moveTime;
    public double startPosX;
    public double startPosY;
    public double startPosZ;
    public int direction;
    public int metadata;
    public double pushpull;

    public EntityMovingPushBlock(World world)
    {
        super(world);
        moveTime = 0;
    }

    public EntityMovingPushBlock(World world, double d, double d1, double d2, int i, int j, int k, double d3)
    {
        this(world);
        blockID = i;
        preventEntitySpawning = true;
        setSize(0.98F, 0.98F);
        yOffset = height / 2.0F;
        setPosition(d, d1, d2);
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
        prevPosX = d;
        prevPosY = d1;
        prevPosZ = d2;
        direction = j;
        metadata = k;
        pushpull = d3;
    }
    @Override
    protected boolean canTriggerWalking()
    {
        return true;
    }
	@Override
    protected void entityInit()
    {
    }
	@Override
    public boolean canBeCollidedWith()
    {
        return true;
    }
	@Override
    public void onUpdate()
    {
        if (blockID == 0)
        {
            setDead();
            return;
        }
        moveTime++;
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        if (direction == 0)
        {
            motionY = pushpull;
            motionZ = 0.0D;
            motionX = 0.0D;
        }
        else if (direction == 1)
        {
            motionY = -pushpull;
            motionZ = 0.0D;
            motionX = 0.0D;
        }
        else if (direction == 2)
        {
            motionZ = pushpull;
            motionY = 0.0D;
            motionX = 0.0D;
        }
        else if (direction == 3)
        {
            motionZ = -pushpull;
            motionY = 0.0D;
            motionX = 0.0D;
        }
        else if (direction == 4)
        {
            motionX = pushpull;
            motionY = 0.0D;
            motionZ = 0.0D;
        }
        else if (direction == 5)
        {
            motionX = -pushpull;
            motionY = 0.0D;
            motionZ = 0.0D;
        }
        moveEntity(motionX, motionY, motionZ);
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(posY);
        int k = MathHelper.floor_double(posZ);
        if (moveTime == 0)
        {
            worldObj.playSoundAtEntity(this, "tile.piston.out", 0.5F, 1.2F);
        }
        else if (moveTime == 10)
        {
            setDead();
            worldObj.setBlock(i, j, k, blockID, metadata, 3);
        }
    }
	@Override
    protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setByte("Tile", (byte)this.blockID);
        nbttagcompound.setByte("Data", (byte)this.metadata);     
    }
	@Override
    protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        this.blockID = nbttagcompound.getByte("Tile") & 0xff;
        this.metadata = nbttagcompound.getByte("Data") & 255;    
    }
	@SideOnly(Side.CLIENT)
    public float getShadowSize()
    {
        return 0.0F;
    }
	@SideOnly(Side.CLIENT)
	public World getWorld()
	{
	    return this.worldObj;
	}
}
