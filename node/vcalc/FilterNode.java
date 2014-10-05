package node.vcalc;

import symbol.vcalc.Scope;
import symbol.vcalc.VcalcValue;
import symbol.vcalc.VectorType;

public class FilterNode implements VcalcNode {

    private final String id;
    private final VcalcNode domainNode;
    private final VcalcNode predNode;
    private final Scope localScope;
    
    public FilterNode(String id, VcalcNode domainNode, VcalcNode predNode, Scope scope) {
        this.id = id;
        this.domainNode = domainNode;
        this.predNode = predNode;
        this.localScope = scope;
    }

    @Override
    public VcalcValue<VectorType> evaluate() {
        VcalcValue<?> domainValue = domainNode.evaluate(); //get the domain vector
        
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
            VcalcValue<?> value = predNode.evaluate();
            
            if(!value.isInt()) {
                throw new RuntimeException("The right hand side expression of a filter must only evaluate to integer values."
                        + "Received: " + value.value);
            }
            
            int pred = value.asInt().getValue();
            
            
            if(pred != 0) {
                // Add result entry to new vector
                newVector.addElement(element);
            } 
        }
        return new VcalcValue<VectorType>(newVector);
    }

}
