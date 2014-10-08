package symbol2.vcalc;

public class LocalScope extends BaseScope {

    public LocalScope(Scope enclosingScope) {
        super("LOCAL", enclosingScope);
    }

}
