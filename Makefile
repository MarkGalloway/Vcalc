clang = clang
make:

	$(clang) -O0 -o Tests/llvm/printO0.ll -S Tests/c/print.c -emit-llvm
	$(clang) -O1 -o Tests/llvm/printO1.ll -S Tests/c/print.c -emit-llvm
	$(clang) -O2 -o Tests/llvm/printO2.ll -S Tests/c/print.c -emit-llvm
	$(clang) -O3 -o Tests/llvm/printO3.ll -S Tests/c/print.c -emit-llvm
