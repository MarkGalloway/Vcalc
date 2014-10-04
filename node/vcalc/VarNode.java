package node.vcalc;

import scope.vcalc.Scope;
import symbol.vcalc.VcalcValue;

public class VarNode implements VcalcNode {
    private String id;
    private Scope scope;
    
    public VarNode(String id, Scope scope) {
        this.id = id;
        this.scope = scope;
    }
    
    @Override
    public VcalcValue evaluate() {
        //return scope.resolve(id);
        throw new RuntimeException("VARS NOT IMPLEMENTED"); //TODO: hook up to Tamara symbol table stuff
    }
}
