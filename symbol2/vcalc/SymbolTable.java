package symbol2.vcalc;

import ast.vcalc.VcalcAST;

public class SymbolTable {

    public static final int tINT = 0;
    public static final int tVECTOR = 1;

    public static final BuiltInTypeSymbol _int =
        new BuiltInTypeSymbol("int", tINT);
    public static final BuiltInTypeSymbol _vector =
        new BuiltInTypeSymbol("vector", tVECTOR);

//    public CymbolListener listener =
//        new CymbolListener() {
//            public void info(String msg) { System.out.println(msg); }
//            public void error(String msg) { System.err.println(msg); }
//        };

    
    public static final Type[] indexToType = {
        null, _int, _vector
    };
    
    public GlobalScope globals;
    
    public SymbolTable() {
        this.globals = new GlobalScope(null);
        initTypeSystem();
    }
    
    protected void initTypeSystem() {
        for (Type t : indexToType) {
            if ( t!=null ) globals.define((BuiltInTypeSymbol)t);
        }
    }
    
    public Type arithmetic(VcalcAST a, VcalcAST b)   { 
        if(a.evalType == b.evalType) 
            return a.evalType;
        else 
            return _vector;
    }
    public Type relational(VcalcAST a, VcalcAST b) { 
        if(a.evalType == b.evalType) 
            return a.evalType;
        else 
            return _vector;
    }
    public Type equality(VcalcAST a, VcalcAST b)  { 
        if(a.evalType == b.evalType) 
            return a.evalType;
        else 
            return _vector;
    }
    
    public Type index(VcalcAST a, VcalcAST b) {
        if(a.evalType == _int)
            throw new RuntimeException("Index can only be used on vector types. Line: " + a.getLine());
        
        return _int;
    }
    
    public Type range(VcalcAST a, VcalcAST b) {
        if(a.evalType != _int || b.evalType != _int) {
            throw new RuntimeException("Range operator operands must evaluate to integers. Line: " + a.getLine());
        }
        return _vector;
    }
    
    public Type generator(VcalcAST a, VcalcAST b) {
        if(a.evalType != _vector) 
            throw new RuntimeException("Generator Domain expression must evaluate to vector. Line: " + a.getLine());
        if(a.evalType != _int) 
            throw new RuntimeException("Generator Right Hand Side expression must evaluate to integer. Line: " + b.getLine());
        
        return _vector;
    }
    
    public Type filter(VcalcAST a, VcalcAST b) {
        if(a.evalType != _vector) 
            throw new RuntimeException("Filter Domain expression must evaluate to vector. Line: " + a.getLine());
        if(a.evalType != _int) 
            throw new RuntimeException("Filter Right Hand Side expression must evaluate to integer. Line: " + b.getLine());
        
        return _vector;
    }
    
    public Type assignment(Type type, VcalcAST b) {
        if(type != b.evalType)
            throw new RuntimeException("Assignment types must match. Line: " + b.getLine());
        
        return type;
    }
    
    public Type declaration(Type type, VcalcAST b) {
        if(type != b.evalType)
            throw new RuntimeException("Declaration types must match. Line: " + b.getLine());
        
        return type;
    }
    
    public Type conditional(VcalcAST a) {
        if(a.evalType != _int)
            throw new RuntimeException("Conditional expression must evaluate to integer. Line: " + a.getLine());
        
        return _int;
    }
    
    public Type print(VcalcAST a) {
        return a.evalType;
    }

}
