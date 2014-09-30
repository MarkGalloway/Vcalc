package node.vcalc;

import scope.vcalc.Scope;

public class VarNode implements VcalcNode {
    private String id;
    private Scope scope;
    
    public VarNode(String id, Scope scope) {
        this.id = id;
        this.scope = scope;
    }
    
    @Override
    public int evaluate() {
        return scope.resolve(id);
    }
}
