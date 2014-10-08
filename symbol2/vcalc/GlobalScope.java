package symbol2.vcalc;

public class GlobalScope extends BaseScope {

    public GlobalScope(Scope enclosingScope) {
        super("GLOBAL", enclosingScope);
    }

}
