; ModuleID = 'tamaraisthebest.c'
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-unknown-linux-gnu"
; target datalayout = "e-p:64:64:64-i1:8:8-i8:8:8-i16:16:16-i32:32:32-i64:64:64-f32:32:32-f64:64:64-v64:64:64-v128:128:128-a0:0:64-s0:64:64-f80:128:128-n8:16:32:64-S128"
; target triple = "x86_64-unknown-linux-gnu"

%struct.vector = type { i64, i64, <32 x i32>* }
@.str = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1
@.str1 = private unnamed_addr constant [3 x i8] c"[ \00", align 1
@.str2 = private unnamed_addr constant [4 x i8] c"%d \00", align 1
@.str3 = private unnamed_addr constant [3 x i8] c"]\0A\00", align 1
declare i32 @printf(i8*, ...)

; Function Attrs: nounwind ssp uwtable
define i32 @.eqIntegers(i32 %op1, i32 %op2) {
  %1 = icmp eq i32 %op1, %op2
  %2 = zext i1 %1 to i32
  ret i32 %2
}

; Function Attrs: nounwind ssp uwtable
define i32 @.neIntegers(i32 %op1, i32 %op2) {
  %1 = icmp ne i32 %op1, %op2
  %2 = zext i1 %1 to i32
  ret i32 %2
}

; Function Attrs: nounwind ssp uwtable
define i32 @.gtIntegers(i32 %op1, i32 %op2) {
  %1 = icmp sgt i32 %op1, %op2
  %2 = zext i1 %1 to i32
  ret i32 %2
}

; Function Attrs: nounwind readnone ssp uwtable
define i32 @.addIntegers(i32 %op1, i32 %op2) {
  %1 = add nsw i32 %op2, %op1
  ret i32 %1
}

; Function Attrs: nounwind ssp uwtable
define i32 @.subIntegers(i32 %op1, i32 %op2) {
  %1 = sub nsw i32 %op1, %op2
  ret i32 %1
}

; Function Attrs: nounwind ssp uwtable
define i32 @.multIntegers(i32 %op1, i32 %op2) {
  %1 = mul nsw i32 %op2, %op1
  ret i32 %1
}

; Function Attrs: nounwind ssp uwtable
define i32 @.divIntegers(i32 %op1, i32 %op2) {
  %1 = sdiv i32 %op1, %op2
  ret i32 %1
}

; Function Attrs: nounwind ssp uwtable
define void @.printInteger(i32 %i) {
  %1 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str, i32 0, i32 0), i32 %i)
  ret void
}

define i64 @getIndex(i32 %x) nounwind uwtable readnone {
  %1 = sdiv i32 %x, 32
  %2 = sext i32 %1 to i64
  ret i64 %2
}

define i64 @getPos(i32 %x) nounwind uwtable readnone {
  %1 = srem i32 %x, 32
  %2 = sext i32 %1 to i64
  ret i64 %2
}

define void @.initVector(%struct.vector* %x, i64 %size) nounwind uwtable {
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
  %12 = call i64 @getIndex(i32 %11)
  %13 = load %struct.vector** %1, align 8
  %14 = getelementptr inbounds %struct.vector* %13, i32 0, i32 2
  %15 = load <32 x i32>** %14, align 8
  %16 = getelementptr inbounds <32 x i32>* %15, i64 %12
  %17 = load <32 x i32>* %16
  %18 = load i32* %i, align 4
  %19 = call i64 @getPos(i32 %18)
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
  %22 = call i64 @getPos(i32 %21)
  %23 = load i32* %i, align 4
  %24 = call i64 @getIndex(i32 %23)
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
  %16 = call i64 @getIndex(i32 %15)
  %17 = load %struct.vector** %2, align 8
  %18 = getelementptr inbounds %struct.vector* %17, i32 0, i32 2
  %19 = load <32 x i32>** %18, align 8
  %20 = getelementptr inbounds <32 x i32>* %19, i64 %16
  %21 = load <32 x i32>* %20
  %22 = load i32* %i, align 4
  %23 = call i64 @getPos(i32 %22)
  %24 = trunc i64 %23 to i32
  %25 = extractelement <32 x i32> %21, i32 %24
  %26 = load i32* %i, align 4
  %27 = call i64 @getPos(i32 %26)
  %28 = load i32* %i, align 4
  %29 = call i64 @getIndex(i32 %28)
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



declare noalias i8* @calloc(i64, i64) nounwind

define i32 @main() nounwind uwtable {
%1 = alloca i32, align 4
store i32 0, i32* %1
  ; store constant and load it into counter
  %2 = alloca i32, align 4
  store i32 2, i32* %2, align 4
  %3 = load i32* %2, align 4
  ; store constant and load it into counter
  %4 = alloca i32, align 4
  store i32 5, i32* %4, align 4
  %5 = load i32* %4, align 4
  %_5 = alloca %struct.vector, align 8
  call void @.range(%struct.vector* %_5, i32 %3, i32 %5)
  ; print
  call void @.printVector(%struct.vector* %_5)
  ; store constant and load it into counter
  %6 = alloca i32, align 4
  store i32 2, i32* %6, align 4
  %7 = load i32* %6, align 4
  ; store constant and load it into counter
  %8 = alloca i32, align 4
  store i32 65, i32* %8, align 4
  %9 = load i32* %8, align 4
  %_9 = alloca %struct.vector, align 8
  call void @.range(%struct.vector* %_9, i32 %7, i32 %9)
  ; print
  call void @.printVector(%struct.vector* %_9)

	%10 = load i32* %1
    ret i32 %10
}

declare void @llvm.memcpy.p0i8.p0i8.i64(i8* nocapture, i8* nocapture, i64, i32, i1) nounwind
