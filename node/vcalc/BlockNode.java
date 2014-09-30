package node.vcalc;
import java.util.ArrayList;
import java.util.List;


public class BlockNode implements VcalcNode {
    private List<VcalcNode> statements;
    
    public BlockNode() {
        statements = new ArrayList<VcalcNode>();
    }
    
    public void addStatement(VcalcNode statement) {
        statements.add(statement);
    }
    
    @Override
    public int evaluate() {
        for(VcalcNode stat : statements) {
            stat.evaluate();
        }
        return 0;
    }
}
