grammar Vcalc;

options {
  language = Java;
  output = AST;
  ASTLabelType = CommonTree;
}

tokens {
  PROGRAM;
  VAR;
  IF;
  LOOP;
  PRINT;
  SLIST;
  FI;
}
program 
  : declaration* statement* -> ^(PROGRAM declaration* statement*)
  ;

declaration
  : type ID '=' expression ';' -> ^(VAR type ID expression)
  ;
  
type
  : 'int' 
  ; 
   
statement
  : assignment
  | ifStat
  | loop
  | print
  ;

assignment
  : ID '=' expression ';' -> ^('=' ID expression)
  ;

ifStat
  : 'if' '(' expression ')' block 'fi' ';' -> ^(IF expression block)
  ;
  
loop
  : 'loop' '(' expression ')' block 'pool' ';' -> ^(LOOP expression block)
  ;
  
block
  : statement* -> ^(SLIST statement*)
  ;

print
  : 'print' '(' expression ')' ';' -> ^(PRINT expression)
  ;

expression
  : comparison ((('=='^ | '!='^) comparison))*
  ;

comparison
 : add ((('<'^ | '>'^) add))* 
 ;

add
  : mult ((('+'^ | '-'^) mult))*
  ;

mult
  : atom ((('*'^ | '/'^) atom))*
  ;

atom
  : ID
  | '(' expression ')' -> expression
  | INTEGER
  ;

fragment DIGIT : '0'..'9';
fragment LETTER : 'a'..'z' |'A'..'Z';
WS : (' ' | '\t' | '\f')+ {$channel=HIDDEN;};
NL : ('\r' '\n' | '\r' | '\n' | EOF) {$channel=HIDDEN;};
ID : LETTER ((LETTER | DIGIT))*;
INTEGER : DIGIT+;

