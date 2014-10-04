package node.vcalc;

import symbol.vcalc.Scope;
import symbol.vcalc.VcalcValue;

public class VarNode implements VcalcNode {
    private String id;
    private Scope scope;
    
    public VarNode(String id, Scope scope) {
        this.id = id;
        this.scope = scope;
    }
    
	@Override
    public VcalcValue<?> evaluate() {
        return scope.resolve(id).getValueType();
    }
}
