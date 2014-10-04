package symbol.vcalc;

public class IntType extends VcalcValue {

    private final int value;
    
    public IntType(int value) {
        super();
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
}