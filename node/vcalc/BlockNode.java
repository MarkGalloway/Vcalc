package node.vcalc;
import java.util.ArrayList;
import java.util.List;

import symbol.vcalc.VcalcValue;


public class BlockNode implements VcalcNode {
    private List<VcalcNode> statements;
    
    public BlockNode() {
        statements = new ArrayList<VcalcNode>();
    }
    
    public void addStatement(VcalcNode statement) {
        statements.add(statement);
    }
    
    @Override
    public VcalcValue<?> evaluate() {
        for(VcalcNode stat : statements) {
            stat.evaluate();
        }
        return null; //TODO Fix this return value to return something more useful...
    }
}
