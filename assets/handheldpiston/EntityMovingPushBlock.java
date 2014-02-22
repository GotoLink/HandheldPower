package assets.handheldpiston;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityMovingPushBlock extends Entity implements IEntityAdditionalSpawnData{
	public Block blockID;
	public short direction;
	public int metadata;
	public int moveTime;
	public double pushpull;
	public double startPosX;
	public double startPosY;
	public double startPosZ;

	public EntityMovingPushBlock(World world) {
		super(world);
		moveTime = 0;
	}

	public EntityMovingPushBlock(World world, double d, double d1, double d2, Block i, short j, int k, double d3) {
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
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (blockID == Blocks.air) {
			setDead();
			return;
		}
		moveTime++;
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		if (direction == 0 || direction == 1) {
			motionY = pushpull * (1 - 2 * direction);
			motionZ = 0.0D;
			motionX = 0.0D;
		} else if (direction == 2 || direction == 3) {
			motionZ = pushpull * (5 - 2 * direction);
			motionY = 0.0D;
			motionX = 0.0D;
		} else if (direction == 4 || direction == 5) {
			motionX = pushpull * (9 - 2 * direction);
			motionY = 0.0D;
			motionZ = 0.0D;
		}
		moveEntity(motionX, motionY, motionZ);
		if (moveTime == 1) {
			worldObj.playSoundAtEntity(this, "tile.piston.out", 0.5F, 1.2F);
		} else if (moveTime == 10) {
            int i = MathHelper.floor_double(posX);
            int j = MathHelper.floor_double(posY);
            int k = MathHelper.floor_double(posZ);
            if(!worldObj.isRemote){
                worldObj.setBlock(i, j, k, blockID, metadata, 3);
                setDead();
            }
		}
	}

	@Override
	protected boolean canTriggerWalking() {
		return true;
	}

	@Override
	protected void entityInit() {
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		this.blockID = Block.getBlockById(nbttagcompound.getInteger("Tile"));
		this.metadata = nbttagcompound.getByte("Data") & 255;
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setInteger("Tile", Block.getIdFromBlock(this.blockID));
		nbttagcompound.setByte("Data", (byte) this.metadata);
	}

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeInt(Block.getIdFromBlock(this.blockID));
        buffer.writeByte(this.metadata);
        buffer.writeDouble(this.pushpull);
        buffer.writeShort(this.direction);
    }

    @Override
    public void readSpawnData(ByteBuf data) {
        this.blockID = Block.getBlockById(data.readInt());
        this.metadata = data.readByte();
        this.pushpull = data.readDouble();
        this.direction = data.readShort();
    }
}
