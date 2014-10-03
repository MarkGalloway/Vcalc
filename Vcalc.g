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
  : ID '=' expression ';' -> ^('=' ID expression)
  ;

ifStat
  : If '(' expression ')' block Fi ';' -> ^(IF expression block)
  ;
  
loop
  : Loop '(' expression ')' block Pool ';' -> ^(LOOP expression block)
  ;
  
block
  : statement* -> ^(SLIST statement*)
  ;

print
  : Print '(' expression ')' ';' -> ^(PRINT expression)
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
  : range ((('*'^ | '/'^) range))*
  ;
  
range
  : atom ('..'^ atom)?
  ;

atom
  : ID
  | '(' expression ')' -> expression
  | INTEGER
  ;

//LEXER RULES

// Reserved Words
If : 'if';
Fi : 'fi';
Filter : 'filter';
In : 'in';
Int : 'int';
Loop : 'loop';
Pool : 'pool';
Print : 'print';
Vector : 'vector';


ID : LETTER ((LETTER | DIGIT))*;
INTEGER : DIGIT+;

WS : (' ' | '\t' | '\f')+ {$channel=HIDDEN;};
NL : ('\r' '\n' | '\r' | '\n' | EOF) {$channel=HIDDEN;};

fragment DIGIT : '0'..'9';
fragment LETTER : 'a'..'z' |'A'..'Z';
