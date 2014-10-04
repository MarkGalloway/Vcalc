package symbol.vcalc;

import java.util.Stack;

public class SymbolTable {

    private Stack<Scope> scopeStack;
    private int generatedIdCounter = 0;

    public SymbolTable() {
    	// global base scope
        Scope global = new Scope(genId());
        // create a new base scope and add global
        scopeStack = new Stack<Scope>();
        scopeStack.push(global);
    }

    public Scope pushScope() {
        Scope parentScope = scopeStack.peek();
        Scope scope = new Scope(parentScope, genId());
        scopeStack.push(scope);
        return scope;
    }

    public void popScope() {
        scopeStack.pop();
    }
    
    public Scope getCurrentScope() {
    	return scopeStack.peek();
    }
    
    private int genId() {
    	return ++generatedIdCounter;
    }

    public String toString() {
        return scopeStack.toString();
    }
}