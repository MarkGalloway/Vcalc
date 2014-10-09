; ModuleID = 'tamarahasmoldypants.c'
target datalayout = "e-p:64:64:64-i1:8:8-i8:8:8-i16:16:16-i32:32:32-i64:64:64-f32:32:32-f64:64:64-v64:64:64-v128:128:128-a0:0:64-s0:64:64-f80:128:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

@.str = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1

; Function Attrs: nounwind ssp uwtable
define i32 @.addIntegers(i32 %op1, i32 %op2) {
  %1 = add nsw i32 %op2, %op1
  ret i32 %1
}



; Function Attrs: nounwind ssp uwtable
define void @printInteger(i32 %i) {
  %1 = tail call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str, i64 0, i64 0), i32 %i) #3
  ret void
}

declare i32 @printf(i8*, ...)

define i32 @main() nounwind uwtable {
  
    ;load vars for addition

    %1 = alloca i32, align 4
    store i32 5, i32 %1, align 4


    %2 = alloca i32, align 4
    store i32 5, i32 %2, align 4

    %3 = call i32 @.addIntegers(i32 %1, i32 %2)

    %4 = load i32* %3, align 4
    tail call void @printInteger(i32 %4)
    

    ret i32 0
}

