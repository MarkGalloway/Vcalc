Vcalc
=====

Main:
X Update Parser to Vcalc
  - Revisit operator precedence and such with some robust test cases
X Add Symbol Table to interpreter
-- Add symbol table to defined.g and uncomment it
X Update binary ops to handle new type system.
- Vectors
  X Implement FilterNode
    -- test
  X Implement GeneratorNode
    -- test
  - Vector binary operations (arithmetic, comparison ...)
  - Vector promotion for binary ops
  - Test IndexNode, fix if needed


- Ensure all constrains are being met by Interpreter, add walkers if needed
  - Ensure all exceptions are thrown and are legibile
- LLVM
  - Create walkers if needed to preprocess constrains
  - Modify Templater.g to handle new parser
  - Create 







Nice to haves:
- Create a proper void return value for VcalcValue 
- More tests. Edge cases. Constraints. etc