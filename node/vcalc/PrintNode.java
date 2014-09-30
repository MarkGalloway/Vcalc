package node.vcalc;

public class PrintNode implements VcalcNode {
    
    private final VcalcNode expr;
    
    public PrintNode(VcalcNode expr) {
        this.expr = expr;
    }
    
    @Override
    public int evaluate() {
        System.out.println(expr.evaluate());
        return 0;
    }
}
