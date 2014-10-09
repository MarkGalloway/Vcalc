; ModuleID = 'tamaraisthebest.c'
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

; Addition between two vectors
define <32 x i32> @.addVectors(<32 x i32> %A, <32 x i32> %B) nounwind uwtable {
  %1 = add <32 x i32> %A, %B
  ret <32 x i32> %1
}

; subtraction between two vectors
define <32 x i32> @.subVectors(<32 x i32> %A, <32 x i32> %B) nounwind uwtable {
  %1 = sub <32 x i32> %A, %B
  ret <32 x i32> %1
}

; mult between two vectors
define <32 x i32> @.multVectors(<32 x i32> %A, <32 x i32> %B) nounwind uwtable {
  %1 = mul <32 x i32> %A, %B
  ret <32 x i32> %1
}

; div between two vectors
define <32 x i32> @.divVectors(<32 x i32> %A, <32 x i32> %B) nounwind uwtable {
  %1 = sdiv <32 x i32> %A, %B
  ret <32 x i32> %1
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
  %additional = alloca i32, align 4
  %i = alloca i32, align 4
  store %struct.vector* %x, %struct.vector** %1, align 8
  store i32 %start, i32* %2, align 4
  store i32 %end, i32* %3, align 4
  %4 = load i32* %3, align 4
  %5 = load i32* %2, align 4
  %6 = sub nsw i32 %4, %5
  %7 = add nsw i32 %6, 1
  %8 = sext i32 %7 to i64
  %9 = load %struct.vector** %1, align 8
  %10 = getelementptr inbounds %struct.vector* %9, i32 0, i32 0
  store i64 %8, i64* %10, align 8
  %11 = load %struct.vector** %1, align 8
  %12 = getelementptr inbounds %struct.vector* %11, i32 0, i32 0
  %13 = load i64* %12, align 8
  %14 = urem i64 %13, 32
  %15 = icmp ne i64 %14, 0
  %16 = select i1 %15, i32 1, i32 0
  store i32 %16, i32* %additional, align 4
  %17 = load %struct.vector** %1, align 8
  %18 = getelementptr inbounds %struct.vector* %17, i32 0, i32 0
  %19 = load i64* %18, align 8
  %20 = udiv i64 %19, 32
  %21 = load i32* %additional, align 4
  %22 = sext i32 %21 to i64
  %23 = add i64 %20, %22
  %24 = load %struct.vector** %1, align 8
  %25 = getelementptr inbounds %struct.vector* %24, i32 0, i32 1
  store i64 %23, i64* %25, align 8
  %26 = load %struct.vector** %1, align 8
  %27 = getelementptr inbounds %struct.vector* %26, i32 0, i32 1
  %28 = load i64* %27, align 8
  %29 = call noalias i8* @calloc(i64 %28, i64 128) nounwind
  %30 = bitcast i8* %29 to <32 x i32>*
  %31 = load %struct.vector** %1, align 8
  %32 = getelementptr inbounds %struct.vector* %31, i32 0, i32 2
  store <32 x i32>* %30, <32 x i32>** %32, align 8
  store i32 0, i32* %i, align 4
  br label %33

; label:33                                      ; preds = %54, %0
  %34 = load i32* %i, align 4
  %35 = sext i32 %34 to i64
  %36 = load %struct.vector** %1, align 8
  %37 = getelementptr inbounds %struct.vector* %36, i32 0, i32 0
  %38 = load i64* %37, align 8
  %39 = icmp ult i64 %35, %38
  br i1 %39, label %40, label %57

; label:40                                      ; preds = %33
  %41 = load i32* %2, align 4
  %42 = add nsw i32 %41, 1
  store i32 %42, i32* %2, align 4
  %43 = load i32* %i, align 4
  %44 = call i64 @getPos(i32 %43)
  %45 = load i32* %i, align 4
  %46 = call i64 @getIndex(i32 %45)
  %47 = load %struct.vector** %1, align 8
  %48 = getelementptr inbounds %struct.vector* %47, i32 0, i32 2
  %49 = load <32 x i32>** %48, align 8
  %50 = getelementptr inbounds <32 x i32>* %49, i64 %46
  %51 = trunc i64 %44 to i32
  %52 = load <32 x i32>* %50
  %53 = insertelement <32 x i32> %52, i32 %41, i32 %51
  store <32 x i32> %53, <32 x i32>* %50
  br label %54

; label:54                                      ; preds = %40
  %55 = load i32* %i, align 4
  %56 = add nsw i32 %55, 1
  store i32 %56, i32* %i, align 4
  br label %33

; label:57                                      ; preds = %33
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
  store i32 50, i32* %6, align 4
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
