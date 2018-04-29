package growthcraft.core.shared.utils;

public class Easing
{
	// The original idea was to have one for floats and another for doubles
	// EasingTemplate<float> and EasingTemplate<double> respectively
	// Unfortunately, java's type system sucks, so we'll have to settle for
	// doubles, or I duplicate code...
	public static EasingTemplate d = new EasingTemplate();

	private Easing() {}
}
