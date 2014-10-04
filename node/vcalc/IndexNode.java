package node.vcalc;

import symbol.vcalc.IntType;
import symbol.vcalc.VcalcValue;

public class IndexNode implements VcalcNode {
    VcalcNode expression;
    VcalcNode element;
    
    public IndexNode(VcalcNode expression, VcalcNode element) {
        this.expression = expression;
        this.element = element;
    }

    @Override
    public VcalcValue<IntType> evaluate() {
        VcalcValue<?> expr = expression.evaluate();
        VcalcValue<?> index = element.evaluate();
        
        if(!expr.isVector()) {
            throw new RuntimeException("Index operator can only index Vectors or expressions that return vectors." +
                    "Could not index " + expr + ".");
        }
        
        int value = expr.asVector().getElement(index.asInt().getValue());
        
        return new VcalcValue<IntType>(new IntType(value));
    }

}
