package symbol.vcalc;

public class IntType {

    private final int value;
    
    public IntType(int value) {
        super();
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    @Override
    public String toString() {
    	return String.valueOf(value);
    }
}
