package symbol.vcalc;

import java.util.ArrayList;

public class VectorType extends VcalcValue {

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
	
}
