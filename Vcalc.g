grammar Vcalc;

options {
  language = Java;
  output = AST;
  ASTLabelType = VcalcAST;
}

tokens {
  PROGRAM;
  VAR;
  IF;
  LOOP;
  PRINT;
  SLIST;
  GENERATOR;
  FILTER;
  INDEX;
}

@header {
  import java.util.Arrays;
  import ast.vcalc.*;
}

@members {

public void displayRecognitionError(String[] tokenNames,RecognitionException e) {
        String hdr = getErrorHeader(e);
        String msg = getErrorMessage(e, tokenNames);
        String[] msglist = msg.split(" ");
        
        if (msg.equals("missing EOF at 'int'") || msg.equals("missing EOF at 'vector'")) {
          System.err.println(hdr + " - declaration of variables must be at the top of the input file");
          return;
        }
        
        if (msglist[0].equals("missing") && msglist[1].equals("';'")) {
           System.err.println(hdr + " - expected semicolon before " + msglist[3]);
           return;
        }
        
        System.err.println(hdr + " - " + msg);
    }

}




program 
  : declaration* statement* EOF -> ^(PROGRAM declaration* statement*)
  ;

declaration
  : type ID '=' expression ';'
    -> ^(VAR type ID expression)
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

//Expressions

expression
  :  equality (index^ equality ']'! )?  //Add plus if multiple indexes allowed... -> ^([ $vecExpr $ind)
  ;

index
  : '[' -> INDEX
  ;

equality
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
  | filter
  | generator
  | INTEGER
  ;
  
generator: 
  | '[' ID In  vecExpr=expression '|' intExpr=expression ']' -> ^(GENERATOR ID $vecExpr $intExpr)
  ; 
  //TODO: expression 1 is anything that returns a vector.... type check at runtime, I guess
  //TODO: expression 2 is int valued.... type check at runtime
  
filter
  : Filter '(' ID In vecExpr=expression '|' predicate=expression ')' -> ^(FILTER ID $vecExpr $predicate)
  ;
  //TODO: expression 1 is anything that returns a vector.. runtime check
  //TODO: expression 2 is anything that returns a predicate.. runtime check

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
