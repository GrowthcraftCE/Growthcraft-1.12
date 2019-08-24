package growthcraft.bees.common.tileentity.device;

import growthcraft.bees.common.lib.config.BeesRegistry;
import growthcraft.bees.common.lib.config.IFlowerBlockEntry;
import growthcraft.bees.common.tileentity.TileEntityBeeBox;
import growthcraft.bees.shared.config.GrowthcraftBeesConfig;
import growthcraft.core.shared.block.BlockFlags;
import growthcraft.core.shared.tileentity.device.DeviceBase;
import growthcraft.core.shared.utils.RandomUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockFlowerPot.EnumFlowerType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DeviceBeeBox extends DeviceBase {
    // Temp variable used by BlockBeeBox for storing flower lists
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<List> flowerList = new ArrayList<List>();
    private final float honeyCombSpawnRate = GrowthcraftBeesConfig.beeBoxHoneyCombSpawnRate;
    private final float honeySpawnRate = GrowthcraftBeesConfig.beeBoxHoneySpawnRate;
    private final float beeSpawnRate = GrowthcraftBeesConfig.beeBoxBeeSpawnRate;
    private final float flowerSpawnRate = GrowthcraftBeesConfig.beeBoxFlowerSpawnRate;
    private final int flowerRadius = GrowthcraftBeesConfig.beeBoxFlowerRadius;
    private final float bonus = GrowthcraftBeesConfig.beeBoxBonusMultiplier;
    private Random random = new Random();
    private int bonusTime;

    public DeviceBeeBox(TileEntityBeeBox te) {
        super(te);
    }

    protected TileEntityBeeBox getParentTile() {
        if (parent instanceof TileEntityBeeBox) {
            return (TileEntityBeeBox) parent;
        }
        return null;
    }

    public int getBonusTime(int t) {
        return bonusTime;
    }

    public void setBonusTime(int t) {
        this.bonusTime = t;
    }

    public boolean hasBonus() {
        return bonusTime > 0;
    }

    // for lack of a better name, can this BeeBox do any work?
    private boolean canDoWork() {
        BlockPos posUp = parent.getPos().up();
        if (getWorld().isRainingAt(posUp))
            return false;
        return getWorld().getLightFromNeighbors(posUp) >= 7;
    }

    private boolean isBlockFlower(IBlockState blockState) {
        return BeesRegistry.instance().isBlockFlower(blockState);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private List<List> gatherFlowersInRadius(World world, BlockPos pos, int checkSize, List<List> list) {
        final int i = pos.getX() - ((checkSize - 1) / 2);
        final int k = pos.getZ() - ((checkSize - 1) / 2);

        MutableBlockPos fpos = new MutableBlockPos();
        for (int xLoop = -checkSize; xLoop < checkSize; xLoop++) {
            for (int yLoop = -checkSize; yLoop < checkSize; yLoop++) {
                fpos.setPos(i + xLoop, pos.getY(), k + yLoop);
                if (!world.isAirBlock(fpos)) {
                    final IBlockState flower = world.getBlockState(fpos);
                    final Block flowerBlock = flower.getBlock();
                    if (flower != null) {
                        if (isBlockFlower(flower)) {
                            list.add(Arrays.asList(flower, flowerBlock.getMetaFromState(flower)));
                        }
                    }
                }
            }
        }
        return list;
    }

    private float calcGrowthRate(World world, BlockPos pos) {
        final int checkSize = 5;
        final int i = pos.getX() - ((checkSize - 1) / 2);
        final int k = pos.getZ() - ((checkSize - 1) / 2);
        float f = 1.0F;

        MutableBlockPos fpos = new MutableBlockPos();
        for (int loopx = -checkSize; loopx < checkSize; loopx++) {
            for (int loopz = -checkSize; loopz < checkSize; loopz++) {
                fpos.setPos(i + loopx, pos.getY(), k + loopz);

                final IBlockState flower = world.getBlockState(fpos);
                final Block flowerBlock = flower.getBlock();
                final IBlockState soil = world.getBlockState(fpos.down());
                float f1 = 0.0F;

                if (soil.getBlock() == Blocks.GRASS) {
                    //f1 = 1.0F;
                    f1 = 0.36F;

                    if (isBlockFlower(flower)) {
                        //f1 = 3.0F;
                        f1 = 1.08F;
                    }
                } else if (flower.getBlock() == Blocks.FLOWER_POT && flower.getValue(BlockFlowerPot.CONTENTS) != EnumFlowerType.EMPTY
						/*(world.getBlockMetadata(i + loopx, y, k + loopz) == 1 ||
					world.getBlockMetadata(i + loopx, y, k + loopz) == 2)*/) {
                    //f1 = 2.0F;
                    f1 = 0.72F;
                }

                f1 /= 4.0F;

                f += f1;
            }
        }

        final TileEntityBeeBox te = getParentTile();

        if (te != null) {
            final int bees = te.countBees();
            final float div = 2.0F - (0.015625F * bees);

            f /= div;

            if (te.hasBonus()) {
                f *= this.bonus;
            }
        }

        return f;
    }

    public float getGrowthRate() {
        return calcGrowthRate(getWorld(), parent.getPos());
    }

    public void update() {
        if (bonusTime > 0) bonusTime--;
    }

    // Honey Comb bias, if the value is less than 0, it means there are more empty combs than
    // filled, and that the device should focus on filling these combs before creating new ones
    // Otherwise if the value is greater than or equal to zero, the device should focus on creating
    // more empty honey combs
    protected int honeyCombBias() {
        final TileEntityBeeBox te = getParentTile();
        final int empty = te.countEmptyCombs();
        final int filled = te.countHoney();
        return filled - empty;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void updateTick() {
        final TileEntityBeeBox te = getParentTile();
        if (!canDoWork() || !te.hasBees()) return;

        final BlockPos pos = te.getPos();

        float f = getGrowthRate();

        if (!te.hasMaxBees()) {
            if (random.nextInt((int) (beeSpawnRate / f) + 1) == 0) {
                te.spawnBee();
            }
        }

        final int maxCombs = te.getHoneyCombMax();
        final int curCombs = te.countCombs();
        if (te.countHoney() < maxCombs) {
            final int bias = honeyCombBias();
            // If the bias is less than 0, then we should focus on filling with honey
            boolean shouldFill = bias < 0;
            if (bias != 0 && curCombs <= maxCombs) {
                // abs the bias, and then clamp it to a range of 6
                final int biasSpawn = Math.min(MathHelper.abs(bias), 6);
                // if the biasSpawn isn't invalid
                if (biasSpawn > 0) {
                    // the higher the bias, the less likely the operation will flip
                    if (random.nextInt(biasSpawn) == 0) {
                        // flip the operation
                        shouldFill = !shouldFill;
                    }
                }
            }
            if (shouldFill) {
                // try to fill a honey comb
                if (random.nextInt((int) (honeySpawnRate / f) + 1) == 0) {
                    te.fillHoneyComb();
                }
            } else {
                // try to spawn a honey comb
                if (random.nextInt((int) (honeyCombSpawnRate / f) + 1) == 0) {
                    te.spawnHoneyComb();
                }
            }
        }

        f = 7.48F / (2.0F - (0.015625F * te.countBees()));
        if (te.hasBonus()) {
            f *= bonus;
        }

        final int spawnRate = (int) (this.flowerSpawnRate / f) + 1;
        if (random.nextInt(spawnRate) == 0) {
            final int checkSize = flowerRadius;

            flowerList.clear();
            gatherFlowersInRadius(getWorld(), pos, checkSize, flowerList);

            if (!flowerList.isEmpty()) {
                BlockPos randomPos = new BlockPos(pos.getX() + random.nextInt(checkSize * 2) - checkSize, pos.getY(), pos.getZ() + random.nextInt(checkSize * 2) - checkSize);
                final List randomList = RandomUtils.sample(random, flowerList);

                if (randomList != null) {
                    final IBlockState blockState = (IBlockState) randomList.get(0);
                    final Block block = blockState.getBlock();
                    final int meta = (int) randomList.get(1);
                    final IFlowerBlockEntry entry = BeesRegistry.instance().getFlowerBlockEntry(block, meta);
                    if (entry != null) {
                        if (entry.canPlaceAt(getWorld(), randomPos)) {
                            getWorld().setBlockState(randomPos, block.getStateFromMeta(meta), BlockFlags.SYNC);
                        }
                    }
                }
            }
        }
    }

    public void updateClientTick() {
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.bonusTime = data.getInteger("bonus_time");
    }

    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("bonus_time", bonusTime);
    }

    @Override
    public boolean readFromStream(ByteBuf buf) {
        super.readFromStream(buf);
        this.bonusTime = buf.readInt();
        return false;
    }

    /**
     * @param buf - buffer to write to
     */
    @Override
    public boolean writeToStream(ByteBuf buf) {
        super.writeToStream(buf);
        buf.writeInt(bonusTime);
        return false;
    }
}