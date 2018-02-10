package growthcraft.grapes.items;

import growthcraft.grapes.Reference;
import growthcraft.grapes.api.definition.IGrapeType;
import growthcraft.grapes.common.blocks.BlockGrapeVineBase;
import growthcraft.grapes.handlers.EnumHandler;
import growthcraft.grapes.handlers.EnumHandler.GrapeTypes;
import growthcraft.grapes.init.GrowthcraftGrapesBlocks;
import growthcraft.grapes.utils.GrapeTypeUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.List;

public class ItemGrapeSeed extends ItemSeeds implements IPlantable {

    public ItemGrapeSeed(String unlocalizedName) {
        super(GrowthcraftGrapesBlocks.grapeVine0.getBlock(), Blocks.FARMLAND);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setHasSubtypes(true);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add(TextFormatting.GRAY + I18n.format("item.grape_seed.tooltip"));
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = player.getHeldItem(hand);
        net.minecraft.block.state.IBlockState state = worldIn.getBlockState(pos);
        if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack) && state.getBlock().canSustainPlant(state, worldIn, pos, EnumFacing.UP, this) && worldIn.isAirBlock(pos.up()))
        {
        	IGrapeType type = GrapeTypeUtils.getTypeByVariantID(GrapeTypes.values(), itemstack.getMetadata());
        	if( type == null )
        		return EnumActionResult.FAIL;
        	IBlockState plantState = getPlant(worldIn, pos).withProperty(BlockGrapeVineBase.SUBTYPE, type.getGrapeSubTypeID());

        	worldIn.setBlockState(pos.up(), plantState);
            itemstack.shrink(1);
            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.FAIL;
        }
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for ( int i = 0; i < EnumHandler.GrapeTypes.values().length; i++ ) {
        	EnumHandler.GrapeTypes type = EnumHandler.GrapeTypes.values()[i];
            subItems.add(new ItemStack(itemIn, 1, type.getVariantID()));
        }
    }

}
