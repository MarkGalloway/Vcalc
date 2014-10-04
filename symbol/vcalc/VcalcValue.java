package symbol.vcalc;

import java.util.ArrayList;

public class VcalcValue<T> {
	public T value;
	
	public VcalcValue(T value) {
		// value is a Vector or IntValue
		this.value = value;
		if(!isInt() && !isVector()) {
		    throw new RuntimeException("Undefined data type. Int or Vector supported only.");
		}
	}

    public IntType asInt() {
    	if (value instanceof VectorType) {
    		throw new RuntimeException("VectorTypes cannot be promoted to IntTypes.");
    	}
    	return (IntType)value;
	}
	
	public VectorType asVector() {
		// To Do: Write promotion logic for this in IntType
		return (VectorType)value;
	}
	
	public boolean isInt() {
		return (value instanceof IntType);
	}
	
	public boolean isVector() {
		return (value instanceof VectorType);
	}
}
