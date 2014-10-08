package symbol2.vcalc;

public class Symbol {
    public String name;
    public Type type;
    
    public Symbol(String name) {
        this.name = name;
    }
    
    public Symbol(String name, Type type) {
        this(name);
        this.type = type;
    }
    
    public String getName() {
        return name;
    }
}
