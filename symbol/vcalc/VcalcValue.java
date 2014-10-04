package symbol.vcalc;

import java.util.ArrayList;

public class VcalcValue {
	protected Object value;
	
	public VcalcValue(Object value) {
		// value is a Vector or IntValue
		this.value = value;
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
