package symbol2.vcalc;

import java.util.HashMap;
import java.util.Map;

public class BaseScope implements Scope {
    private Map<String, Symbol> symbolMap = new HashMap<String, Symbol>();
    private String scopeName;
    private Scope enclosingScope;
    
    public BaseScope(String scopeName, Scope enclosingScope) {
        this.scopeName = scopeName;
        this.enclosingScope = enclosingScope;
    }
    
    @Override
    public String getScopeName() {
        return scopeName;
    }

    @Override
    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    @Override
    public void define(Symbol sym) {
        symbolMap.put(sym.getName(), sym);
    }

    @Override
    public Symbol resolve(String name) {
        Symbol symbol = symbolMap.get(name);
        
        if (symbol != null) return symbol;
        
        if (enclosingScope != null) 
            return enclosingScope.resolve(name);
        
        return null;
    }
}
