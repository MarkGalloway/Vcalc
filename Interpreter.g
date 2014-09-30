tree grammar Interpreter;

options {
  language = Java;
  tokenVocab = Vcalc;
  ASTLabelType = CommonTree;
}

@header {
  import node.vcalc.*;
  import scope.vcalc.*;
}

@members {
  Scope global;
  
  public Interpreter(CommonTreeNodeStream nodestream){
    super(nodestream);
    global = new Scope();
  }
}

program returns [VcalcNode node] 
@init { BlockNode blockNode = new BlockNode(); }
@after{ node = blockNode; }
  : ^(PROGRAM (declaration {blockNode.addStatement($declaration.node);})* 
              (statement {blockNode.addStatement($statement.node);})*)
  ;

declaration returns [VcalcNode node]
  : ^(VAR type ID expression) {$node = new AssignmentNode($ID.text, $expression.node, global);}
  ;

type
  : 'int' 
  ;
 
statement returns [VcalcNode node]
  : assignment {$node = $assignment.node;}
  | ifStat {$node = $ifStat.node;}
  | loop {$node = $loop.node;}
  | print {$node = $print.node;}
  ;

assignment returns [VcalcNode node]
  : ^('=' ID expression) {$node = new AssignmentNode($ID.text, $expression.node, global);}
  ;

ifStat returns [VcalcNode node]
  : ^(IF expression block) {$node = new IfNode($expression.node, $block.node);}
  ;
  
loop returns [VcalcNode node]
  : ^(LOOP expression block) {$node = new LoopNode($expression.node, $block.node);}
  ;

print returns [VcalcNode node]
  : ^(PRINT expression) {$node = new PrintNode($expression.node);}
  ;

block returns [VcalcNode node]
@init { BlockNode blockNode = new BlockNode(); }
@after{ node = blockNode; }
  : ^(SLIST (statement {blockNode.addStatement($statement.node);})*)
  ;

expression returns [VcalcNode node]
  : ^('==' op1=expression op2=expression) { $node = new EQNode($op1.node, $op2.node); }
  | ^('!=' op1=expression op2=expression) { $node = new NENode($op1.node, $op2.node); }
  | ^('<' op1=expression op2=expression)  { $node = new LTNode($op1.node, $op2.node); }
  | ^('>' op1=expression op2=expression)  { $node = new GTNode($op1.node, $op2.node); }
  | ^('+' op1=expression op2=expression)  { $node = new AddNode($op1.node, $op2.node); }
  | ^('-' op1=expression op2=expression)  { $node = new SubNode($op1.node, $op2.node); }
  | ^('*' op1=expression op2=expression)  { $node = new MultNode($op1.node, $op2.node); }
  | ^('/' op1=expression op2=expression)  { $node = new DivNode($op1.node, $op2.node); }
  | ID {$node = new VarNode($ID.text, global);}
  | INTEGER {$node = new IntNode(Integer.parseInt($INTEGER.text));}
  ;