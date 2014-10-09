#include <stdio.h>

// int addFunc(int op1, int op2) {
//   return op1 + op2;
// }

// int subFunc(int op1, int op2) {
//   return op1 - op2;
// }

// int multFunc(int op1, int op2) {
//   return op1 * op2;
// }

// int divFunc(int op1, int op2) {
//   return op1 / op2;
// }

typedef int v4si __attribute__ ((vector_size (16)));

struct vector{
    int size;
    int segments;
    v4si vector;
};


void printInteger(int i) {
  printf("%d\n", i);
}

// int i = 5;
// int j = 2;

int main() {
  //int result = addFunc(i,j);
  //printInteger(result);
}

