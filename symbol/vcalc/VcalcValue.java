package symbol.vcalc;

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
	
	public VectorType getLonger(VectorType otherValue) {
		if (!(value instanceof VectorType))
			throw new RuntimeException("ReturnLonger check can only be done on two Vectors.");
		
		if (((VectorType)value).getSize() > otherValue.getSize())
			return  (VectorType)value;
		return otherValue;
	}
	
	public boolean isInt() {
		return (value instanceof IntType);
	}
	
	public boolean isVector() {
		return (value instanceof VectorType);
	}
}
