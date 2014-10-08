package symbol.vcalc;

import java.util.ArrayList;

public class VectorType {

    private final ArrayList<Integer> value;
    
    //empty vector constructor
    public VectorType() {
        value = new ArrayList<Integer>();
    }
    
    //vector from intype constructor
    public VectorType(IntType inttype, int length) {
    	value = new ArrayList<Integer>();
    	int intvalue = inttype.getValue();
    	for (int i = length; i > 0; i--) {
    		value.add(intvalue);
    	}
    }
    
    //extended vector from vector constructor
    public VectorType(VectorType vectorType, int newLength, int padwith) {
    	value = new ArrayList<Integer>();
    	
    	for (int i = 0; i < vectorType.getSize(); i++) {
    		value.add(vectorType.getElement(i));
    	}
    	
    	for (int i = 0; i < (newLength-vectorType.getSize()); i++) {
    		value.add(padwith);
    	}
    }
    
    //range constructor
    public VectorType(int to, int from) {
        if(to > from) {
            throw new RuntimeException("The lower bound of the range operator cannot be greater than the upper bound." +
                    "Expected " + to + " to be less than " + from);
        }
        
        value = new ArrayList<Integer>();
        
        for(int i = to; i <= from; i++ ) {
            value.add(i);
        }
    }

    public int getElement(int index) {
    	if (index >= value.size() || index < 0) {
    		return 0;
    	}
    	
    	return value.get(index);
    }
    
    public void addElement(int element) {
        value.add(element);
    }
    
    public int getSize() {
        return value.size();
    }
    
    public ArrayList<Integer> getVector() {
        return value;
    }
    
	public VectorType getLonger(VectorType otherVector) {		
		if (getSize() > otherVector.getSize())
			return  this;
		return otherVector;
	}

    @Override
    public String toString() {
    	StringBuilder stringbuilder = new StringBuilder();
    	stringbuilder.append("[ ");
    	for (Integer element : getVector()) {
    		stringbuilder.append(element.toString());
    		stringbuilder.append(" ");
    	}
    	stringbuilder.append("]");
    	return stringbuilder.toString();
    }
	
}
