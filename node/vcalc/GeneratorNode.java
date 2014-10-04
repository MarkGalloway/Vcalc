package node.vcalc;

import symbol.vcalc.Scope;
import symbol.vcalc.SymbolTable;
import symbol.vcalc.VcalcValue;
import symbol.vcalc.VectorType;

public class GeneratorNode implements VcalcNode {

    private final String id;
    private final VcalcNode domainNode;
    private final VcalcNode exprNode;
    private final SymbolTable symTable;
    private final Scope localScope;
    
    
    public GeneratorNode(String id, VcalcNode domainNode, VcalcNode exprNode, SymbolTable symTable) {
        this.id = id;
        this.domainNode = domainNode;
        this.exprNode = exprNode;
        this.symTable = symTable;
        localScope = symTable.pushScope();
    }

    @Override
    public VcalcValue evaluate() {
        VcalcValue domainValue = domainNode.evaluate(); //get the domain vector
        
        if(!domainValue.isVector()) {
            throw new RuntimeException("The domain of a generator must be a vector or vector valued expression."
                    + "Received: " + domainValue.value);
        }
  
        VectorType domain = domainValue.asVector();
        VectorType newVector = new VectorType();
        
        for(Integer element : domain.getVector()) {
            // Update value of domain variable with corresponding vector variable
            new AssignmentNode(id, new IntNode(element), localScope).evaluate();
            //System.out.println(localScope.toString());
            
            // Evaluate RHS expression
            VcalcValue value = exprNode.evaluate();
            
            if(!value.isInt()) {
                throw new RuntimeException("The right hand side expression of a generator must only create integer values."
                        + "Received: " + value.value);
            }
            
            // Add result entry to new vector
            newVector.addElement(value.asInt().getValue());
        }
        symTable.popScope();
        return new VcalcValue(newVector);
    }

}
