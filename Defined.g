tree grammar Defined;

options {
  language = Java;
  tokenVocab = Vcalc;
  ASTLabelType = CommonTree;
}


@header {
  import java.util.ArrayList;
}

@members {
  ArrayList<String> variables = new ArrayList<String>();
}

program 
  : ^(PROGRAM declaration* statement*)
  ;

declaration
  : ^(VAR type ID expression)
    { if(variables.contains($ID.text)) { throw new RuntimeException("Variable " + $ID.text + " cannot be declared twice!");}
      variables.add($ID.text); 
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
  : ^('==' expression expression)
  | ^('!=' expression expression)
  | ^('<' expression expression)
  | ^('>' expression expression)
  | ^('+' expression expression)
  | ^('-' expression expression)
  | ^('*' expression expression)
  | ^('/' expression expression)
  | ID { if(!variables.contains($ID.text)) {throw new RuntimeException("Use of undeclared variable " + $ID.text);}}
  | INTEGER
  ;
