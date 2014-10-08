package symbol2.vcalc;

public interface Scope {
    public ScopeType getScopeType();
    public Scope getEnclosingScope();
    public void define(Symbol sym);
    public Symbol resolve(String name);
}
