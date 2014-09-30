package scope.vcalc;

import java.util.Map;
import java.util.HashMap;

public class Scope {
    public Map<String,Integer> variables;
    
    public Scope() {
        variables = new HashMap<String, Integer>();
    }
    
    public void assign(String id, int value) {
        variables.put(id, value);
    }
    
    public int resolve(String id) {
        if (!variables.containsKey(id)) {
            throw new RuntimeException("Variable "+ id +" not declared...");
        }
        return variables.get(id);
    }
}
