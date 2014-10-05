package node.vcalc;

import java.util.ArrayList;

import errors.vcalc.InvalidAssignmentException;
import symbol.vcalc.IntType;
import symbol.vcalc.VcalcValue;
import symbol.vcalc.VectorType;

public class IndexNode implements VcalcNode {
    VcalcNode expression;
    VcalcNode element;
    int line;
    String exprText;
    
    public IndexNode(VcalcNode expression, VcalcNode element, int line, String text) {
        this.expression = expression;
        this.element = element;
        this.line = line;
        this.exprText = text;
    }

    @Override
    public VcalcValue<?> evaluate() throws InvalidAssignmentException {
        VcalcValue<?> expr = expression.evaluate();
        VcalcValue<?> index = element.evaluate();
        
        if(!expr.isVector()) {
            throw new RuntimeException("could not index int '" + exprText + "' on line " + this.line);
        }
        
        // vectors indexed with IntTypes return IntTypes
        if(!index.isVector()){
            int result = expr.asVector().getElement(index.asInt().getValue());
            return new VcalcValue<IntType>(new IntType(result));
        }
        
        // vectors indexed with VectorTypes return VectorTypes
        
        VectorType vectorExpr = expr.asVector();
        VectorType vectorIndex = index.asVector();
        VectorType newVector = new VectorType();
        
        for(Integer i : vectorIndex.getVector()) {
        	newVector.addElement(vectorExpr.getElement(i));
        }
        
        return new VcalcValue<VectorType>(newVector);
        

    }

}
