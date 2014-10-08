tree grammar Defined;

options {
  language = Java;
  tokenVocab = Vcalc;
  ASTLabelType = CommonTree;
}


@header {
  import symbol2.vcalc.*;
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

      VariableSymbol vs = new VariableSymbol($ID.text, $type.tsym);
      currentScope.define(vs);
    }
  ;

type returns [Type tsym]
@after {$tsym = (Type)currentScope.resolve($text);
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
      VariableSymbol vs = (VariableSymbol)currentScope.resolve($ID.text); //throw exception
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
        }  
  | INTEGER
  | ^(GENERATOR ID {
                    currentScope = new LocalScope(currentScope); //push scope
                    VariableSymbol vs = new VariableSymbol($ID.text, (Type)currentScope.resolve("int"));
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
                 currentScope.define(vs);
                } 
       expression expression 
                {
                 currentScope = currentScope.getEnclosingScope(); //pop scope
                }
     )
  ;
  
