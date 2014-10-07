Vcalc  
=====  

Main:  
X Update Parser to Vcalc  
  X Revisit operator precedence and such with some robust test cases  
X Add Symbol Table to interpreter  
X Add symbol table to defined.g and uncomment it  
X Update binary ops to handle new type system.  
X Vectors  
  X Implement FilterNode  
    X test  
  X Implement GeneratorNode  
    X test  
  X Vector binary operations (arithmetic, comparison ...)  
  X Vector promotion for binary ops  
  X Test IndexNode, fix if needed  



X Ensure all constrains are being met by Interpreter, add walkers if needed  
  X Ensure all exceptions are thrown and are legibile  


- LLVM  
  - your compiler is required to use LLVM vector types and to produce vector operations whenever possible.
    - because Using the LLVM vector types to represent vectors enables LLVM to parallelize many of the vector operations. Such code is much faster than the code generated for an integer array, which would have to iterate through every element of a vector sequentially to perform any operations on vectors.
  - How to do both integer and vector binary arithmetic? 
    - Can we have function overloading?
    - We also need to implement vector padding for different size vectors
      - To handle variable-length vectors you should use an array of LLVM vectors of a fixed size to store all vectors. eg/ {i32, i32, <32 x i32>*}
        - Need to 0 or 1 the unused portion (see spec, 2.2)
    - LLVM has type checking so can we type check things to see where they need to go?
    - These binary operations must be implemented using the vector operations in the LLVM IR. The comparison instructions in the LLVM IR will return vectors of i1 types, but these can be zero extended to integer types again using zext.
  - vector reassignment: More memory will most likely need to be reallocated to hold the different size.
  - Generators:
    - The compiler should transform all generator expressions into a sequence of vector operations in the LLVM IR on the 32 element vector segments, except for the indexing expressions and filter expressions that occur inside of the generator. eg/ [i in 1..10 | i * i] is equivalent to performing the following element-wise vector multiplication: (1..10) * (1..10). Performing this vector operation does not require looping over vector elements individually. This operation can be performed with a single vector multiplication per vector segment.
  - Filters:
    - Filters can not be implemented using the LLVM vector instructions, and must be done by looping sequentially through the elements of the domain vector.
  X Indexing:
    X You do not have to implement vector indexing using LLVM vector instructions.
  - Printing:
    - need to implement printing of vectors. Maybe just get each element and print it, because I don't think printf can handle a vector.











Nice to haves:
- Create a proper void return value for VcalcValue   
- More tests. Edge cases. Constraints. etc  
X System Exits on Parser Error and does not attempt to build AST  
