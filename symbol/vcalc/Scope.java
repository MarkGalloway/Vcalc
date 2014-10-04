package symbol.vcalc;

import java.util.Map;
import java.util.HashMap;

public class Scope {	

    private ScopeType type;
    private Scope parentScope;
    private Map<String, Symbol> symbolMap = new HashMap<String, Symbol>();
    
	private enum ScopeType {
	    GLOBAL,
	    LOCAL;
	}
	
	// constructor for base global scope
	public Scope() {
		this.parentScope = null;
		this.type = ScopeType.GLOBAL;
	}

	// constructor for local scopes
    public Scope(Scope parentScope) {
        this.parentScope = parentScope;
        this.type = ScopeType.LOCAL;
    }
    
    //define a symbol in the current scope
    public void assign(String id, VcalcValue valueType) {
        Symbol symbol = new Symbol(id, valueType, this);
        symbolMap.put(symbol.getId(), symbol);
    }
    
    
    // resolve a symbol in the current scope
    // if not found continue with outer scopes
    private Symbol resolve(String id) {
        Symbol symbol = symbolMap.get(id);
        
        if (symbol != null) 
        	return symbol;
        
        if (parentScope != null) 
        	return parentScope.resolve(id);
        
        throw new RuntimeException("Variable "+ id +" not declared...");
    }

    // get parent scope
    public Scope getParentScope() {
        return parentScope;
    }

    public String toString() {
        return symbolMap.keySet().toString();
    }
}
