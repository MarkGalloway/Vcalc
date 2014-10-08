tree grammar Defined;

options {
  language = Java;
  tokenVocab = Vcalc;
  ASTLabelType = VcalcAST;
}


@header {
  import symbol2.vcalc.*;
  import ast.vcalc.*;
}

@members {
  SymbolTable symTable;
  Scope currentScope;
  
  public Defined(CommonTreeNodeStream nodestream, SymbolTable symTable){
    this(nodestream);
    this.symTable = symTable;
    currentScope = symTable.globals;
  }
}

program 
  : ^(PROGRAM declaration* statement*)
  ;

declaration
  : ^(VAR type ID expression)
    { 
      Symbol s = currentScope.resolve($ID.text);
      if(s != null) {
        throw new RuntimeException("Variable " + $ID.text + " declared twice in the same scope.");
      }

      VariableSymbol vs = new VariableSymbol($ID.text, $type.type);
      vs.def = $ID;            // track AST location of def's ID
      $ID.symbol = vs;         // track in AST
      $ID.scope = currentScope; //track scope
      currentScope.define(vs);
    }
  ;

type returns [Type type]
@init {
  VcalcAST t = (VcalcAST)input.LT(1);
}
@after {
    t.symbol = currentScope.resolve(t.getText());
    t.scope = currentScope;
    $type = (Type)t.symbol;
}
  : Int 
  | Vector
  ;
 
statement
  : assignment
  | ifStat
  | loop
  | print
  ;

assignment
  : ^('=' ID expression) 
    {
      VariableSymbol vs = (VariableSymbol)currentScope.resolve($ID.text);
      if(vs == null) {
        throw new RuntimeException("Variable" + $ID.text + "must be declared before being used in assignment.");
      }
      $ID.symbol = vs; // track in AST
      $ID.scope = currentScope; // track scope
      
    }
  ;

ifStat
  : ^(IF expression ^(SLIST statement*))
  ;
  
loop
  : ^(LOOP expression ^(SLIST statement*))
  ;

print
  : ^(PRINT expression) 
  ;

expression
  : ^(INDEX expression expression)
  | ^('==' expression expression)
  | ^('!=' expression expression)
  | ^('<' expression expression)
  | ^('>' expression expression)
  | ^('+' expression expression)
  | ^('-' expression expression)
  | ^('*' expression expression)
  | ^('/' expression expression)
  | ^('..' expression expression)
  | ID  { 
          Symbol s = currentScope.resolve($ID.text); 
          if(s == null) throw new RuntimeException("Unknown Variable " + $ID.text + ". Variables must be declared before use in Vcalc."); 
          //$ID.scope = currentScope; I think this is bad. What if we reference a global from local scope? fucks it up
        }  
  | INTEGER
  | ^(GENERATOR ID {
                    currentScope = new LocalScope(currentScope); //push scope
                    VariableSymbol vs = new VariableSymbol($ID.text, (Type)currentScope.resolve("int"));
                    vs.def = $ID;            // track AST location of def's ID
                    $ID.symbol = vs;         // track in AST
                    $ID.scope = currentScope; // track scope
                    currentScope.define(vs);
                   } 
       expression expression 
                   {
                    currentScope = currentScope.getEnclosingScope(); //pop scope
                   }
     )
  | ^(FILTER ID {
                 currentScope = new LocalScope(currentScope); //push scope
                 VariableSymbol vs = new VariableSymbol($ID.text, (Type)currentScope.resolve("int"));
                 vs.def = $ID;            // track AST location of def's ID
                 $ID.symbol = vs;         // track in AST
                 $ID.scope = currentScope; // track scope
                 currentScope.define(vs);
                } 
       expression expression 
                {
                 currentScope = currentScope.getEnclosingScope(); //pop scope
                }
     )
  ;
  
