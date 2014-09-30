package node.vcalc;

public class IntNode implements VcalcNode {
    private int value;
    
    public IntNode(int value) {
        this.value = value;
    }
    
    @Override
    public int evaluate() {
        return value;
    }
}
