tree grammar Templater;

options {
  language = Java;
  output = template;
  tokenVocab = Vcalc;
  ASTLabelType = CommonTree;
}

program 
  : ^(PROGRAM declaration* statement*)
  ;

declaration
  : ^(VAR type ID expression)
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
  | ID 
  | INTEGER
  | ^(GENERATOR ID)
  | ^(FILTER ID)
  ;
