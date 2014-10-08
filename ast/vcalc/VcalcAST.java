package ast.vcalc;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.Token;

import symbol2.vcalc.*;

public class VcalcAST extends CommonTree {
    public Scope scope;   // set by Def.g; ID lives in which scope?
    public Symbol symbol; // set by Types.g; point at def in symbol table
    public Type evalType; // The type of an expression; set by Types.g

    public VcalcAST() { }
    
    public VcalcAST(Token t) { 
        super(t);
    }

}
