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
		if (value instanceof IntType)
			throw new RuntimeException("IntTypes cannot be promoted without VectorTypes");
		return (VectorType)value;
	}
	
	public VectorType promoteToVector(VcalcValue<?> binaryOther) {
		return promoteToVector(binaryOther, 0);
	}
	
	public VectorType promoteToVector(VcalcValue<?> binaryOther, int padwith) {
		if (value instanceof IntType) {
			if (binaryOther.isInt())
				throw new RuntimeException("IntTypes require VectorTypes for Promotion");
			
			return new VectorType(asInt(), binaryOther.asVector().getSize());
		}
		if (binaryOther.isVector()) {
			int otherSize = binaryOther.asVector().getSize();
			int thisSize = asVector().getSize();
				if ( otherSize > thisSize) {
					return new VectorType((VectorType)value, otherSize, padwith);
				}
		}
		return (VectorType) value;
	}
	
	public boolean isInt() {
		return (value instanceof IntType);
	}
	
	public boolean isVector() {
		return (value instanceof VectorType);
	}
}
