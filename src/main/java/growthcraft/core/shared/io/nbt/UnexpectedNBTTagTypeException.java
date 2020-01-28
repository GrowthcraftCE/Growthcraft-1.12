package growthcraft.core.shared.io.nbt;

/**
 * Exception thrown when a specific tag type is expected and another is encountered instead.
 */
public class UnexpectedNBTTagTypeException extends RuntimeException {
    public static final long serialVersionUID = 1L;

    public UnexpectedNBTTagTypeException(String msg) {
        super(msg);
    }

    public UnexpectedNBTTagTypeException() {
    }

    public static UnexpectedNBTTagTypeException createFor(Object expected, Object actual) {
        return new UnexpectedNBTTagTypeException("Wrong NBT Tag type `" + actual + "` (expected `" + expected + "`)");
    }
}
