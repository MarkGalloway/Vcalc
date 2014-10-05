package symbol.vcalc;

import java.util.Map;
import java.util.HashMap;

public class Scope {	

    private ScopeType type;
    private Scope parentScope;
    private Map<String, Symbol> symbolMap = new HashMap<String, Symbol>();
    // for debugging
    private int id; 
    
	private enum ScopeType {
	    GLOBAL,
	    LOCAL;
	}
	
	// constructor for base global scope
	public Scope(int id) {
		this.parentScope = null;
		this.type = ScopeType.GLOBAL;
		this.id = id;
	}

	// constructor for local scopes
    public Scope(Scope parentScope, int id) {
        this.parentScope = parentScope;
        this.type = ScopeType.LOCAL;
        this.id = id;
    }
    
    //define a symbol in the current scope
    public void assign(String id, VcalcValue<?> valueType) {
        Symbol symbol = new Symbol(id, valueType, this);
        symbolMap.put(symbol.getId(), symbol);
    }
    
    
    // resolve a symbol in the current scope
    // if not found continue with outer scopes
    public Symbol resolve(String id) {
        Symbol symbol = symbolMap.get(id);
        
        if (symbol != null) 
        	return symbol;
        
        if (parentScope != null) 
        	return parentScope.resolve(id);
        
        throw new RuntimeException("Variable "+ id +" not declared...");
    }
    
    // check if variable has been declared in the current scope
    public boolean contains(String id) {
        Symbol symbol = symbolMap.get(id);
        
        if (symbol != null) 
        	return true;
        
        return false;
    }
    
    // check if variable has been declared in any scope
    public boolean anyContains(String id) {
        Symbol symbol = symbolMap.get(id);
        
        if (symbol != null) 
        	return true;
        
        if (parentScope != null) 
        	return parentScope.anyContains(id);
        
        return false;
    }

    // get parent scope
    public Scope getParentScope() {
        return parentScope;
    }
    
    public String toString() {
        return id + ":" + symbolMap.keySet().toString();
    }

	public ScopeType getType() {
		return type;
	}
}
