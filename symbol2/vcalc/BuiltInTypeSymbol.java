package symbol2.vcalc;

public class BuiltInTypeSymbol extends Symbol implements Type {
    
    int typeIndex;
    
    public BuiltInTypeSymbol(String name, int typeIndex) {
        super(name);
        this.typeIndex = typeIndex;
    }
    
    public int getTypeIndex() { 
        return typeIndex; 
    }
}
