package symbol.vcalc;

public class Symbol {

    private String id;
    private VcalcValue valueType;
    private Scope scope;

    public Symbol(String id, VcalcValue valueType, Scope scope) {
        this.id = id;
        this.valueType = valueType;
        this.scope = scope;
    }

    public String getId() {
        return id;
    }

    public VcalcValue getValueType() {
        return valueType;
    }

    public Scope getScope() {
        return scope;
    }

    public String toString() {
        return getId();
    }
}