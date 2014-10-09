; ModuleID = 'tamaraisthebest.c'
;target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
;target triple = "x86_64-unknown-linux-gnu"
target datalayout = "e-p:64:64:64-i1:8:8-i8:8:8-i16:16:16-i32:32:32-i64:64:64-f32:32:32-f64:64:64-v64:64:64-v128:128:128-a0:0:64-s0:64:64-f80:128:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"

%struct.vector = type { i64, i64, <32 x i32>* }
@.str = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1
@.str1 = private unnamed_addr constant [3 x i8] c"[ \00", align 1
@.str2 = private unnamed_addr constant [4 x i8] c"%d \00", align 1
@.str3 = private unnamed_addr constant [3 x i8] c"]\0A\00", align 1
declare i32 @printf(i8*, ...)

define i32 @.eqIntegers(i32 %op1, i32 %op2) {
  %1 = icmp eq i32 %op1, %op2
  %2 = zext i1 %1 to i32
  ret i32 %2
}

define i32 @.neIntegers(i32 %op1, i32 %op2) {
  %1 = icmp ne i32 %op1, %op2
  %2 = zext i1 %1 to i32
  ret i32 %2
}

define i32 @.gtIntegers(i32 %op1, i32 %op2) {
  %1 = icmp sgt i32 %op1, %op2
  %2 = zext i1 %1 to i32
  ret i32 %2
}

define i32 @.addIntegers(i32 %op1, i32 %op2) {
  %1 = add nsw i32 %op2, %op1
  ret i32 %1
}

define i32 @.subIntegers(i32 %op1, i32 %op2) {
  %1 = sub nsw i32 %op1, %op2
  ret i32 %1
}

define i32 @.multIntegers(i32 %op1, i32 %op2) {
  %1 = mul nsw i32 %op2, %op1
  ret i32 %1
}

define i32 @.divIntegers(i32 %op1, i32 %op2) {
  %1 = sdiv i32 %op1, %op2
  ret i32 %1
}

define void @.printInteger(i32 %i) {
  %1 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str, i32 0, i32 0), i32 %i)
  ret void
}

define i64 @.getIndex(i32 %x) {
  %1 = sdiv i32 %x, 32
  %2 = sext i32 %1 to i64
  ret i64 %2
}

define i64 @.getPos(i32 %x) {
  %1 = srem i32 %x, 32
  %2 = sext i32 %1 to i64
  ret i64 %2
}

define void @.initVector(%struct.vector* %x, i64 %size) {
  %1 = alloca %struct.vector*, align 8
  %2 = alloca i64, align 8
  %additional = alloca i32, align 4
  store %struct.vector* %x, %struct.vector** %1, align 8
  store i64 %size, i64* %2, align 8
  %3 = load i64* %2, align 8
  %4 = load %struct.vector** %1, align 8
  %5 = getelementptr inbounds %struct.vector* %4, i32 0, i32 0
  store i64 %3, i64* %5, align 8
  %6 = load i64* %2, align 8
  %7 = urem i64 %6, 32
  %8 = icmp ne i64 %7, 0
  %9 = select i1 %8, i32 1, i32 0
  store i32 %9, i32* %additional, align 4
  %10 = load i64* %2, align 8
  %11 = udiv i64 %10, 32
  %12 = load i32* %additional, align 4
  %13 = sext i32 %12 to i64
  %14 = add i64 %11, %13
  %15 = load %struct.vector** %1, align 8
  %16 = getelementptr inbounds %struct.vector* %15, i32 0, i32 1
  store i64 %14, i64* %16, align 8
  %17 = load %struct.vector** %1, align 8
  %18 = getelementptr inbounds %struct.vector* %17, i32 0, i32 1
  %19 = load i64* %18, align 8
  %20 = call noalias i8* @calloc(i64 %19, i64 128) nounwind
  %21 = bitcast i8* %20 to <32 x i32>*
  %22 = load %struct.vector** %1, align 8
  %23 = getelementptr inbounds %struct.vector* %22, i32 0, i32 2
  store <32 x i32>* %21, <32 x i32>** %23, align 8
  ret void
}

define void @.printVector(%struct.vector* nocapture %x) nounwind uwtable {
  %1 = alloca %struct.vector*, align 8
  %temp = alloca i32, align 4
  %i = alloca i32, align 4
  store %struct.vector* %x, %struct.vector** %1, align 8
  %2 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([3 x i8]* @.str1, i32 0, i32 0))
  store i32 0, i32* %i, align 4
  br label %3

  %4 = load i32* %i, align 4
  %5 = sext i32 %4 to i64
  %6 = load %struct.vector** %1, align 8
  %7 = getelementptr inbounds %struct.vector* %6, i32 0, i32 0
  %8 = load i64* %7, align 8
  %9 = icmp ult i64 %5, %8
  br i1 %9, label %10, label %27

  %11 = load i32* %i, align 4
  %12 = call i64 @.getIndex(i32 %11)
  %13 = load %struct.vector** %1, align 8
  %14 = getelementptr inbounds %struct.vector* %13, i32 0, i32 2
  %15 = load <32 x i32>** %14, align 8
  %16 = getelementptr inbounds <32 x i32>* %15, i64 %12
  %17 = load <32 x i32>* %16
  %18 = load i32* %i, align 4
  %19 = call i64 @.getPos(i32 %18)
  %20 = trunc i64 %19 to i32
  %21 = extractelement <32 x i32> %17, i32 %20
  store i32 %21, i32* %temp, align 4
  %22 = load i32* %temp, align 4
  %23 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str2, i32 0, i32 0), i32 %22)
  br label %24

  %25 = load i32* %i, align 4
  %26 = add nsw i32 %25, 1
  store i32 %26, i32* %i, align 4
  br label %3

  %28 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([3 x i8]* @.str3, i32 0, i32 0))
  ret void
}

define void @.range(%struct.vector* %x, i32 %start, i32 %end) nounwind uwtable {
  %1 = alloca %struct.vector*, align 8
  %2 = alloca i32, align 4
  %3 = alloca i32, align 4
  %size = alloca i64, align 8
  %i = alloca i32, align 4
  store %struct.vector* %x, %struct.vector** %1, align 8
  store i32 %start, i32* %2, align 4
  store i32 %end, i32* %3, align 4
  %4 = load i32* %3, align 4
  %5 = load i32* %2, align 4
  %6 = sub nsw i32 %4, %5
  %7 = add nsw i32 %6, 1
  %8 = sext i32 %7 to i64
  store i64 %8, i64* %size, align 8
  %9 = load %struct.vector** %1, align 8
  %10 = load i64* %size, align 8
  call void @.initVector(%struct.vector* %9, i64 %10)
  store i32 0, i32* %i, align 4
  br label %11

; label:11                                      ; preds = %32, %0
  %12 = load i32* %i, align 4
  %13 = sext i32 %12 to i64
  %14 = load %struct.vector** %1, align 8
  %15 = getelementptr inbounds %struct.vector* %14, i32 0, i32 0
  %16 = load i64* %15, align 8
  %17 = icmp ult i64 %13, %16
  br i1 %17, label %18, label %35

; label:18                                      ; preds = %11
  %19 = load i32* %2, align 4
  %20 = add nsw i32 %19, 1
  store i32 %20, i32* %2, align 4
  %21 = load i32* %i, align 4
  %22 = call i64 @.getPos(i32 %21)
  %23 = load i32* %i, align 4
  %24 = call i64 @.getIndex(i32 %23)
  %25 = load %struct.vector** %1, align 8
  %26 = getelementptr inbounds %struct.vector* %25, i32 0, i32 2
  %27 = load <32 x i32>** %26, align 8
  %28 = getelementptr inbounds <32 x i32>* %27, i64 %24
  %29 = trunc i64 %22 to i32
  %30 = load <32 x i32>* %28
  %31 = insertelement <32 x i32> %30, i32 %19, i32 %29
  store <32 x i32> %31, <32 x i32>* %28
  br label %32

; label:32                                      ; preds = %18
  %33 = load i32* %i, align 4
  %34 = add nsw i32 %33, 1
  store i32 %34, i32* %i, align 4
  br label %11

; label:35                                      ; preds = %11
  ret void
}

define void @.vectorAssign(%struct.vector* %newv, %struct.vector* %old) nounwind uwtable {
  %1 = alloca %struct.vector*, align 8
  %2 = alloca %struct.vector*, align 8
  %i = alloca i32, align 4
  store %struct.vector* %newv, %struct.vector** %1, align 8
  store %struct.vector* %old, %struct.vector** %2, align 8
  %3 = load %struct.vector** %1, align 8
  %4 = load %struct.vector** %2, align 8
  %5 = getelementptr inbounds %struct.vector* %4, i32 0, i32 0
  %6 = load i64* %5, align 8
  call void @.initVector(%struct.vector* %3, i64 %6)
  store i32 0, i32* %i, align 4
  br label %7

; label:7                                       ; preds = %37, %0
  %8 = load i32* %i, align 4
  %9 = sext i32 %8 to i64
  %10 = load %struct.vector** %2, align 8
  %11 = getelementptr inbounds %struct.vector* %10, i32 0, i32 0
  %12 = load i64* %11, align 8
  %13 = icmp ult i64 %9, %12
  br i1 %13, label %14, label %40

; label:14                                      ; preds = %7
  %15 = load i32* %i, align 4
  %16 = call i64 @.getIndex(i32 %15)
  %17 = load %struct.vector** %2, align 8
  %18 = getelementptr inbounds %struct.vector* %17, i32 0, i32 2
  %19 = load <32 x i32>** %18, align 8
  %20 = getelementptr inbounds <32 x i32>* %19, i64 %16
  %21 = load <32 x i32>* %20
  %22 = load i32* %i, align 4
  %23 = call i64 @.getPos(i32 %22)
  %24 = trunc i64 %23 to i32
  %25 = extractelement <32 x i32> %21, i32 %24
  %26 = load i32* %i, align 4
  %27 = call i64 @.getPos(i32 %26)
  %28 = load i32* %i, align 4
  %29 = call i64 @.getIndex(i32 %28)
  %30 = load %struct.vector** %1, align 8
  %31 = getelementptr inbounds %struct.vector* %30, i32 0, i32 2
  %32 = load <32 x i32>** %31, align 8
  %33 = getelementptr inbounds <32 x i32>* %32, i64 %29
  %34 = trunc i64 %27 to i32
  %35 = load <32 x i32>* %33
  %36 = insertelement <32 x i32> %35, i32 %25, i32 %34
  store <32 x i32> %36, <32 x i32>* %33
  br label %37

; label:37                                      ; preds = %14
  %38 = load i32* %i, align 4
  %39 = add nsw i32 %38, 1
  store i32 %39, i32* %i, align 4
  br label %7

; label:40                                      ; preds = %7
  ret void
}


define i64 @.getLarger(i64 %a, i64 %b) nounwind uwtable {
  %1 = alloca i64, align 8
  %2 = alloca i64, align 8
  store i64 %a, i64* %1, align 8
  store i64 %b, i64* %2, align 8
  %3 = load i64* %1, align 8
  %4 = load i64* %2, align 8
  %5 = icmp ugt i64 %3, %4
  br i1 %5, label %6, label %8

; 18:6                                       ; preds = %0
  %7 = load i64* %1, align 8
  br label %10

; 18:8                                       ; preds = %0
  %9 = load i64* %2, align 8
  br label %10

; 18:10                                      ; preds = %8, %6
  %11 = phi i64 [ %7, %6 ], [ %9, %8 ]
  ret i64 %11
}

define i64 @.getSmaller(i64 %a, i64 %b) nounwind uwtable {
  %1 = alloca i64, align 8
  %2 = alloca i64, align 8
  store i64 %a, i64* %1, align 8
  store i64 %b, i64* %2, align 8
  %3 = load i64* %1, align 8
  %4 = load i64* %2, align 8
  %5 = icmp ult i64 %3, %4
  br i1 %5, label %6, label %8

; 18:6                                       ; preds = %0
  %7 = load i64* %1, align 8
  br label %10

; 18:8                                       ; preds = %0
  %9 = load i64* %2, align 8
  br label %10

; 18:10                                      ; preds = %8, %6
  %11 = phi i64 [ %7, %6 ], [ %9, %8 ]
  ret i64 %11
}

define void @.vectorCopy(%struct.vector* %orig, %struct.vector* %new) nounwind uwtable {
  %1 = alloca %struct.vector*, align 8
  %2 = alloca %struct.vector*, align 8
  %i = alloca i32, align 4
  store %struct.vector* %orig, %struct.vector** %1, align 8
  store %struct.vector* %new, %struct.vector** %2, align 8
  store i32 0, i32* %i, align 4
  br label %3

; 18:3                                       ; preds = %33, %0
  %4 = load i32* %i, align 4
  %5 = sext i32 %4 to i64
  %6 = load %struct.vector** %1, align 8
  %7 = getelementptr inbounds %struct.vector* %6, i32 0, i32 0
  %8 = load i64* %7, align 8
  %9 = icmp ult i64 %5, %8
  br i1 %9, label %10, label %36

; 18:10                                      ; preds = %3
  %11 = load i32* %i, align 4
  %12 = call i64 @.getIndex(i32 %11)
  %13 = load %struct.vector** %1, align 8
  %14 = getelementptr inbounds %struct.vector* %13, i32 0, i32 2
  %15 = load <32 x i32>** %14, align 8
  %16 = getelementptr inbounds <32 x i32>* %15, i64 %12
  %17 = load <32 x i32>* %16
  %18 = load i32* %i, align 4
  %19 = call i64 @.getPos(i32 %18)
  %20 = trunc i64 %19 to i32
  %21 = extractelement <32 x i32> %17, i32 %20
  %22 = load i32* %i, align 4
  %23 = call i64 @.getPos(i32 %22)
  %24 = load i32* %i, align 4
  %25 = call i64 @.getIndex(i32 %24)
  %26 = load %struct.vector** %2, align 8
  %27 = getelementptr inbounds %struct.vector* %26, i32 0, i32 2
  %28 = load <32 x i32>** %27, align 8
  %29 = getelementptr inbounds <32 x i32>* %28, i64 %25
  %30 = trunc i64 %23 to i32
  %31 = load <32 x i32>* %29
  %32 = insertelement <32 x i32> %31, i32 %21, i32 %30
  store <32 x i32> %32, <32 x i32>* %29
  br label %33

; 18:33                                      ; preds = %10
  %34 = load i32* %i, align 4
  %35 = add nsw i32 %34, 1
  store i32 %35, i32* %i, align 4
  br label %3

; 18:36                                      ; preds = %3
  ret void
}

define void @.intToVectorCopy(%struct.vector* %x, i32 %value) nounwind uwtable {
  %1 = alloca %struct.vector*, align 8
  %2 = alloca i32, align 4
  %i = alloca i32, align 4
  store %struct.vector* %x, %struct.vector** %1, align 8
  store i32 %value, i32* %2, align 4
  store i32 0, i32* %i, align 4
  br label %3

; 18:3                                       ; preds = %23, %0
  %4 = load i32* %i, align 4
  %5 = sext i32 %4 to i64
  %6 = load %struct.vector** %1, align 8
  %7 = getelementptr inbounds %struct.vector* %6, i32 0, i32 0
  %8 = load i64* %7, align 8
  %9 = icmp ult i64 %5, %8
  br i1 %9, label %10, label %26

; 18:10                                      ; preds = %3
  %11 = load i32* %2, align 4
  %12 = load i32* %i, align 4
  %13 = call i64 @.getPos(i32 %12)
  %14 = load i32* %i, align 4
  %15 = call i64 @.getIndex(i32 %14)
  %16 = load %struct.vector** %1, align 8
  %17 = getelementptr inbounds %struct.vector* %16, i32 0, i32 2
  %18 = load <32 x i32>** %17, align 8
  %19 = getelementptr inbounds <32 x i32>* %18, i64 %15
  %20 = trunc i64 %13 to i32
  %21 = load <32 x i32>* %19
  %22 = insertelement <32 x i32> %21, i32 %11, i32 %20
  store <32 x i32> %22, <32 x i32>* %19
  br label %23

; 18:23                                      ; preds = %10
  %24 = load i32* %i, align 4
  %25 = add nsw i32 %24, 1
  store i32 %25, i32* %i, align 4
  br label %3

; 18:26                                      ; preds = %3
  ret void
}

define void @.vectorAdd(%struct.vector* %x, %struct.vector* %y, %struct.vector* %z) {
  %1 = alloca %struct.vector*, align 8
  %2 = alloca %struct.vector*, align 8
  %3 = alloca %struct.vector*, align 8
  %x2 = alloca %struct.vector, align 8
  %y2 = alloca %struct.vector, align 8
  %i = alloca i32, align 4
  store %struct.vector* %x, %struct.vector** %1, align 8
  store %struct.vector* %y, %struct.vector** %2, align 8
  store %struct.vector* %z, %struct.vector** %3, align 8
  %4 = load %struct.vector** %3, align 8
  %5 = load %struct.vector** %1, align 8
  %6 = getelementptr inbounds %struct.vector* %5, i32 0, i32 0
  %7 = load i64* %6, align 8
  %8 = load %struct.vector** %2, align 8
  %9 = getelementptr inbounds %struct.vector* %8, i32 0, i32 0
  %10 = load i64* %9, align 8
  %11 = call i64 @.getLarger(i64 %7, i64 %10)
  call void @.initVector(%struct.vector* %4, i64 %11)
  %12 = load %struct.vector** %3, align 8
  %13 = getelementptr inbounds %struct.vector* %12, i32 0, i32 0
  %14 = load i64* %13, align 8
  call void @.initVector(%struct.vector* %x2, i64 %14)
  %15 = load %struct.vector** %1, align 8
  call void @.vectorCopy(%struct.vector* %15, %struct.vector* %x2)
  %16 = load %struct.vector** %3, align 8
  %17 = getelementptr inbounds %struct.vector* %16, i32 0, i32 0
  %18 = load i64* %17, align 8
  call void @.initVector(%struct.vector* %y2, i64 %18)
  %19 = load %struct.vector** %2, align 8
  call void @.vectorCopy(%struct.vector* %19, %struct.vector* %y2)
  store i32 0, i32* %i, align 4
  br label %20

; 18:20                                      ; preds = %46, %0
  %21 = load i32* %i, align 4
  %22 = sext i32 %21 to i64
  %23 = getelementptr inbounds %struct.vector* %x2, i32 0, i32 0
  %24 = load i64* %23, align 8
  %25 = icmp ult i64 %22, %24
  br i1 %25, label %26, label %49

; 18:26                                      ; preds = %20
  %27 = load i32* %i, align 4
  %28 = call i64 @.getIndex(i32 %27)
  %29 = getelementptr inbounds %struct.vector* %x2, i32 0, i32 2
  %30 = load <32 x i32>** %29, align 8
  %31 = getelementptr inbounds <32 x i32>* %30, i64 %28
  %32 = load <32 x i32>* %31
  %33 = load i32* %i, align 4
  %34 = call i64 @.getIndex(i32 %33)
  %35 = getelementptr inbounds %struct.vector* %y2, i32 0, i32 2
  %36 = load <32 x i32>** %35, align 8
  %37 = getelementptr inbounds <32 x i32>* %36, i64 %34
  %38 = load <32 x i32>* %37
  %39 = add <32 x i32> %32, %38
  %40 = load i32* %i, align 4
  %41 = call i64 @.getIndex(i32 %40)
  %42 = load %struct.vector** %3, align 8
  %43 = getelementptr inbounds %struct.vector* %42, i32 0, i32 2
  %44 = load <32 x i32>** %43, align 8
  %45 = getelementptr inbounds <32 x i32>* %44, i64 %41
  store <32 x i32> %39, <32 x i32>* %45
  br label %46

; 18:46                                      ; preds = %26
  %47 = load i32* %i, align 4
  %48 = add nsw i32 %47, 1
  store i32 %48, i32* %i, align 4
  br label %20

; 18:49                                      ; preds = %20
  ret void
}

declare noalias i8* @calloc(i64, i64) nounwind

define i32 @main() nounwind uwtable {
%1 = alloca i32, align 4
  %x = alloca %struct.vector, align 8
  %y = alloca %struct.vector, align 8

store i32 0, i32* %1
  ; store constant and load it into counter
  %2 = alloca i32, align 4
  store i32 1, i32* %2, align 4
  %3 = load i32* %2, align 4
  ; store constant and load it into counter
  %4 = alloca i32, align 4
  store i32 50, i32* %4, align 4
  %5 = load i32* %4, align 4
  %_5 = alloca %struct.vector, align 8
  call void @.range(%struct.vector* %_5, i32 %3, i32 %5)
  call void @.vectorAssign(%struct.vector* %x, %struct.vector* %_5)
  ; load vector
   %_6 = alloca %struct.vector, align 8
   call void @.vectorAssign(%struct.vector* %_6, %struct.vector* %x)
  ; nop
  %6 = add i1 0, 0
  call void @.vectorAssign(%struct.vector* %y, %struct.vector* %_6)

  ; store constant and load it into counter
  %7 = alloca i32, align 4
  store i32 1, i32* %7, align 4
  %8 = load i32* %7, align 4
  ; store constant and load it into counter
  %9 = alloca i32, align 4
  store i32 20000, i32* %9, align 4
  %10 = load i32* %9, align 4
  %_10 = alloca %struct.vector, align 8
  call void @.range(%struct.vector* %_10, i32 %8, i32 %10)
  call void @.vectorAssign(%struct.vector* %y, %struct.vector* %_10)
  ; load vector
   %_11 = alloca %struct.vector, align 8
   call void @.vectorAssign(%struct.vector* %_11, %struct.vector* %x)
  ; nop
  %11 = add i1 0, 0
  ; print
  call void @.printVector(%struct.vector* %_11)
  ; load vector
   %_12 = alloca %struct.vector, align 8
   call void @.vectorAssign(%struct.vector* %_12, %struct.vector* %y)
  ; nop
  %12 = add i1 0, 0
  ; print
  call void @.printVector(%struct.vector* %_12)
  ; store constant and load it into counter
  %13 = alloca i32, align 4
  store i32 1, i32* %13, align 4
  %14 = load i32* %13, align 4
  ; store constant and load it into counter
  %15 = alloca i32, align 4
  store i32 2, i32* %15, align 4
  %16 = load i32* %15, align 4
  ; addition !
  %17 = call i32 @.addIntegers(i32 %14, i32 %16)

  ; print
  tail call void @.printInteger(i32 %17)

	%18 = load i32* %1
    ret i32 %18
}

declare void @llvm.memcpy.p0i8.p0i8.i64(i8* nocapture, i8* nocapture, i64, i32, i1) nounwind
