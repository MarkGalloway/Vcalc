tree grammar Defined;

options {
  language = Java;
  tokenVocab = Vcalc;
  ASTLabelType = CommonTree;
}


@header {
  import symbol.vcalc.*;
}

@members {
  SymbolTable symTable;
  
  public Defined(CommonTreeNodeStream nodestream){
    super(nodestream);
    symTable = new SymbolTable();
  }
}

program 
  : ^(PROGRAM declaration* statement*)
  ;

declaration
  : ^(VAR type ID expression)
    { if(symTable.getCurrentScope().contains($ID.text)) { throw new RuntimeException("Variable " + $ID.text + " declared twice in the same scope!");}
      symTable.getCurrentScope().assign($ID.text, new VcalcValue(new IntType(0))); 
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
  | ^('..' op1=expression op2=expression)
  | ID { if(!symTable.contains($ID.text)) {throw new RuntimeException("Undeclared Variable " + $ID.text);}}
  | INTEGER
  | ^(GENERATOR ID {symTable.pushScope(); symTable.getCurrentScope().assign($ID.text, new VcalcValue(new IntType(0)));} expression expression {symTable.popScope();})
  | ^(FILTER ID {symTable.pushScope(); symTable.getCurrentScope().assign($ID.text, new VcalcValue(new IntType(0)));} expression expression {symTable.popScope();})
  ;
  
