package symbol.vcalc;

import java.util.Stack;

public class SymbolTable {

	private static SymbolTable symTable;
    private Stack<Scope> scopeStack;

    private SymbolTable() {
    	// global base scope
        Scope global = new Scope();
        
        // create a new base scope and add global
        scopeStack = new Stack<Scope>();
        scopeStack.push(global);
    }

    // singleton init method 
    // (we only want one symbol table kicking around at a time)
    public static SymbolTable getInstance() {
    	if (symTable == null) {
    		symTable = new SymbolTable();
    	}
    	return symTable;
    }

    public Scope pushScope() {
        Scope parentScope = scopeStack.peek();
        Scope scope = new Scope(parentScope);
        scopeStack.push(scope);
        return scope;
    }

    public void popScope() {
        scopeStack.pop();
    }
    
    public Scope getCurrentScope() {
    	return scopeStack.peek();
    }

    public String toString() {
        return scopeStack.toString();
    }
}