package symbol2.vcalc;

import java.util.LinkedHashMap;
import java.util.Map;

public class BaseScope implements Scope {
    private Map<String, Symbol> symbolMap = new LinkedHashMap<String, Symbol>();
    private ScopeType scopeType;
    private Scope enclosingScope;
    
    public BaseScope(ScopeType scopeType, Scope enclosingScope) {
        this.scopeType = scopeType;
        this.enclosingScope = enclosingScope;
    }
    
    @Override
    public ScopeType getScopeType() {
        return scopeType;
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
