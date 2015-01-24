package assets.handheldpiston;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityMovingPushBlock extends Entity implements IEntityAdditionalSpawnData{
	public Block blockID;
	public int metadata;
	private short direction;
	private int moveTime;
	private float pushpull;
	private int maxMoveTime;
	public int startX, startY, startZ;

	public EntityMovingPushBlock(World world) {
		super(world);
		preventEntitySpawning = true;
		setSize(1.0F, 1.0F);
		noClip = true;
	}

	public EntityMovingPushBlock(World world, int d, int d1, int d2, Block i, short j, int k, float d3) {
		this(world);
		blockID = i;
		setLocationAndAngles(d + 0.5F, d1, d2 + 0.5F, 0F, 0F);
		startX = d;
		startY = d1;
		startZ = d2;
		direction = j;
		metadata = k;
		pushpull = d3;
		maxMoveTime = (int) (1 / MathHelper.abs(d3));
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 1.0F;
	}

	@Override
	public void onUpdate() {
		if (blockID == Blocks.air) {
			setDead();
			return;
		}
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		moveTime++;
		motionX = getXOffset()*pushpull;
		motionY = getYOffset()*pushpull;
		motionZ = getZOffset()*pushpull;
		moveEntity(motionX, motionY, motionZ);
		if (moveTime == 1) {
			worldObj.playSoundAtEntity(this, "tile.piston.out", 0.5F, 1.2F);
		}
		if (moveTime >= maxMoveTime) {
			if(!worldObj.isRemote) {
				int[] end = getEnd();
				worldObj.setBlock(end[0], end[1], end[2], blockID, metadata, 3);
			}
			setDead();
		}
	}

	private int[] getEnd(){
		int multi = (int) Math.signum(pushpull);
		return new int[]{startX+multi*getXOffset(), startY+(int)(multi*getYOffset()), startZ+multi*getZOffset()};
	}

	public double[] getMove(){
		int temp = moveTime*getXOffset();
		if(temp!=0){
			return new double[]{pushpull*temp - Math.signum(pushpull*temp)*0.5D - 0.5D, 0.0D, -0.5D};
		}else{
			temp = moveTime*getZOffset();
			if(temp!=0){
				return new double[]{-0.5D, 0.0D, pushpull*temp - Math.signum(pushpull*temp)*0.5D - 0.5D};
			}else{
				temp = (int) (moveTime*getYOffset());
				return new double[]{-0.5D, pushpull*temp - Math.signum(pushpull*temp)*0.5D, -0.5D};
			}
		}
	}

	public boolean isStart(int x, int y, int z){
		return x == startX && y == startY && z == startZ;
	}

	public int getXOffset(){
		if(direction > 3){
			return (9 - 2 * direction);
		}
		return 0;
	}

	public double getYOffset(){
		if(direction < 2){
			return (1 - 2 * direction);
		}
		return 0;
	}

	public int getZOffset(){
		if(direction == 2 || direction == 3){
			return (5 - 2 * direction);
		}
		return 0;
	}

	@Override
	public AxisAlignedBB getBoundingBox(){
		return boundingBox;
	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity entity){
		return entity.boundingBox;
	}

	@Override
	public boolean isBurning(){
		return false;
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	protected void entityInit() {
    }

	@Override
	public boolean isSneaking()
	{
		return false;
	}

	@Override
	public void setSneaking(boolean value)
	{
	}

	@Override
	public boolean isSprinting()
	{
		return false;
	}

	@Override
	public void setSprinting(boolean value)
	{
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount){
		return false;
	}

	@Override
	public boolean handleWaterMovement() {
		return false;
	}

	@Override
	public boolean handleLavaMovement(){
		return false;
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
        buffer.writeFloat(this.pushpull);
		buffer.writeShort(this.direction);
		buffer.writeInt(this.startX);
		buffer.writeByte(this.startY);
		buffer.writeInt(this.startZ);
    }

    @Override
    public void readSpawnData(ByteBuf data) {
        this.blockID = Block.getBlockById(data.readInt());
        this.metadata = data.readByte();
        this.pushpull = data.readFloat();
		this.maxMoveTime = (int) (1 / MathHelper.abs(this.pushpull));
        this.direction = data.readShort();
		this.startX = data.readInt();
		this.startY = data.readByte();
		this.startZ = data.readInt();
    }
}
