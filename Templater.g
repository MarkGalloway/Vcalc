tree grammar Templater;

options {
  language = Java;
  output = template;
  tokenVocab = Vcalc;
  ASTLabelType = CommonTree;
}

@members {
  int counter = 0;
}

program 
  : ^(PROGRAM i+=declaration* s+=statement*)      -> mainSchema(decls = {$i}, stats = {$s})
  ;

declaration
  : ^(VAR Int    ID e+=expression)                -> assignment(var = {$ID.text}, expr = {$e})
  | ^(VAR Vector ID e+=expression)                -> vectorDeclaration(var = {$ID.text}, expr = {$e})
  ;
 
statement
  : ^('=' ID e+=expression)                       -> assignment(var = {$ID.text}, expr = {$e})
  | ^(IF e+=expression ^(SLIST s+=statement*))    -> ifStat(expr = {$e}, stats = {$s})
  | ^(LOOP e+=expression ^(SLIST s+=statement*))  -> loopStat(expr = {$e}, stats = {$s})
  | ^(PRINT e+=expression)                        -> print(expr = {$e}, oldcounter={counter}, counter={++counter})
  ;

expression
  : ^(INDEX op1+=expression op2+=expression)      -> loadIndex(vector = {$op1}, index = {$op2})
  | ^('=='  op1+=expression op2+=expression)      -> eqComparison(lhs = {$op1}, rhs = {$op2})
  | ^('!='  op1+=expression op2+=expression)      -> neComparison(lhs = {$op1}, rhs = {$op2})
  | ^('<'   op1+=expression op2+=expression)      -> ltComparison(lhs = {$op1}, rhs = {$op2})
  | ^('>'   op1+=expression op2+=expression)      -> gtComparison(lhs = {$op1}, rhs = {$op2})
  | ^('+'   op1+=expression op2+=expression)      -> add(lhs = {$op1}, rhs = {$op2}, lhsLabel={counter-1}, rhsLabel={counter} ,counter={++counter})
  | ^('-'   op1+=expression op2+=expression)      -> sub(lhs = {$op1}, rhs = {$op2}, lhsLabel={counter-1}, rhsLabel={counter} ,counter={++counter})
  | ^('*'   op1+=expression op2+=expression)      -> mult(lhs = {$op1}, rhs = {$op2}, lhsLabel={counter-1}, rhsLabel={counter} ,counter={++counter})
  | ^('/'   op1+=expression op2+=expression)      -> div(lhs = {$op1}, rhs = {$op2}, lhsLabel={counter-1}, rhsLabel={counter} ,counter={++counter})
  | ^('..'  op1+=expression op2+=expression)      -> range(lhs = {$op1}, rhs = {$op2}, lhsLabel={counter-1}, rhsLabel={counter} ,counter={++counter})
  | ID                                            -> loadVariable(var = {$ID.text}, counter={++counter})
  | INTEGER                                       -> loadConstant(value = {$INTEGER.text}, counter={++counter})
  | ^(GENERATOR ID op1+=expression op2+=expression) -> generator(var = {$ID.text}, lhs = {$op1}, rhs = {$op2})
  | ^(FILTER ID op1+=expression op2+=expression)    -> filter(var = {$ID.text}, lhs = {$op1}, rhs = {$op2})
  ;
