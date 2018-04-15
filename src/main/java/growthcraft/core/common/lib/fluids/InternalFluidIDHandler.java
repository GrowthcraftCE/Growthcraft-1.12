package growthcraft.core.common.lib.fluids;

import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Fluid identification has changed to strings for never MC version.
 * But an internal ID handling is still necessary, to transfer IDs between server & client.<br/>
 * 
 * <b>Warning: IDs are internal! Strings should be used for NBT instead.</b>
 */
public class InternalFluidIDHandler {
	private Map<Fluid, Integer> fluidToId = null;
	private HashMap<Integer, Fluid> idToFluid = null;
	
	private void maybeInitFluidMapping() {
		if( fluidToId != null )
			return;
		
		fluidToId = FluidRegistry.getRegisteredFluidIDs();
		idToFluid = new HashMap<Integer, Fluid>();
		
		for(  Map.Entry<Fluid, Integer> entry : fluidToId.entrySet() )
			idToFluid.put(entry.getValue(), entry.getKey());
	}
	
	public Fluid getFluidByInternalID(int ID) {
		maybeInitFluidMapping();
		return idToFluid.get(ID); 
	}
	
	public int getInternalIDByFluid(Fluid fluid) {
		maybeInitFluidMapping();
		return fluidToId.get(fluid);
	}
	
}
