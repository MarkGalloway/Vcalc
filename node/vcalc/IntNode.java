package node.vcalc;

import symbol.vcalc.IntType;
import symbol.vcalc.VcalcValue;

public class IntNode implements VcalcNode {
    private int value;
    
    public IntNode(int value) {
        this.value = value;
    }
    
    @Override
    public VcalcValue evaluate() {
        return new VcalcValue(new IntType(value));
    }
}
