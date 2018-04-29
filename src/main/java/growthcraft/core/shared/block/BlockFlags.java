package growthcraft.core.shared.block;

/**
 * Constant space for block flags, use these instead of magic numbers
 */
public final class BlockFlags
{
	// Cause the block to update
	public static final int BLOCK_UPDATE = 1;
	// Send change to clients
	public static final int SYNC = 2;
	// Stop the block from re-rendering
	public static final int SUPRESS_RENDER = 4;

	public static final int NONE = 0;
	public static final int UPDATE_AND_SYNC = BLOCK_UPDATE | SYNC;
	public static final int ALL = UPDATE_AND_SYNC | SUPRESS_RENDER;

	private BlockFlags() {}
}
