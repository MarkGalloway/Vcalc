package symbol2.vcalc;

import ast.vcalc.VcalcAST;

public class Symbol {
    public String name;
    public Type type; // Symbol type
    public Scope scope;      // Containing Scope
    public VcalcAST def;    // Location in AST of ID node
    
    public Symbol(String name) {
        this.name = name;
    }
    
    public Symbol(String name, Type type) {
        this(name);
        this.type = type;
    }
    
    public String getName() {
        return name;
    }
    
    public String toString() {
        String s = "";
        if ( scope!=null ) s = "Scope: " +scope.getScopeType()+".";
        if ( type!=null ) return '<'+s+getName()+"  TYPE:"+type+'>' + "\n";
        return s+getName();
    }
}
