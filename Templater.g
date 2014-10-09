tree grammar Templater;

options {
  language = Java;
  output = template;
  tokenVocab = Vcalc;
  ASTLabelType = VcalcAST;
}

@header {
  import symbol2.vcalc.*;
  import ast.vcalc.*;
}

@members {
  int counter = 1;
  int veccounter = 0;
  ArrayList<String> intVars = new ArrayList<String>();
  ArrayList<String> vecVars = new ArrayList<String>();
  
  SymbolTable symTable;
	public Templater(TreeNodeStream input, SymbolTable symTable) {
	    this(input);
	    this.symTable = symTable;
	}
}

program
  : ^(PROGRAM i+=declaration* s+=statement*)      -> mainSchema(intVars={intVars}, vecVars={vecVars}, decls = {$i}, stats = {$s}, label={++counter})
  ;

declaration
  : ^(VAR Int    ID e=expression) {intVars.add($ID.text);} -> intAssign(var = {$ID.text}, expr = {$e.st}, label = {$e.label})
  | ^(VAR Vector ID e=expression) {vecVars.add($ID.text);}               -> vecAssign(var = {$ID.text}, expr = {$e.st}, veclabel = {$e.label})
  ;
 //
statement
  : ^(EQ='=' ID e=expression)
    -> {$EQ.evalType == SymbolTable._vector}?  vecAssign(var = {$ID.text}, expr = {$e.st}, veclabel = {$e.label})
    -> intAssign(var = {$ID.text}, expr = {$e.st}, label = {$e.label})
    
  | ^(IF e=expression {counter+=2;} ^(SLIST s+=statement*) {++counter;})   
    -> ifStat(expr = {$e.st}, stats = {$s}, exprlabel={$e.label}, condition={$e.label+1}, ifbody={$e.label+2}, ifend={counter} )
    
  | ^(LOOP  {int looplabel = ++counter;} e=expression {counter+=2;} ^(SLIST s+=statement*) {++counter;})  
      -> loopStat(expr = {$e.st}, stats = {$s}, looplabel={looplabel}, exprlabel={$e.label}, condition={$e.label+1}, loopbody={$e.label+2}, loopend={counter} )
  
  | ^(PRINT e=expression)
     -> {$PRINT.evalType == SymbolTable._vector}? printVector(expr = {$e.st}, counter={e.label})        
     -> printInteger(expr = {$e.st}, counter={e.label})    
  ;

// expressions should return the instruction index their result is loaded
expression returns [int label]

 // equality binary ops
  : ^('=='  op1=expression op2=expression) {$label = ++counter;}
        -> eqIntegers(lhs = {$op1.st}, rhs = {$op2.st}, lhsLabel={$op1.label}, rhsLabel={$op2.label}, counter={$label})
  | ^('!='  op1=expression op2=expression) {$label = ++counter;}
        -> neIntegers(lhs = {$op1.st}, rhs = {$op2.st}, lhsLabel={$op1.label}, rhsLabel={$op2.label}, counter={$label})
  | ^('<'   op1=expression op2=expression) {$label = ++counter;}
        -> ltIntegers(lhs = {$op1.st}, rhs = {$op2.st}, lhsLabel={$op1.label}, rhsLabel={$op2.label}, counter={$label})
  
  | ^('>'   op1=expression op2=expression) {$label = ++counter;}
        -> gtIntegers(lhs = {$op1.st}, rhs = {$op2.st}, lhsLabel={$op1.label}, rhsLabel={$op2.label}, counter={$label})

  // binary ops
  | ^('+'   op1=expression op2=expression) {$label = ++counter;}
        -> addIntegers(lhs = {$op1.st}, rhs = {$op2.st}, lhsLabel={$op1.label}, rhsLabel={$op2.label}, counter={$label})      
  | ^('-'   op1=expression op2=expression) {$label = ++counter;}
        -> subIntegers(lhs = {$op1.st}, rhs = {$op2.st}, lhsLabel={$op1.label}, rhsLabel={$op2.label} ,counter={$label}) 
  | ^('*'   op1=expression op2=expression) {$label = ++counter;}
        -> multIntegers(lhs = {$op1.st}, rhs = {$op2.st}, lhsLabel={$op1.label}, rhsLabel={$op2.label} ,counter={$label}) 
  | ^('/'   op1=expression op2=expression) {$label = ++counter;}      
        -> divIntegers(lhs = {$op1.st}, rhs = {$op2.st}, lhsLabel={$op1.label}, rhsLabel={$op2.label} ,counter={$label}) 
        
  | ^('..'  op1=expression op2=expression) {$label = counter;}      
    -> range(lhs = {$op1.st}, rhs = {$op2.st}, lhsLabel={$op1.label}, rhsLabel={$op2.label} ,veclabel={$label}) // to do
  //  | ^(INDEX op1=expression op+=expression)      -> loadIndex(vector = {$op1.text}, index = {$op2.text}) //to do
  
  | ID {$label = ++counter;}                             
    -> {$ID.evalType == SymbolTable._vector}? loadVector(var = {$ID.text}, counter={$label})
    -> loadVariable(var = {$ID.text}, counter={$label}) 
  |
   INTEGER {counter+=2; $label = counter;}         -> loadConstant(value = {$INTEGER.text}, storecounter={$label-1}, loadcounter={$label})
  
//  | ^(GENERATOR ID op1+=expression op2+=expression) {$label = counter;}
 //     -> generator(var = {$ID.text}, lhs = {$op1}, rhs = {$op2}) // to do 
//  | ^(FILTER ID op1+=expression op2+=expression)    -> filter(var = {$ID.text}, lhs = {$op1}, rhs = {$op2}) // to do
  ;
