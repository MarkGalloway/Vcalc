package node.vcalc;

import symbol.vcalc.VcalcValue;
import symbol.vcalc.VectorType;
import symbol.vcalc.IntType;

public class RangeNode implements VcalcNode {

    private final VcalcNode op1;
    private final VcalcNode op2;
    
    public RangeNode(VcalcNode op1, VcalcNode op2) {
        this.op1 = op1;
        this.op2 = op2;
    }

	@Override
    public VcalcValue<VectorType> evaluate() {
        VcalcValue<?> low = op1.evaluate();
        VcalcValue<?> high = op2.evaluate();
        
        if(!low.isInt() && !high.isInt()) {
            throw new RuntimeException("Range operator can only accept integer arguments." +
                    "Received " + low  + " and " + high);
        }
        return new VcalcValue<VectorType>(new VectorType(low.asInt().getValue(), high.asInt().getValue()));
    }
}
