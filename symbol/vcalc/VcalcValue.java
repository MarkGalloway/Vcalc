package symbol.vcalc;

import java.util.ArrayList;

public class VcalcValue {
	private Object value;
	
	protected VcalcValue() {
        // Do nothing
    }
	
	public VcalcValue(Object value) {
		this.value = value;
		
		// value is a Vector or IntValue
		if(!isInt() && !isVector()) {
		    throw new RuntimeException("Undefined data type. Int or Vector supported only.");
		}
	}

    public IntType asInt() {
		return (IntType)this.value;
	}
	
	public VectorType asVector() {
		return (VectorType)this.value;
	}
	
	public boolean isInt() {
		return (value instanceof IntType);
	}
	
	public boolean isVector() {
		return (value instanceof VectorType);
	}
}
