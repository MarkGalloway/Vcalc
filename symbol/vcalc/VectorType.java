package symbol.vcalc;

import java.util.ArrayList;

public class VectorType {

    private final ArrayList<Integer> value;
    
    public VectorType(int to, int from) {
        super();
        
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
        return value.get(index);
    }
    
    @Override
    public String toString() {
    	StringBuilder stringbuilder = new StringBuilder();
    	stringbuilder.append("[ ");
    	for (Integer element : value) {
    		stringbuilder.append(element.toString());
    		stringbuilder.append(" ");
    	}
    	stringbuilder.append("]");
    	return stringbuilder.toString();
    }
	
}
