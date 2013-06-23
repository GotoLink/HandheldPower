package mods.handheldpiston;

import net.minecraft.world.World;
@Deprecated
public class AirPowering
{
    public static int i1,j1,k1;
    public static boolean flag = false;
    public static int powerTime = 0;

    protected AirPowering()
    {
    }

    public static void newAirPower(World world, int i, int j, int k)
    {
        if (flag)
        {
            world.setBlockToAir(i1, j1, k1);
            flag = false;
        }
        i1 = i;
        j1 = j;
        k1 = k;
        flag = true;
        powerTime = 0;
    }

    public static void removedAirPower(World world, int i, int j, int k)
    {
    }
}
