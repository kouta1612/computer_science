// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.
    @8192
    D=A
    @pixel_num
    M=D
    @STATE_CHECK
    0;JMP
(STATE_CHECK)
    @KBD
    D=M
    @BLACK
    D;JNE
    @WHITE
    0;JMP
(BLACK)
    @i
    M=0
    @BLACK_LOOP
    0;JMP
(BLACK_LOOP)
    // キーボードが押下されたかどうか確認
    @KBD
    D=M
    @STATE_CHECK
    D;JEQ
    // ピクセル数以上ループしたらはじめに戻る
    @i
    D=M
    @pixel_num
    D=M-D
    @1
    D=D-A
    @STATE_CHECK
    D;JEQ
    // ループ内でスクリーンを8ビットずつ黒く塗りつぶす
    @i
    D=M
    @SCREEN
    A=A+D
    M=-1
    @i
    M=M+1
    @BLACK_LOOP
    0;JMP
(WHITE)
    @i
    M=0
    @WHITE_LOOP
    0;JMP
(WHITE_LOOP)
    // キーボードが押下されていないかどうか確認
    @KBD
    D=M
    @STATE_CHECK
    D;JNE
    // ピクセル数以上ループしたらはじめに戻る
    @i
    D=M
    @pixel_num
    D=M-D
    @1
    D=D-A
    @STATE_CHECK
    D;JEQ
    // ループ内でスクリーンを8ビットずつ白く塗りつぶす
    @i
    D=M
    @SCREEN
    A=A+D
    M=0
    @i
    M=M+1
    @WHITE_LOOP
    0;JMP