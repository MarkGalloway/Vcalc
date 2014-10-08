package symbol2.vcalc;

public class SymbolTable {

    public GlobalScope globals;
    
    public SymbolTable() {
        this.globals = new GlobalScope(null);
        initTypeSystem();
    }
    
    protected void initTypeSystem() {
        globals.define(new BuiltInTypeSymbol("int"));
        globals.define(new BuiltInTypeSymbol("vector"));
    }

}
