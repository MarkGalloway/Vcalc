tree grammar Types;

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
    
    public Types(TreeNodeStream input, SymbolTable symTable) {
        this(input);
        this.symTable = symTable;
    }
}

program 
  : ^(PROGRAM declaration* statement*)
  ;

declaration
  : ^(VAR type ID expression)
    {
     VariableSymbol vs = (VariableSymbol) $ID.scope.resolve($ID.text);
     $start.evalType = symTable.declaration(vs.type, $expression.start);
    }
  ;

type
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
     VariableSymbol vs = (VariableSymbol) $ID.scope.resolve($ID.text);
     $start.evalType = symTable.assignment(vs.type, $expression.start);
    }
  ;

ifStat
  : ^(IF expression ^(SLIST statement*))
    {
      $start.evalType = symTable.conditional($expression.start);
    }
  ;
  
loop
  : ^(LOOP expression ^(SLIST statement*))
    {
      $start.evalType = symTable.conditional($expression.start);
    }
  ;

print
  : ^(PRINT expression)
    { 
      $start.evalType = $expression.start.evalType;
    }
  ;

expression returns [Type type]
@after{ $start.evalType = $type; }
  : ^(INDEX a=expression b=expression)   {$type = symTable.index($a.start, $b.start); }
  | binaryOps {$type = $binaryOps.type;}
  | ID {
        VariableSymbol vs = (VariableSymbol) $ID.scope.resolve($ID.text);
        $ID.symbol = vs;
        $type = vs.type;
       }
  | INTEGER {$type = SymbolTable._int;}
  | ^(GENERATOR ID a=expression b=expression) {$type=symTable.generator($a.start, $b.start);}
  | ^(FILTER ID a=expression b=expression)    {$type=symTable.filter($a.start, $b.start);}
  ;
  
binaryOps returns [Type type]
@after { $start.evalType = $type; }
    : ^(arithmetic a=expression b=expression) {$type=symTable.arithmetic($a.start, $b.start);}
    | ^(relational a=expression b=expression) {$type=symTable.relational($a.start, $b.start);}
    | ^(equality a=expression b=expression)   {$type=symTable.equality($a.start, $b.start);}
    | ^('..' a=expression b=expression)       {$type=symTable.range($a.start, $b.start);}
    ;


arithmetic :   '+' | '-' | '*' | '/' ;
relational:  '<' | '>' ;
equality:   '!=' | '==' ;