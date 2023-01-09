// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)
//
// This program only needs to handle arguments that satisfy
// R0 >= 0, R1 >= 0, and R0*R1 < 32768.

// Put your code here.
@0
D=M // D = R0
@i
M=D // i = D
@1
D=M // D = R1
@j
M=D // j = D
@mul
M=0 // mul = 0
(LOOP)
@i
D=M // D = i
@END
D;JLE // if D <= 0 then goto END
@j
D=M // D = j
@mul
M=M+D // mul += D
@i
M=M-1 // i--
@LOOP
0;JMP // goto LOOP
(END)
@mul
D=M // D = mul
@2
M=D // R2 = D
@END
0;JMP // goto END (infinite)
